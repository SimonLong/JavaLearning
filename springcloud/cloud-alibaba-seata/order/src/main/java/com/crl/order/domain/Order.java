package com.crl.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class Order {

    private Long orderId;

    private Integer userId;

    private String status;



}
