package com.tx.mq.manage.listen;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;


@Component
public abstract class AbstractMessageHandler implements ChannelAwareMessageListener {


    public final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.message.queue.retryTimes:5}")
    private Integer retryTimes;

    /**
     * 用户自定义消息处理
     *
     * @param deliveryTag 消息处理标识
     * @param message     消息
     * @param channel     处理通道
     * @return true-业务处理成功  false-业务处理失败
     */
    public abstract boolean handleMessage(long deliveryTag, String message, Channel channel) throws IOException;

    private ConcurrentHashMap<String, AcknowledgeMode> ackMap = new ConcurrentHashMap<>(8);

    /**
     * 消息处理
     *
     * @param message 消息体
     * @param channel channel通道
     * @throws Exception
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 业务处理是否成功
        boolean handleResult = false;
        // 消息处理标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        // 获取消费的队列名
        String queue = message.getMessageProperties().getConsumerQueue();
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        // TODO 进行自己的业务处理 比如记录日志
        try {
            // 自定义业务处理
            handleResult = this.handleMessage(deliveryTag, msg, channel);
        } catch (Exception e) {
            if (this.log.isDebugEnabled()) {
                e.printStackTrace();
            }
        }
        // TODO 如果消息处理失败，处理失败的采取措施， 确保消息不丢失
        this.onMessageCompleted(msg, queue, channel, deliveryTag, handleResult);
    }

    /**
     * 消息处理结束后进行复处理
     *
     * @param msg          消息实体
     * @param queue        队列名
     * @param channel      消息处理通道
     * @param deliveryTag  消息标识
     * @param handleResult 业务处理是否成功
     */
    private void onMessageCompleted(String msg, String queue, Channel channel, long deliveryTag, boolean handleResult) {
        this.log.info("消息：" + msg + "处理完成，等待事务提交和状态更新");
        if (!handleResult) {
            // TODO 业务处理失败，需要更新状态
            // TODO 放入一个队列
            return;
        }
        AcknowledgeMode ack = this.ackMap.get(queue);
        if (ack.isManual()) {
            //进行消息手动签收处理
            RetryTemplate oRetryTemplate = new RetryTemplate();
            SimpleRetryPolicy oRetryPolicy = new SimpleRetryPolicy();
            oRetryPolicy.setMaxAttempts(retryTimes);
            oRetryTemplate.setRetryPolicy(oRetryPolicy);
            try {
                // obj为doWithRetry的返回结果，可以为任意类型
                Integer result = oRetryTemplate.execute(new RetryCallback<Integer, Exception>() {
                    int count = 0;

                    @Override
                    public Integer doWithRetry(RetryContext context) throws Exception {//开始重试
                        channel.basicAck(deliveryTag, false);
                        log.info("消息 : {}  已签收 ", msg);
                        return ++this.count;
                    }
                }, new RecoveryCallback<Integer>() {
                    @Override
                    public Integer recover(RetryContext context) throws Exception { //重试多次后都失败了
                        log.info("消息 : {}  已签收 ", msg);
                        return Integer.MAX_VALUE;
                    }
                });
                if (result > retryTimes) {
                    //MQ服务器或网络出现问题，签收失败 更改状态
                    // TODO 放到一个统一队列
                }
            } catch (Exception e) {
                log.error("消息 {} \t 签收出现异常", msg);
                log.error(e.getMessage(), e);
            }
        } else {
            log.info("消息自动签收");
        }

    }

    /**
     * 注入消息签收模式
     *
     * @param queue 队列名
     * @param ack   消息签收模式
     */
    public final void setAck(String queue, AcknowledgeMode ack) {
        this.ackMap.put(queue, ack);
        log.info("注入队列 {} \t 消息签收模式: {} ", queue, ack.name());
    }
}
