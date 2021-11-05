package com.example.rabbitmqdemosimple.producer;

import com.example.rabbitmqdemosimple.message.Demo02MessageTopic;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo02ProducerTopic {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, String routingKey) {
        // 创建 Demo02MessageTopic 消息
        Demo02MessageTopic message = new Demo02MessageTopic();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo02MessageTopic.EXCHANGE, routingKey, message);
    }

}
