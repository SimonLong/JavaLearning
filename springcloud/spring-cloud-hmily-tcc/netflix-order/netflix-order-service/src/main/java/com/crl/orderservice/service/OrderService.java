package com.crl.orderservice.service;

import com.crl.dto.User;
import com.crl.orderdto.Order;
import com.crl.orderservice.feign.UserClient;
import com.crl.orderservice.mapper.OrderMapper;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderMapper orderMapper;

    @Hmily(confirmMethod = "confirmOrder", cancelMethod = "cancelOrder")
    public void save(Order order) {
        order.setStatus("1");
        orderMapper.insert(order);
        User user = new User();
        user.setMoney(1);
        user.setId(order.getUserId());
        userClient.changeMoney(user);
        // int i = 1/0;
    }

    public void confirmOrder(Order order) {
        System.out.println("-------confirm order-----");

    }

    public void cancelOrder(Order order) {
        // cancel需要做成幂等(根据业务情况)
        System.out.println("-------cancel order-----");
        order.setStatus("-1");
        orderMapper.cancelOrder(order);
    }

}
