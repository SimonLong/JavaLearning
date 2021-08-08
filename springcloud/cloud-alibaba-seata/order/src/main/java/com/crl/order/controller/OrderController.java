package com.crl.order.controller;

import com.crl.order.domain.Order;
import com.crl.order.domain.OrderDTO;
import com.crl.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
@RefreshScope
@Slf4j
public class OrderController {

    @Value("${crl.title:}")
    private String title;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/save")
    public String save(@RequestBody OrderDTO order) {
        orderService.save(order);
        return title;
    }

}
