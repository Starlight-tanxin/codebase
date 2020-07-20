package com.tx.mq.demo;

import com.rabbitmq.client.Channel;
import com.tx.mq.manage.listen.AbstractMessageHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * describe: 推荐以每个MQ或者其他的进行操作
 *
 * @author TanXin
 * @date 2020/7/13 11:14
 */
@Component
public class TestMessageHandler extends AbstractMessageHandler {


    @Override
    public void handleMessage(long deliveryTag, String message, Channel channel) throws IOException {
        log.info("不对消息进行操作 ： {}", message);
        channel.basicAck(deliveryTag, true);
    }
}
