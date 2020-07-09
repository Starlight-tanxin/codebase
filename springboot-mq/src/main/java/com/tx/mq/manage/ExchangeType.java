package com.tx.mq.manage;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/1 8:48
 */
public enum ExchangeType {
    /**
     * 路由完全匹配交换机（点对点交换机，点对点模式可以完全不用交换机）
     */
    DIRECT,
    /**
     * 路由匹配交换机 （#：多个单词；*：一个单词）
     */
    TOPIC,
    /**
     * 广播交换机
     */
    FANOUT,
}
