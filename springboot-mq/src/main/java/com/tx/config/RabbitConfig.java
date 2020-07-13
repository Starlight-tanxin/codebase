package com.tx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class RabbitConfig {


    private static Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public Queue Hello() {
        return new Queue("hello");
    }

    @Bean
    public Queue AddUser() {
        return new Queue("addUser");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean(name = "rabbitTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        //发送到交换器失败处理
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                if (ack) {
//                    log.info("消息发送成功：{}", correlationData.getId());
//                } else {
//                    log.error("ConfirmCallback消息发送队列失败 messageId：{}", correlationData.getId());
//                }
//            }
//        });
//        //发送到队列失败处理
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                String messageId = message.getMessageProperties().getCorrelationId();
//                log.error("ReturnCallback消息发送队列失败 messageId：{}", message);
//            }
//        });

        return rabbitTemplate;
    }

}
