package com.tx.mq.manage.send.impl;

import com.tx.mq.manage.send.AbstractSendService;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * describe: 默认的发送实现,大部分情况够用了
 *
 * @author TanXin
 * @date 2020/7/13 14:26
 */
@Component
public class DefaultSendServiceImpl extends AbstractSendService {

    @Override
    public void handleConfirmCallback(String messageId, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功：{}", messageId);
        } else {
            //TODO 对消息进行处理
            log.error("ConfirmCallback消息发送队列失败 {}", messageId);
        }
    }


    @Override
    public void handleReturnCallback(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息发送到队列失败");
    }
}
