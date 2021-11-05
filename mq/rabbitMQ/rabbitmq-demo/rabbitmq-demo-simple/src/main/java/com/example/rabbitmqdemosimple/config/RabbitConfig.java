package com.example.rabbitmqdemosimple.config;

import com.example.rabbitmqdemosimple.message.Demo01MessageDirect;
import com.example.rabbitmqdemosimple.message.Demo02MessageTopic;
import com.example.rabbitmqdemosimple.message.Demo03MessageFanout;
import com.example.rabbitmqdemosimple.message.Demo04MessageHeaders;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo01Queue() {
            return new Queue(Demo01MessageDirect.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Direct Exchange
        @Bean
        public DirectExchange demo01Exchange() {
            return new DirectExchange(Demo01MessageDirect.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo01Message.EXCHANGE
        // Routing key：Demo01Message.ROUTING_KEY
        // Queue：Demo01Message.QUEUE
        @Bean
        public Binding demo01Binding() {
            return BindingBuilder.bind(demo01Queue()).to(demo01Exchange()).with(Demo01MessageDirect.ROUTING_KEY);
        }

    }

    /**
     * Topic Exchange 示例的配置类
     */
    public static class TopicExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo02Queue() {
            return new Queue(Demo02MessageTopic.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Topic Exchange
        @Bean
        public TopicExchange demo02Exchange() {
            return new TopicExchange(Demo02MessageTopic.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo02Message.EXCHANGE
        // Routing key：Demo02Message.ROUTING_KEY
        // Queue：Demo02Message.QUEUE
        // 每次启动会创建新的Binding到MQ(每次修改启动都会新增一个binding)
        @Bean
        public Binding demo02Binding() {
            return BindingBuilder.bind(demo02Queue()).to(demo02Exchange()).with(Demo02MessageTopic.ROUTING_KEY);
        }

    }

    /**
     * Fanout Exchange 示例的配置类
     */
    public static class FanoutExchangeDemoConfiguration {

        // 创建 Queue A
        @Bean
        public Queue demo03QueueA() {
            return new Queue(Demo03MessageFanout.QUEUE_A, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Queue B
        @Bean
        public Queue demo03QueueB() {
            return new Queue(Demo03MessageFanout.QUEUE_B, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Fanout Exchange
        @Bean
        public FanoutExchange demo03Exchange() {
            return new FanoutExchange(Demo03MessageFanout.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding A
        // Exchange：Demo03Message.EXCHANGE
        // Queue：Demo03Message.QUEUE_A
        @Bean
        public Binding demo03BindingA() {
            return BindingBuilder.bind(demo03QueueA()).to(demo03Exchange());
        }

        // 创建 Binding B
        // Exchange：Demo03Message.EXCHANGE
        // Queue：Demo03Message.QUEUE_B
        @Bean
        public Binding demo03BindingB() {
            return BindingBuilder.bind(demo03QueueB()).to(demo03Exchange());
        }

    }

    /**
     * Headers Exchange 示例的配置类
     */
    public static class HeadersExchangeDemoConfiguration {

        // 创建 Queue
        @Bean
        public Queue demo04Queue() {
            return new Queue(Demo04MessageHeaders.QUEUE, // Queue 名字
                    true, // durable: 是否持久化
                    false, // exclusive: 是否排它
                    false); // autoDelete: 是否自动删除
        }

        // 创建 Headers Exchange
        @Bean
        public HeadersExchange demo04Exchange() {
            return new HeadersExchange(Demo04MessageHeaders.EXCHANGE,
                    true,  // durable: 是否持久化
                    false);  // exclusive: 是否排它
        }

        // 创建 Binding
        // Exchange：Demo04Message.EXCHANGE
        // Queue：Demo04Message.QUEUE
        // Headers: Demo04Message.HEADER_KEY + Demo04Message.HEADER_VALUE
        @Bean
        public Binding demo4Binding() {
            return BindingBuilder.bind(demo04Queue()).to(demo04Exchange())
                    .where(Demo04MessageHeaders.HEADER_KEY).matches(Demo04MessageHeaders.HEADER_VALUE); // 配置 Headers 匹配
        }

    }

}
