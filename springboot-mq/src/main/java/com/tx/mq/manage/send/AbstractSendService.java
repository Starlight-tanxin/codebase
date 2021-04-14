package com.tx.mq.manage.send;

import com.alibaba.fastjson.JSON;
import com.tx.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;


public abstract class AbstractSendService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private static final Logger log = LoggerFactory.getLogger(AbstractSendService.class);

    @Autowired
    protected RabbitTemplate rabbitTemplate;

//    /**
//     * 交换机如果根据自身的类型和路由键匹配上对应的队列时，是否调用returnCallback回调函数
//     * true: 调用returnCallback回调函数
//     * false： 不调用returnCallback回调函数 这样在发送消息失败时，会导致消息丢失
//     */
//    @Value("${spring.message.mandatory:true}  ")
//    private Boolean mandatory;
//
//
//    /**
//     * 默认队列的优先级
//     */
//    public static final int MESSAGE_PRIORITY = 1;

    @PostConstruct
    public final void init() {
        log.info("sendService 初始化...... ");
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 简单的发送消息
     * 发送的队列即为RoutingKey,需要绑定队列时
     *
     * @param queue   队列，默认是跟路由键是相同的
     * @param content 发送的内容
     */
    public void send(String queue, String content) {
        if (StringUtils.isEmpty(queue)) {
            throw new RuntimeException("队列名不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw new RuntimeException("内容不能为空");
        }
        this.send(null, queue, content, null, UUIDUtils.generateUuid());
    }

    /**
     * 发送一条有过期时间的消息
     *
     * @param queue      队列，默认是跟路由键相同的
     * @param content    发送的内容
     * @param expireTime 过期时间 时间毫秒
     */
    public void send(String queue, String content, int expireTime) {
        if (StringUtils.isEmpty(queue)) {
            throw new RuntimeException("发送的队列不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw new RuntimeException("内容不能为空");
        }
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置消息的过期时间
                message.getMessageProperties().setExpiration(String.valueOf(expireTime));
                return message;
            }
        };
        this.send(null, queue, content, messagePostProcessor, UUIDUtils.generateUuid());
    }

    /**
     * 按照给定的交换机、路由键、发送内容、发送的自定义属性来发送消息
     *
     * @param exchange             交换机名称
     * @param routingKey           路由键
     * @param msg                  发送的内容
     * @param messagePostProcessor 发送消息自定义处理
     * @param messageId            消息ID
     */
    public void send(String exchange, String routingKey, String msg, MessagePostProcessor messagePostProcessor, String messageId) {
        if (StringUtils.isEmpty(routingKey)) {
            throw new RuntimeException("路由键不能为空");
        }
        if (StringUtils.isEmpty(msg)) {
            throw new RuntimeException("发送的内容不能为空");
        }
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(StringUtils.isEmpty(messageId) ? UUIDUtils.generateUuid() : messageId);
        if (ObjectUtils.isEmpty(messagePostProcessor)) {
            this.rabbitTemplate.convertAndSend(exchange, routingKey, msg, correlationData);
        } else {
            // 发送对应的消息
            this.rabbitTemplate.convertAndSend(exchange, routingKey, msg, messagePostProcessor, correlationData);
        }
    }

    /**
     * 默认实现发送确认的处理方法
     * 子类需要重写该方法，实现自己的业务处理逻辑
     *
     * @param messageId 消息
     * @param ack
     * @param cause
     */
    public abstract void handleConfirmCallback(String messageId, boolean ack, String cause);

    /**
     * 默认实现发送匹配不上队列时 回调函数的处理
     *
     * @param message    消息主体
     * @param replyCode  回复码
     * @param replyText  回复内容
     * @param exchange   交换器
     * @param routingKey 路由键
     */
    public abstract void handleReturnCallback(Message message, int replyCode, String replyText, String exchange, String routingKey);


    /**
     * 确认后回调方法
     *
     * @param correlationData MQ消息的内容数据（包括消息id，和消息主体内容）
     * @param ack             是否发送成功
     * @param cause           异常信息
     */
    @Override
    public final void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("confirm----->  correlationData： {} \t ack: {} \t cause: {} ", JSON.toJSONString(correlationData), ack, cause);
        // 调用自定义处理
        this.handleConfirmCallback(correlationData.getId(), ack, cause);
    }

    /**
     * 发送失败时候 回调函数的处理
     *
     * @param message    消息主体
     * @param replyCode  回复码
     * @param replyText  回复内容
     * @param exchange   交换器
     * @param routingKey 路由键
     */
    @Override
    public final void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("return----->  message: {} \t replyCode: {} \t replyText: {} \t exchange: {} \t routingKey: {} ", JSON.toJSONString(message), replyCode, replyText, exchange, routingKey);
        // 调用自定义处理
        this.handleReturnCallback(message, replyCode, replyText, exchange, routingKey);
    }

}
