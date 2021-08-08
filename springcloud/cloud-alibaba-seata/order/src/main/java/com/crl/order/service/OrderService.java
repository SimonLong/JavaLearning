package com.crl.order.service;


import com.crl.order.client.UserClient;
import com.crl.order.domain.Order;
import com.crl.order.domain.OrderDTO;
import com.crl.order.domain.converter.OrderConverter;
import com.crl.order.mapper.OrderMapper;
import com.crl.userdto.User;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

    @GlobalTransactional
    public void save(OrderDTO orderDTO) {
        Order order = OrderConverter.INSTANCE.dtoToDo(orderDTO);
        orderMapper.insert(order);
        User user = new User();
        user.setId(order.getUserId());
        user.setMoney(-orderDTO.getMoney());
        user.setMoneyFreeze(orderDTO.getMoney());
        userClient.changeMoney(user);
    }


}
