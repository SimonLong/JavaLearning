package com.example.rpcfxprovider.config;

import com.example.rpcfxapi.service.OrderService;
import com.example.rpcfxapi.service.UserService;
import com.example.rpcfxprovider.service.impl.OrderServiceImpl;
import com.example.rpcfxprovider.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config  {

    @Bean("com.example.rpcfxapi.service.UserService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean("com.example.rpcfxapi.service.OrderService")
    public OrderService orderService() {
        return new OrderServiceImpl();
    }

}
