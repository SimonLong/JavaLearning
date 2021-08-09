#### 概念理解

##### Redis主从与MySQL主从的区别

1. Redis是异步复制；MySQL默认异步复制（同步数据不可靠），可配置半同步复制、组复制
2. MySQL从数据库可写，Redis从数据库是只读

##### 哨兵机制(sentinel)

哨兵机制是Redis自身解决高可用问题的方案，在master宕机后能够重新选举master，保证Redis服务的可用性。而MySQL的高可用是需要中间件解决的

#### Redis主从配置步骤

1. 拷贝原始的redis.conf文件，复制生成两份文件redis-6380.conf,redis-6381.conf，修改关键地方配置

   ```
   port 6380
   pidfile /var/run/redis_6380.pid
   logfile "redis-6380.log"
   #rbd配置
   dbfilename dump-6380.rdb
   #多线程开启，redis支持多线程，但默认是关闭的
   io-threads 4
   io-threads-do-reads yes
   ```

   

2. docker启动redis容器,设置端口映射与数据卷挂载

   ```
   docker run -it -d --name redis-test -p 6379:6379 -p 6380:6380 -p 6381:6381 -v D:\workspace\idea_workspace\java-learning\java-learning\week12\job01-sentinel-cluster\conf:/data/redis/conf redis
   ```

   

3. 启动6380与6381的redis服务，在6380与6381两个库都写入port-test值(设置主从后原来的数据消失？)

   ```
   redis-server /data/redis/conf/redis-6380.conf
   redis-cli -h 127.0.0.1 -p 6380
   set port-test 6380
   ```

4. 6381服务指向主库6380（此方式为手动设置，重启会失效，后续配置方式居多，可参考下面sentinel配置文件方式）

   ```
   slaveOf 127.0.0.1 6380
   ```

5. 查询从库6381的port-test值是否与6380一致，或set值是否操作

   ```
   get port-test
   ```

#### Redis哨兵机制sentinel

redis配置主从只是提高了性能，没有保证高可用。高可用需要使用redis的sentinel哨兵机制，配置3个哨兵、1主2从实例如下：

1. 在redis的各个从服务器conf文件中配置master

   ```
   port 6381
   pidfile /var/run/redis_6381.pid
   logfile "redis-6381.log"
   #rbd配置
   dbfilename dump-6381.rdb
   #多线程开启，redis支持多线程，但默认是关闭的
   io-threads 4
   io-threads-do-reads yes
   #master配置
   replicaof 127.0.0.1 6380
   #master密码配置
   masterauth 123456
   ```

   

2. 创建3个sentinel的配置文件

   ```
   #监控的IP 端口号 名称 sentinel通过投票后认为mater宕机的数量，此处为至少2个
   sentinel monitor mymaster 127.0.0.1 6380 2
   sentinel auth-pass mymaster 123456
   port 23680
   #10秒ping不通主节点，认为mater宕机
   sentinel down-after-milliseconds mymaster 10000
   #后台运行
   daemonize yes
   #设置日志文件
   logfile /data/redis/log/sentinel0.log
   ```

3. docker启动一个redis容器，设置挂载卷与redis端口映射、哨兵端口映射

   ```
   docker run -it -d --name redis-sentinel-test -p 6379:6379 -p 6380:6380 -p 6381:6381 -p 6382:6382 -p 26380:26380 -p 26381:26381 -p 26382:26382 -v D:\workspace\docker_workspace\redis_sentinel:/data/redis redis /bin/bash
   ```

   

4. docker依次启动master,slave

   ```
   redis-server /data/redis/conf/redis-6380.conf
   ```

   

5. 依次启动3个sentinel哨兵(redis-server命令也可,要加上--sentinel)

   ```
   redis-sentinel /data/redis/conf/sentinel0.conf
   ```

   启动成功后的sentinel配置文件会生成添加对应的信息

   ```
   #监控的IP 端口号 名称 sentinel通过投票后认为mater宕机的数量，此处为至少2个
   sentinel monitor mymaster 127.0.0.1 6380 2
   sentinel auth-pass mymaster 123456
   #10秒ping不通主节点，认为mater宕机
   sentinel down-after-milliseconds mymaster 10000
   port 23682
   # Generated by CONFIG REWRITE
   user default on nopass ~* &* +@all
   dir "/data"
   sentinel myid 63c50ce598c4b86a3f46d24272c766012dce354d
   sentinel config-epoch mymaster 0
   sentinel leader-epoch mymaster 0
   sentinel current-epoch 0
   sentinel known-replica mymaster 127.0.0.1 6382
   sentinel known-replica mymaster 127.0.0.1 6381
   sentinel known-sentinel mymaster 127.0.0.1 23681 90f0ae90e3713376e7dd0a91dab5511f763888ed
   sentinel known-sentinel mymaster 127.0.0.1 26379 51f1974d1410c7540acec2e4d6b2da84316e6d99
   
   ```

