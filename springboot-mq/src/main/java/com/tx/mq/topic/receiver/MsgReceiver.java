package com.tx.mq.topic.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.tx.config.TopicRabbitConfig;

import java.nio.charset.StandardCharsets;

//@Component
//@RabbitListener(queues = TopicRabbitConfig.TOPIC_M1)
public class MsgReceiver {

    private static Logger log = LoggerFactory.getLogger(MsgReceiver.class);

    @RabbitHandler
    public void process(Message message) {
        log.info("MsgReceiver : " + new String(message.getBody(), StandardCharsets.UTF_8));
    }
}
