#启动eureka
sudo nohup java -jar ./eureka-server/target/eureka-server-1.0.0.jar --spring.profiles.active=peer1 > eureka1.out 2>&1 &
sudo nohup java -jar ./eureka-server/target/eureka-server-1.0.0.jar --spring.profiles.active=peer2 > eureka2.out 2>&1 &

#启动网关
sudo nohup java -jar ./zuul-gateway/target/zuul-gateway-1.0.0.jar > gateway.out 2>&1 &

#启动订单模块
sudo nohup java -jar ./order/order-service/target/order-service-1.0.0.jar > order.out 2>&1 &
#启动用户模块
sudo nohup java -jar ./user/user-service/target/user-service-1.0.0.jar > user.out 2>&1 &

#sleuth链路追踪demo
java -jar ./sleuth/sleuth-trace1/target/sleuth-trace1-1.0.0.jar
java -jar ./sleuth/sleuth-trace2/target/sleuth-trace2-1.0.0.jar