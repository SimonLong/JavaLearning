package com.example.rpcfxconsumer;

import com.example.rpcfxapi.domain.Order;
import com.example.rpcfxapi.domain.User;
import com.example.rpcfxapi.service.OrderService;
import com.example.rpcfxapi.service.UserService;
import com.example.rpcfxcore.api.Filter;
import com.example.rpcfxcore.api.LoadBalancer;
import com.example.rpcfxcore.api.Router;
import com.example.rpcfxcore.api.RpcfxRequest;
import com.example.rpcfxcore.client.AbstractRpcClient;
import com.example.rpcfxcore.client.proxy.RpcByteBuddy;
import com.example.rpcfxcore.client.proxy.RpcClientCglib;
import com.example.rpcfxcore.client.proxy.RpcClientJdk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RpcfxConsumerApplication {

    public static void main(String[] args) {

        // UserService service = new xxx();
        // service.findById
        AbstractRpcClient jdk = new RpcClientJdk();
        UserService userService = jdk.create(UserService.class, "http://localhost:8080/");
        User user = userService.findById(1);
        System.out.println("RpcClientJdk find user id=1 from server: " + user.getName());

        AbstractRpcClient buddy = new RpcByteBuddy();
        OrderService orderService = buddy.create(OrderService.class, "http://localhost:8080/");
        Order order = orderService.findOrderById(1992129);
        System.out.println(String.format("RpcByteBuddy find order name=%s, amount=%f",order.getName(),order.getAmount()));

        //
        AbstractRpcClient cglib = new RpcClientCglib();
        UserService userService2 = cglib.createFromRegistry(UserService.class, "localhost:2181", new TagRouter(), new RandomLoadBalancer());
        User byId = userService2.findById(1);
        System.out.println("RpcClientCglib find user id=1 from server: " + byId.getName());
//		SpringApplication.run(RpcfxClientApplication.class, args);
    }

    private static class TagRouter implements Router {
        @Override
        public List<String> route(List<String> urls) {
            return urls;
        }
    }

    private static class RandomLoadBalancer implements LoadBalancer {
        @Override
        public String select(List<String> urls) {
            return urls.get(0);
        }
    }


}
