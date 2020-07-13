package com.tx.mq.manage;

/**
 * describe: 默认的交换机
 *
 * @author TanXin
 * @date 2020/7/13 10:25
 */
public interface RabbitMqDefaultExchange {

    /**
     * 默认的DIRECT类型的交换机
     */
    String DEFAULT_DIRECT_EXCHANGE = "amq.direct";
    /**
     * 默认的TOPIC类型的交换机
     */
    String DEFAULT_TOPIC_EXCHANGE = "amq.topic";
    /**
     * 默认的HEADERS类型的交换机
     */
    String DEFAULT_HEADERS_EXCHANGE = "amq.headers";
    /**
     * 默认的FANOUT类型的交换机
     */
    String DEFAULT_FANOUT_EXCHANGE = "amq.fanout";
}
