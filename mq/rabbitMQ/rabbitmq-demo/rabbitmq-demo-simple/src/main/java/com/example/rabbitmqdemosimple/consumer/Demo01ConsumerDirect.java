package com.example.rabbitmqdemosimple.consumer;

import com.example.rabbitmqdemosimple.message.Demo01MessageDirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Demo01MessageDirect.QUEUE)
public class Demo01ConsumerDirect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void onMessage(Demo01MessageDirect message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

//    @RabbitHandler(isDefault = true)
//    public void onMessage(org.springframework.amqp.core.Message message) {
//        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
//    }

}
