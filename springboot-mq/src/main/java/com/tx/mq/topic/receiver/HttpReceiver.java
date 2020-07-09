package com.tx.mq.topic.receiver;

import com.tx.config.TopicRabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

//@Component
//@RabbitListener(queues = TopicRabbitConfig.TOPIC_M3)
public class HttpReceiver {


    private static Logger log = LoggerFactory.getLogger(HttpReceiver.class);

    @RabbitHandler
    public void process(Message message) {
        log.info("HttpReceiver : " + new String(message.getBody(), StandardCharsets.UTF_8));
    }
}
