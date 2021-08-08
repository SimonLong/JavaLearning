package com.crl.order.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class OrderDTO {

    private Long orderId;

    private Integer userId;

    private String status;

    private Integer money;



}
