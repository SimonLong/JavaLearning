package com.example.rabbitmqdemosimple.producer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class Demo02ProducerTopicTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo02ProducerTopic producer;

    @Test
    public void testSyncSendSuccess() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        //不可以发送到#.yy.qq.队列
        producer.syncSend(id, "yy.qq.jj");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testSyncSendFailure() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        //可以发送到#.yy.qq的队列
        producer.syncSend(id, "ii.yy.qq");
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

}
