#### 项目结构
rpcfx-api: 公共domain与服务api
rpcfx-consumer: 服务消费者
rpcfx-core: rpc核心，包括netty编码解码器、rpc netty server实现、rpc netty client代理实现、序列化、路由负载均衡定义
rpcfx-provider: 服务提供者，采用netty一主多从的Reactor模型对外提供服务、zookeeper服务注册
#### 流程介绍
1. Docker启动Zookeeper，版本是3.4.13
2. ServerApplication启动，注册服务到Zookeeper，并启动NettyServer
3. RpcfxConsumerApplication消费者启动，调用动态代理客户端，直接访问服务端或通过Zookeeper拿到服务端数据并处理路由、负载均衡
#### 不足之处
1. 序列化协议目前是固定的，可改造成SPI机制
2. 动态代理对象目前是直接获取的，可直接改造成AOP
3. 传输数据没有压缩
4. 路由与负载均衡目前只是简单实现
#### 联系我
目前只是实现一个乞丐版的RPC，仅作为学习理解，如果希望进行技术交流，可加我微信

![wx](../img/wx.jpg)