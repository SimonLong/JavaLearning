package com.crl.userdto;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String name;

    private Integer money;

    private Integer moneyFreeze;
}
