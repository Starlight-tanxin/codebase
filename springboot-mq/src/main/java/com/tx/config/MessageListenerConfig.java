package com.tx.config;

import com.tx.mq.topic.receiver.TestAck;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * 消息队列监听配置类
 * @author zdtx
 */
@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    /***
     * 消息接收处理类
     */
    @Autowired
    private TestAck testAck;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // RabbitMQ默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置一个队列
        //如果同时设置多个如下： 前提是队列都是必须已经创建存在的
        container.setQueueNames(TopicRabbitConfig.TOPIC_M1,
                TopicRabbitConfig.TOPIC_M2,
                TopicRabbitConfig.TOPIC_M3);
        container.setMessageListener(testAck);
        return container;
    }
}
