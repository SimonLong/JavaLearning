package com.crl.orderservice.mapper;

import com.crl.orderdto.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper  {

    void insert(Order order);

    void cancelOrder(Order order);
}
