package com.example.rabbitmqdemosimple.message;

import java.io.Serializable;

public class Demo03MessageFanout implements Serializable {

    public static final String QUEUE_A = "QUEUE_DEMO_FANOUT_03_A";
    public static final String QUEUE_B = "QUEUE_DEMO_FANOUT_03_B";

    public static final String EXCHANGE = "EXCHANGE_DEMO_FANOUT_03";

    /**
     * 编号
     */
    private Integer id;

    public Demo03MessageFanout setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo03MessageFanout{" +
                "id=" + id +
                '}';
    }

}
