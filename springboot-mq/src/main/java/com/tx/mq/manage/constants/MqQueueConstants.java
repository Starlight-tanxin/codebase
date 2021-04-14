package com.tx.mq.manage.constants;

/**
 * <p>describe</p>
 *
 * @author tx
 * @since 2021/4/14 12:23
 **/
public final class MqQueueConstants {

    private MqQueueConstants() {
    }

    /**
     * 消息确认失败队列
     */
    public static final String CONFIRM_FAIL_QUEUE = "confirm_fail_queue";
    /**
     * 消息发送失败队列
     */
    public static final String MSG_SEND_FAIL_QUEUE = "msg_send_fail_queue";
    /**
     * 消息处理失败队列
     */
    public static final String MSG_HANDLE_FAIL_QUEUE = "msg_handle_fail_queue";
}
