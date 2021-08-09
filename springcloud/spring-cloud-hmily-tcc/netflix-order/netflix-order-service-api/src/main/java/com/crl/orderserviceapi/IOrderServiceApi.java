package com.crl.orderserviceapi;

import com.crl.orderdto.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/order")
public interface IOrderServiceApi {

    @PostMapping("/save")
    String save(@RequestBody Order order);
}
