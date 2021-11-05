package com.example.rabbitmqdemosimple.message;

import java.io.Serializable;

public class Demo04MessageHeaders implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_HEADERS_04_A";

    public static final String EXCHANGE = "EXCHANGE_DEMO_HEADERS_04";

    public static final String HEADER_KEY = "color";
    public static final String HEADER_VALUE = "red";

    /**
     * 编号
     */
    private Integer id;

    public Demo04MessageHeaders setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo04MessageHeaders{" +
                "id=" + id +
                '}';
    }

}
