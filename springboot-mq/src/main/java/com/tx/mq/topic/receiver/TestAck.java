package com.tx.mq.topic.receiver;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/1 9:39
 */
@Component
public class TestAck implements ChannelAwareMessageListener {

    private static Logger log = LoggerFactory.getLogger(TestAck.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String bodyStr = new String(message.getBody(), StandardCharsets.UTF_8);
            String queueName = message.getMessageProperties().getConsumerQueue();
            log.info("queueName : {}  body : {}", queueName, bodyStr);
            //确认消费
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            //业务处理异常则不确认消费，并且不放回队列
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }
}
