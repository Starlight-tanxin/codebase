package com.tx.mq.manage.constants;

/**
 * <p>describe</p>
 *
 * @author tx
 * @since 2021/4/14 12:22
 **/
public final class MqExchangeConstants {
    private MqExchangeConstants() {
    }

    /**
     * 默认的DIRECT类型的交换机
     */
    public static final String DEFAULT_DIRECT_EXCHANGE = "amq.direct";
    /**
     * 默认的TOPIC类型的交换机
     */
    public static final String DEFAULT_TOPIC_EXCHANGE = "amq.topic";
    /**
     * 默认的HEADERS类型的交换机
     */
    public static final String DEFAULT_HEADERS_EXCHANGE = "amq.headers";
    /**
     * 默认的FANOUT类型的交换机
     */
    public static final String DEFAULT_FANOUT_EXCHANGE = "amq.fanout";
    /**
     * 失败处理的topic
     */
    public static final String FAIL_HANDLE_TOPIC_EXCHANGE = "fail_handle.topic";
}