6. 可用命令查看哨兵信息

   ```
   redis-cli -p 26380 info
   ```

   

7. 关掉master服务器，查看哨兵日志是否生效。登录新的master节点，尝试写操作，操作成功，哨兵机制生效

#### Sentinel使用的Raft算法核心: 原则

1. 所有sentinel都有选举的领头羊的权利
2. 每个sentinel都会要求其他sentinel选举自己为领头羊(主要由发现redis客观下线的sentinel先发起选举)
3. 每个sentinel只有一次选举的机会
4. 采用先到先得的原则
5. 一旦加入到系统了，则不会自动清除(这一点很重要, why?)
6. 每个sentinel都有唯一的uid，不会因为重启而变更
7. 达到领头羊的条件是 N/2 + 1个sentinel选择了自己
8. 采用配置纪元，如果一次选举出现脑裂，则配置纪元会递增，进入下一次选举，所有sentinel都会处于统一配置纪元，以最新的为标准。

#### Redis Cluster集群

redis使用哨兵机制、主从后保证了服务的高可用，但本质上还是单机的，可承载的数据量还是有限的。

1. 创建6个redis.conf

   ```
   #可选操作，该项设置后台方式运行，
   daemonize yes
   
   port 7000
   cluster-enabled yes
   cluster-config-file nodes.conf
   cluster-node-timeout 5000
   appendonly yes
   logfile "redis-7000.log"
   ```

   

2. 启动redis容器，设置端口与挂载卷

   ```
   docker run -it -d --name redis-cluster-test -p 7000:7000 -p 7001:7001 -p 7002:7002 -p 7003:7003 -p 7004:7004 -p 7005:7005 -v D:\workspace\idea_workspace\java-learning\java-learning\week12\job01-sentinel-cluster\conf\cluster:/data/redis/conf redis
   ```

3. 在各个配置文件的目录下，以普通方式启动所有的redis-server（启动目录默认为工作目录，也可在配置文件中指定）

   ```
   cd /data/redis/conf/7000
   redis-server redis.conf
   ```

4. 启动其中一个redis-cli,meet其他所有的redis服务

   ```
    cluster meet 127.0.0.1 7001
    cluster meet 127.0.0.1 7002
    cluster meet 127.0.0.1 7003
    cluster meet 127.0.0.1 7004
    cluster meet 127.0.0.1 7005
   ```

   meet后可用cluster nodes查看节点信息，并用于下一步的主从配置

5. 配置3主3从

   ```
   redis-cli -h 127.0.0.1 -p 7001 cluster replicate c407e493d2c70053d3fcf89d8492511cd8ee9a31
   redis-cli -h 127.0.0.1 -p 7003 cluster replicate c4883beaec845b7e276a126ababc59f899092be4
   redis-cli -h 127.0.0.1 -p 7005 cluster replicate 03048fbcbe044af5e20f3c0eac8cff1b84e283cf
   ```

6. 分配槽位slot(windows貌似不行，需逐个槽位添加)

   ```
   redis-cli -h 127.0.0.1 -p 7000 cluster addslots {0..5461}
   redis-cli -h 127.0.0.1 -p 7002 cluster addslots {5462..10922}
   redis-cli -h 127.0.0.1 -p 7004 cluster addslots {10923..16383}
   ```

7. 以集群命令方式设置值(Redis Cluster会自动设置到对应的分区上)

   ```
   redis-cli -c -h 127.0.0.1 -p 7000 set mayun alibaba-996
   redis-cli -c -h 127.0.0.1 -p 7000 get mayun
   ```

10. 此方式线上容易出纰漏，可考虑使用ruby脚本方式

11. 使用Cluster方式是通过ping/pong消息实现故障发现，不需要sentinel，并解决了单机容量不足问题