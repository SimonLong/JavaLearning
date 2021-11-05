package com.example.rabbitmqdemosimple.producer;
import com.example.rabbitmqdemosimple.message.Demo01MessageDirect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class Demo01ProducerDirect {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Demo01Message 消息
        Demo01MessageDirect message = new Demo01MessageDirect();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo01MessageDirect.EXCHANGE, Demo01MessageDirect.ROUTING_KEY, message);
    }

    public void syncSendDefault(Integer id) {
        // 创建 Demo01Message 消息
        Demo01MessageDirect message = new Demo01MessageDirect();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.convertAndSend(Demo01MessageDirect.QUEUE, message);
    }

    @Async
    public ListenableFuture<Void> asyncSend(Integer id) {
        try {
            // 发送消息
            this.syncSend(id);
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }

}
