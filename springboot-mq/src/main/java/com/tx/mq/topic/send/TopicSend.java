package com.tx.mq.topic.send;

import com.alibaba.fastjson.JSON;
import com.tx.config.TopicRabbitConfig;
import com.tx.pojo.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Component
public class TopicSend {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replaceAll("-", ""));
        Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("UTF-8")
                .setCorrelationId(correlationData.getId()).build(); //消息id设置在请求头里面 用UUID做全局ID
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, TopicRabbitConfig.TOPIC_M1, message, correlationData);
    }


    public void sendUser(User user) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replaceAll("-", ""));
        Message message = MessageBuilder.withBody(JSON.toJSONString(user).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("UTF-8")
                .setCorrelationId(correlationData.getId()).build(); //消息id设置在请求头里面 用UUID做全局ID
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, TopicRabbitConfig.TOPIC_M2, message, correlationData);
    }

    public void sendHttp(Map<String, String> params) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replaceAll("-", ""));
        Message message = MessageBuilder.withBody(JSON.toJSONString(params).getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("UTF-8")
                .setCorrelationId(correlationData.getId()).build(); //消息id设置在请求头里面 用UUID做全局ID
        rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, TopicRabbitConfig.TOPIC_M3, message, correlationData);
    }
}
