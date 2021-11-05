package com.example.rabbitmqdemosimple.producer;

import com.example.rabbitmqdemosimple.message.Demo04MessageHeaders;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Demo04ProducerHeaders {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id, String headerValue) {
        // 创建 MessageProperties 属性
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader(Demo04MessageHeaders.HEADER_KEY, headerValue); // 设置 header
        // 创建 Message 消息
        Message message = rabbitTemplate.getMessageConverter().toMessage(
                new Demo04MessageHeaders().setId(id), messageProperties);
        // 同步发送消息
        rabbitTemplate.send(Demo04MessageHeaders.EXCHANGE, null, message);
    }

}
