package com.example.rabbitmqdemosimple.producer;

import com.example.rabbitmqdemosimple.message.Demo03MessageFanout;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo03ProducerFanout {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo03MessageFanout 消息
        Demo03MessageFanout message = new Demo03MessageFanout();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo03MessageFanout.EXCHANGE, null, message);
    }

}
