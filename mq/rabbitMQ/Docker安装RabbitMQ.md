1. 拉取RabbitMQ镜像

   ```
   docker pull rabbitmq:management # 注意不带后缀就不会有 web 控制台
   ```

   

2. 运行带有web控制台的RabbitMQ；执行成功后可通过localhost:15672访问web控制台查看界面

   ```
   docker run -itd --name rabbitmq-test -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p 5672:5672 rabbitmq:management
   ```

   容器运行成功后，可以使用命令进入容器内部执行RabbitMQ相关操作

   ```
   docker exec -it rabbitmq-test /bin/bash
   ```

   常用RabbitMQ命令

   ```
    rabbitmqctl list_queues #查看所有队列
    rabbitmqctl status #查看状态
    rabbitmqadmin declare queue name=yy01 -u admin -p admin #创建队列
    rabbitmqadmin get queue=yy01 -u admin -p admin #查看队列
   ```

   

3. RabbitMQ概念

   producer通过不是直接投递消息到队列中，而是利用交换机与queue绑定，不同的交换机绑定方式不一样。

        FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念，相当于这是一个Topic，所有绑定的队列都会收到消息
        HeadersExchange ：通过添加属性key-value匹配
        DirectExchange:按照routingkey分发到指定队列，精准匹配Exchange,routingkey找到对应的队列
        TopicExchange:多关键字匹配，通过通配符可匹配到Exchange上的多个routingkey，从而分发大多个队列上，实现Topic队列