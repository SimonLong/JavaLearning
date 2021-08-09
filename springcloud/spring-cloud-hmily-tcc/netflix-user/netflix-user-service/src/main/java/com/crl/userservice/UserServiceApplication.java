package com.crl.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

//@EnableFeignClients
//激活Eureka的DiscoveryClient实现
@EnableDiscoveryClient
@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
