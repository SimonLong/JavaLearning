package com.example.rpcfxprovider.service.impl;


import com.example.rpcfxapi.domain.Order;
import com.example.rpcfxapi.service.OrderService;
import org.springframework.stereotype.Service;

public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        return new Order(id, "wyy" + System.currentTimeMillis(), 9.9f);
    }
}
