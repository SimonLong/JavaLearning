package com.example.rabbitmqdemosimple.producer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
public class Demo01ProducerDirectTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo01ProducerDirect producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
//        new CountDownLatch(1).await();
    }

    @Test
    public void tesSyncSendDefault() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.syncSendDefault(id);
        logger.info("[tesSyncSendDefault][发送编号：[{}] 发送成功]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<Void>() {

            @Override
            public void onFailure(Throwable e) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

            @Override
            public void onSuccess(Void aVoid) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，发送成功]", id);
            }

        });
        logger.info("[testASyncSend][发送编号：[{}] 调用完成]", id);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void nothing() throws InterruptedException {
        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void nothing02() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer.syncSend(id);
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
            Thread.sleep(5000L);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}