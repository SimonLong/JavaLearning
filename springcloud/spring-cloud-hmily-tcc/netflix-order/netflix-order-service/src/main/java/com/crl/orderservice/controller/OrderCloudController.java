package com.crl.orderservice.controller;

import com.crl.orderdto.Order;
import com.crl.orderservice.service.OrderService;
import com.crl.orderserviceapi.IOrderServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderCloudController implements IOrderServiceApi {

    @Autowired
    private OrderService orderService;

    @Override

    public String save(@RequestBody Order order) {
        orderService.save(order);
        return "success";
    }
}
