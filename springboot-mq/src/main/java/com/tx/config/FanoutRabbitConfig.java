package com.tx.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitConfig {

    public static final String FANOUT_QUEUE_1 = "FANOUT_QUEUE_1";

    public static final String FANOUT_QUEUE_2 = "FANOUT_QUEUE_2";

    public static final String FANOUT_QUEUE_3 = "FANOUT_QUEUE_3";

    public static final String FANOUT_EXCHANGE = "fanoutExchange_01";

    @Bean
    public Queue fanOneQueue() {
        return new Queue(FANOUT_QUEUE_1);
    }

    @Bean
    public Queue fanTwoQueue() {
        return new Queue(FANOUT_QUEUE_2);
    }

    @Bean
    public Queue fanThreeQueue() {
        return new Queue(FANOUT_QUEUE_3);
    }


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }


    @Bean
    Binding bindingExchangeOne(Queue fanOneQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanOneQueue).to(fanoutExchange);
    }


    @Bean
    Binding bindingExchangeTwo(Queue fanTwoQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanTwoQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeThree(Queue fanThreeQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanThreeQueue).to(fanoutExchange);
    }

}
