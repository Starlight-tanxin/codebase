package com.tx.mq.manage.listen;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public abstract class AbstractMessageHandler implements ChannelAwareMessageListener {


    private static final Logger log = LoggerFactory.getLogger(AbstractMessageHandler.class);

    @Value("${spring.message.queue.retryTimes:5}")
    private Integer retryTimes;

    private volatile AcknowledgeMode acknowledgeMode;

    /**
     * 用户自定义消息处理
     *
     * @param deliveryTag 消息处理标识
     * @param message     消息
     * @param channel     处理通道
     * @return true-业务处理成功  false-业务处理失败
     */
    public abstract boolean handleMessage(long deliveryTag, String message, Channel channel) throws IOException;

    /**
     * 消息处理
     *
     * @param message 消息体
     * @param channel channel通道
     */
    @Override
    public void onMessage(Message message, Channel channel) {
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
            handleResult = handleMessage(deliveryTag, msg, channel);
        } catch (Exception e) {
            log.error("AbstractMessageHandler ==> handleMessage() 业务消息处理异常", e);
        }
        // TODO 如果消息处理失败，处理失败的采取措施， 确保消息不丢失
        onMessageCompleted(msg, queue, channel, deliveryTag, handleResult);
    }

    /**
     * 没有被签收的消息，重回本队列末尾
     *
     * @param channel     消息处理通道
     * @param deliveryTag 消息标识
     */
    private void nonAckReturnQueueLast(Channel channel, long deliveryTag) {
        try {
            channel.basicNack(deliveryTag, false, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        log.info("队列 : {} \t 消息: {} ,处理完成，等待事务提交和状态更新", queue, msg);
        if (!handleResult) {
            nonAckReturnQueueLast(channel, deliveryTag);
            return;
        }
        log.info("队列 : {}, 消息签收模式 : {}", queue, acknowledgeMode);
        if (acknowledgeMode.isManual()) {
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
                        log.info("队列 : {} \t 消息 : {} \t 已签收 ", queue, msg);
                        return ++this.count;
                    }
                }, context -> { //重试多次后都失败了
                    log.info("消息 : {}  已签收 ", msg);
                    return Integer.MAX_VALUE;
                });
                if (result > retryTimes) {
                    //MQ服务器或网络出现问题，签收失败 更改状态
                    // TODO 可以放到一个统一队列 暂时也重回本队列
                    nonAckReturnQueueLast(channel, deliveryTag);
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
        acknowledgeMode = ack;
        log.info("注入队列 {} \t 消息签收模式: {} ", queue, ack);
    }
}
