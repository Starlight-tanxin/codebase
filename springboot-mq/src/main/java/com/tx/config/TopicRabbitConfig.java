package com.tx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
    // 队列名
    public static final String TOPIC_M1 = "topic.msg";
    public static final String TOPIC_M2 = "topic.user";
    public static final String TOPIC_M3 = "topic.http";
    public static final String TOPIC_ALL = "topic.#";
    // 交换机名称
    public static final String TOPIC_EXCHANGE = "topicExchange";

    @Bean
    public Queue msgQueue() {
        return new Queue(TOPIC_M1);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(TOPIC_M2);
    }

    @Bean
    public Queue httpQueue() {
        return new Queue(TOPIC_M3);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMsg(Queue msgQueue, TopicExchange exchange) {
        return BindingBuilder.bind(msgQueue).to(exchange).with(TOPIC_M1);
    }

    @Bean
    Binding bindingExchangUser(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with(TOPIC_M2);
    }

    @Bean
    Binding bindingExchangAll(Queue httpQueue, TopicExchange exchange) {
        return BindingBuilder.bind(httpQueue).to(exchange).with(TOPIC_ALL);
    }


}
