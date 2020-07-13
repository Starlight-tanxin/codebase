package com.tx.mq.demo;

import com.rabbitmq.client.Channel;
import com.tx.mq.manage.listen.AbstractMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/13 11:14
 */
@Component
public class TestMessageHandler extends AbstractMessageHandler {

    private static Logger log = LoggerFactory.getLogger(TestMessageHandler.class);

    @Override
    public void handleMessage(long deliveryTag, String message, Channel channel) throws IOException {
        log.info("不对消息进行操作 ： {}", message);
        channel.basicAck(deliveryTag, true);
    }
}
