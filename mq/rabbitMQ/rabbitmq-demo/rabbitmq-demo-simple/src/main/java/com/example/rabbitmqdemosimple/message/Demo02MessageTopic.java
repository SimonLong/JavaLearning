package com.example.rabbitmqdemosimple.message;

import java.io.Serializable;

public class Demo02MessageTopic implements Serializable {

    public static final String QUEUE = "QUEUE_DEMO_TOPIC_02";

    public static final String EXCHANGE = "EXCHANGE_DEMO_TOPIC_02";

    // 匹配已yy.qq结尾的，前面可以有n个前缀
    public static final String ROUTING_KEY = "#.yy.qq";

    /**
     * 编号
     */
    private Integer id;

    public Demo02MessageTopic setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo02MessageTopic{" +
                "id=" + id +
                '}';
    }

}
