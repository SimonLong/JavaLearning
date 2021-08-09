### 项目结构

├── config  配置中心演示  
│   ├── config-client  
│   ├── config-server  
├── eureka-server  注册中心server  
├── netflix-order  订单服务  
│   ├── netflix-order-dto  
│   ├── netflix-order-service  
│   ├── netflix-order-service-api  
│   ├── netflix-order-web  
├── netflix-user  用户服务  
│   ├── netflix-user-dto  
│   ├── netflix-user-service  
│   ├── netflix-user-service-api  
├── sleuth  链路中心演示  
│   ├── sleuth-trace1  
│   ├── sleuth-trace2  
│   └── zipkin-server  
├── stream  消息驱动微服务演示  
│   ├── stream-hello  
│   └── stream-self  
└── zuul-gateway  服务网关  

### 概念理解

1. Spring Cloud Config
   
   配置中心，把配置文件由本地转移到git等远程仓库，然后把配置中心服务注册到eureka等注册中心，其他的客户端通过这个服务来获取配置并启动项目。当配置更改时，可访问/actuator/refresh刷新配置（不灵活），或者配置webHook与Spring Bus，自动访问/actuator/bus-refresh实现自动动态更新，可以全量或者增量更新
   
2. Spring Cloud Bus, Spring Cloud Stream

   Spring Cloud Config的动态实现原理是Spring Cluod Bus消息总线，而Spring Cloud Bus实现原理是Spring Cloud Stream。Spring Cloud Stream是一套通用的消息体系，屏蔽不同消息中间件的差异来实现消息的无差异化使用，底层实现是Spring Messaging 和 Spring Integration

3. Spring Cloud Sleuth,Spring Cloud Zipkin

   链路追踪，在每个restful请求加上header，然后打印在日志中，结合Logstash将追踪链路的数据以json形式输出到文件中。sleuth还可以结合zipkin，记录接口的响应速率

4. nginx流量网关->zuul2业务网关（token转发、登录验证）->具体微服务



### 发布注意事项
1. 调整order-server,user-service的数据库地址，此处用的的windows的linux虚拟机，所以需要配置linux主机能访问的局域网地址
2. 服务地址默认不支持ip，使用ip地址需要改配置；此处是在windows的(C:\Windows\System32\drivers\etc)hosts下调整peer1,peer2的映射，映射到虚拟机内部
3. hmily没有将此版本放到中央仓库，需要自行下载源码进行install到本地仓库https://github.com/dromara/hmily/releases/tag/1.2.1