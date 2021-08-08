### seata-server启动配置

1. 下载seata-server-1.2.0和seata-1.2.0源码(源码主要用于后面的naco配置)

   https://github.com/seata/seata/releases/tag/v1.2.0

2. 创建seata事务相关表（在源码目录下）

   ```
   /script/server/db
   ```

   

3. 更改seata-server中的registry.conf（以nacos为例）

   ```
   registry {
     # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
     type = "nacos"
   
     nacos {
       application = "seata-server"
       serverAddr = "localhost:8848"
       namespace = ""
       cluster = "default"
       username = "nacos"
       password = "nacos"
     }
   
   }
   
   config {
     # file、nacos 、apollo、zk、consul、etcd3
     type = "file"
   
     nacos {
       serverAddr = "localhost:8848"
       namespace = ""
       group = "SEATA_GROUP"
       username = "nacos"
       password = "nacos"
     }
     
   }
   
   ```

   

4. 修改seata-server中的file.config(以db方式为例)

   ```
   
   ## transaction log store, only used in seata-server
   store {
     ## store mode: file、db
     mode = "db"
   
     ## file store property
     file {
       ## store location dir
       dir = "sessionStore"
       # branch session size , if exceeded first try compress lockkey, still exceeded throws exceptions
       maxBranchSessionSize = 16384
       # globe session size , if exceeded throws exceptions
       maxGlobalSessionSize = 512
       # file buffer size , if exceeded allocate new buffer
       fileWriteBufferCacheSize = 16384
       # when recover batch read size
       sessionReloadReadSize = 100
       # async, sync
       flushDiskMode = async
     }
   
     ## database store property
     db {
       ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
       datasource = "druid"
       ## mysql/oracle/postgresql/h2/oceanbase etc.
       dbType = "mysql"
       driverClassName = "com.mysql.jdbc.Driver"
       url = "jdbc:mysql://127.0.0.1:3306/seata"
       user = "root"
       password = "admin"
       minConn = 5
       maxConn = 30
       globalTable = "global_table"
       branchTable = "branch_table"
       lockTable = "lock_table"
       queryLimit = 100
       maxWait = 5000
     }
   }
   ```

   

5. 修改提交nacos脚本到nacos控制台

   ```
   bash nacos-config.sh -h 192.168.3.27
   ```

   PS:此处用的是windows的内置linux子系统，使用sh命令会报语法错误，所以用bash，-h传递host，如果是localhost可不传递

6. 运行seata-server

   ```windows
   .\seata-server.bat
   ```

   

### 项目seata配置（客户端）

1. 创建undo_log日志表(在源码目录下)

   ```
   /script/client/at/db
   ```

   

2. 项目中引入seata依赖