### kafka单机安装

2. 下载kafka，地址：[http://kafka.apache.org/downloads](http://kafka.apache.org/downloads)

3. 修改config目录下的server.properties文件

   ```
   listeners=PLAINTEXT://localhost:9092
   logs.dir=/tmp/kafka/logs
   ```

4. 启动zookeeper与kafka

   linux命令如下：
   ```linux
   bin/zookeeper-server-start.sh config/zookeeper.properties
   bin/kafka-server-start.sh config/server.properties
   ```
   ```
   #后台方式运行
   sudo nohup bin/kafka-server-start.sh config/server.properties >> /mnt/d/workspace/ubuntu_workspace/kafka/kafka_2.13-2.8.0/logs/kafka-nohup.out 2>&1 &
   ```

   windows命令如下(zookeeper启动失败，可单独启动zookeeper或者使用Docker启动)：

   ```windows
   cd D:\program\kafka_2.13-2.8.0\bin\windows
   kafka-server-start.bat ../../config/zookeeper.properties
   kafka-server-start.bat ../../config/server.properties
   ```

5. 单机部署测试

   ```linux
   bin/kafka-topics.sh --zookeeper localhost:2181 --list
   bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic testk --partitions 3 --replication-factor 1
   bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic testk
   bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic testk
   bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic testk
   ```

   ```windows
   cd D:\program\kafka_2.13-2.8.0\bin\windows
   kafka-topics.bat --zookeeper localhost:2181 --list
   kafka-topics.bat --zookeeper localhost:2181 --create --topic testk --partitions 3 --replication-factor 1
   kafka-topics.bat --zookeeper localhost:2181 --describe --topic testk
   kafka-console-consumer.bat --bootstrap-server localhost:9092 --from-beginning --topic testk
   kafka-console-producer.bat --bootstrap-server localhost:9092 --topic testk
   ```
   
6. 性能测试

   ```linux
   bin/kafka-producer-perf-test.sh --topic testk --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9092
   bin/kafka-consumer-perf-test.sh --bootstrap-server localhost:9092 --topic testk --fetch-size 
   1048576 --messages 100000 --threads 1
   ```

   ```windows
   cd D:\program\kafka_2.13-2.8.0\bin\windows
   kafka-producer-perf-test.bat --topic testk --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9092
   kafka-consumer-perf-test.bat --bootstrap-server localhost:9092 --topic testk --fetch-size 1048576 --messages 100000 --threads 1
   ```

### kafka单机安装(Docker)

1. 拉取zookeeper,kafka镜像

   ```
   docker pull wurstmeister/zookeeper
   docker pull wurstmeister/kafka
   ```

2. 单机方式运行zookeeper

   ```
   docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper
   ```

3. 单机方式运行kafka

   ```
   docker run -d --name kafka \
   -p 9092:9092 \
   -e KAFKA_BROKER_ID=0 \
   -e KAFKA_ZOOKEEPER_CONNECT=127.0.0.1:2181 \
   -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://127.0.0.1:9092 \
   -e KAFKA_LISTENERS=PLAINTEXT://127.0.0.1:9092 wurstmeister/kafka
   ```

4. 进入kafka容器(目前容器启动成功且可在容器内部访问，但是目前外部容器无法正常连接，具体原因待看)

   ```
   #进入容器
   docker exec -it ${CONTAINER ID} /bin/bash
   cd opt/kafka/bin
   #单机方式：创建一个主题
   kafka-topics.sh --create --zookeeper 192.168.44.1:2181 --replication-factor 1 --partitions 1 --topic mykafka
   #运行一个生产者
   kafka-console-producer.sh --broker-list localhost:9092 --topic mykafka
   #运行一个消费者(--from-beginnin从开始位置开始消费),--zookeeper方式已过时，用--bootstrap-server
   kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic mykafka
   ```

### kafka集群安装(windows)

1. 启动zookeeper并删除所有数据，可删除本地文件或使用[ZooInspector](https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip)；使用ZooInspector删除除了zookeeper的所有节点即可

2. 启动zookeeper，此处使用Docker启动单个zookeeper

3. 创建集群配置文件

   ```
   broker.id=1 #这里分别设置1,2,3
   num.network.threads=3
   listeners=PLAINTEXT://:9001 #区分端口
   broker.list=localhost:9001,localhost:9002,localhost:9003
   zookeeper.connect=192.168.3.27:2181
   socket.send.buffer.bytes=102400
   socket.receive.buffer.bytes=102400
   socket.request.max.bytes=104857600
   #log.dirs=/tmp/kafka/kafka-logs1 #区分日志文件
   log.dirs=D:/program/kafka_2.13-2.8.0/logs/kafka-logs-9001
   num.partitions=1
   num.recovery.threads.per.data.dir=1
   offsets.topic.replication.factor=1
   transaction.state.log.replication.factor=1
   transaction.state.log.min.isr=1
   log.retention.hours=168
   log.segment.bytes=1073741824
   log.retention.check.interval.ms=300000
   zookeeper.connection.timeout.ms=6000000
   delete.topic.enable=true
   group.initial.rebalance.delay.ms=0
   message.max.bytes=500000
   replica.fetch.max.bytes=500000
   ```

4. 启动集群

     ```
     kafka-server-start.bat server-9001.properties
     kafka-server-start.bat server-9002.properties
     kafka-server-start.bat server-9003.properties
     ```

5. 创建有副本的topic

     ```
     kafka-topics.bat --zookeeper localhost:2181 --create --topic test32 --partitions 3 --replication-factor 2
     
     kafka-console-producer.bat --bootstrap-server localhost:9003 --topic test32
     
     kafka-console-consumer.bat --bootstrap-server localhost:9001 --topic test32 --from-beginning
     ```

6. 性能测试

   ```
   kafka-producer-perf-test.bat --topic test32 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9002
   
   kafka-consumer-perf-test.bat --bootstrap-server localhost:9002 --topic test32 --fetch-size 1048576 --messages 100000 --threads 1
   
   ```

### kafka集群安装(linux)

1. 启动zookeeper并删除所有数据，可删除本地文件或使用[ZooInspector](https://issues.apache.org/jira/secure/attachment/12436620/ZooInspector.zip)；使用ZooInspector删除除了zookeeper的所有节点即可

2. 启动zookeeper，此处使用Docker启动单个zookeeper

3. 创建集群配置文件

   ```
   #这里分别设置1,2,3
   broker.id=1
   num.network.threads=3
   #区分端口
   listeners=PLAINTEXT://:9001 
   broker.list=localhost:9001,localhost:9002,localhost:9003
   zookeeper.connect=192.168.3.27:2181
   socket.send.buffer.bytes=102400
   socket.receive.buffer.bytes=102400
   socket.request.max.bytes=104857600
   log.dirs=/tmp/kafka/kafka-logs1 #区分日志文件
   num.partitions=1
   num.recovery.threads.per.data.dir=1
   offsets.topic.replication.factor=1
   transaction.state.log.replication.factor=1
   transaction.state.log.min.isr=1
   log.retention.hours=168
   log.segment.bytes=1073741824
   log.retention.check.interval.ms=300000
   zookeeper.connection.timeout.ms=6000000
   delete.topic.enable=true
   group.initial.rebalance.delay.ms=0
   message.max.bytes=500000
   replica.fetch.max.bytes=500000
   ```

4. 启动集群

     ```
     kafka-server-start.sh kafka-9001.conf
     kafka-server-start.sh kafka-9002.conf
     kafka-server-start.sh kafka-9003.conf
     ```

5. 创建有副本的topic

     ```
      ./bin/kafka-topics.sh --zookeeper 192.168.3.27:2181 --create --topic test32 --partitions 3 --replication-factor 2
     
      ./bin/kafka-console-producer.sh --bootstrap-server localhost:9003 --topic test32
     
     ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9001 --topic test32 --from-beginning
     ```

6. 性能测试

   ```
   ./bin/kafka-producer-perf-test.sh --topi
   c test32 --num-records 100000 --record-size 1000 --throughput 2000 --producer-props bootstrap.servers=localhost:9002
   
   ```
