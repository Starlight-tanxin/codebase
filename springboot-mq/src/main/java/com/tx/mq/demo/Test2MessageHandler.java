package com.tx.mq.demo;

import com.rabbitmq.client.Channel;
import com.tx.mq.manage.listen.AbstractMessageHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/20 14:53
 */
@Component
public class Test2MessageHandler extends AbstractMessageHandler {

    @Override
    public boolean handleMessage(long deliveryTag, String message, Channel channel) throws IOException {
        log.info("不对消息进行操作 ： {}", message);
        return true;
    }
}
