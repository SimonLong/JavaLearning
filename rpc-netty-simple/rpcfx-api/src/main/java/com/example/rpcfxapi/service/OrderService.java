package com.example.rpcfxapi.service;

import com.example.rpcfxapi.domain.Order;

public interface OrderService {

    Order findOrderById(int id);

}
