package com.tx.mq.manage;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/13 10:25
 */
public class MqDefaultExchange {

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
}
