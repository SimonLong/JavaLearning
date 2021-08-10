package com.example.rpcfxprovider;

import com.example.rpcfxcore.api.ServiceProviderDesc;
import com.example.rpcfxcore.netty.server.RpcNettyServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetAddress;

/**
 * 不使用 spring boot web，启动 netty server 进行监听
 *
 */
@SpringBootApplication
@Slf4j
public class ServerApplication implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        // start zk client
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();



        String userService = "com.example.rpcfxapi.service.UserService";
        registerService(client, userService);
        String orderService = "com.example.rpcfxapi.service.OrderService";
        registerService(client, orderService);
        new SpringApplicationBuilder(ServerApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        RpcNettyServer rpcNettyServer = new RpcNettyServer(applicationContext);
        try {
            rpcNettyServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rpcNettyServer.destroy();
        }
    }

    private static void registerService(CuratorFramework client, String service) throws Exception {
        ServiceProviderDesc userServiceSesc = ServiceProviderDesc.builder()
                .host(InetAddress.getLocalHost().getHostAddress())
                .port(8080).serviceClass(service).build();
//		 String userServiceSescJson = JSON.toJSONString(userServiceSesc);

        try {
            if ( null == client.checkExists().forPath("/" + service)) {
                client.create().withMode(CreateMode.PERSISTENT).forPath("/" + service, "service".getBytes());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        client.create().withMode(CreateMode.EPHEMERAL).
                forPath( "/" + service + "/" + userServiceSesc.getHost() + "_" + userServiceSesc.getPort(), "provider".getBytes());
    }


}
