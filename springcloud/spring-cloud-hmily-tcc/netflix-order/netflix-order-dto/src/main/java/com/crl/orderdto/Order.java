package com.crl.orderdto;

import lombok.Data;

@Data
public class Order {

    private Long orderId;

    private Integer userId;

    private String status;

}
