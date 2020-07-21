package com.tx.mq.manage;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/21 10:41
 */
public interface RabbitMqDefaultQueue {

    /**
     * 消息确认失败队列
     */
    String CONFIRM_FAIL_QUEUE = "confirm_fail_queue";
    /**
     * 消息发送失败队列
     */
    String MSG_SEND_FAIL_QUEUE = "msg_send_fail_queue";
    /**
     * 消息处理失败队列
     */
    String MSG_HANDLE_FAIL_QUEUE = "msg_handle_fail_queue";
}
