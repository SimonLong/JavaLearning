package com.example.rabbitmqdemosimple.message;

import java.io.Serializable;

public class Demo01MessageDirect implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_DIRECT_01";

    public static final String EXCHANGE = "EXCHANGE_DEMO_DIRECT_01";

    public static final String ROUTING_KEY = "ROUTING_KEY_DIRECT_01";

    /**
     * 编号
     */
    private Integer id;

    public Demo01MessageDirect setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo01MessageDirect{" +
                "id=" + id +
                '}';
    }

}
