package com.tx.mq.manage;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Queue;

import java.util.Map;

/**
 * describe: rabbitMq管理
 *
 * @author TanXin
 * @date 2020/6/30 16:09
 */
public interface RabbitMqManage {

    /**
     * 创建一个长度不做限制的queue。
     * 可选在消费完所有的消息且没有监听者后就会自动删除的queue
     *
     * @param queueName  queue名字
     * @param authDelete 是否自动删除
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, boolean authDelete);

    /**
     * 创建一个限制长度，持久化的queue
     *
     * @param queueName    queue名字
     * @param autoDelete   是否自动删除
     * @param maxMsgLength 消息长度限制
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, boolean autoDelete, int maxMsgLength);

    /**
     * 创建一个限制长度，且存在消息过期时间的queue（持久化，会定时清空消息，可代替websocket做数据推送）
     * 即死信队列
     *
     * @param queueName    队列名
     * @param autoDelete   是否自动删除
     * @param maxMsgLength 消息长度限制
     * @param ttl          消息过期时间 (ms)
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, boolean autoDelete, int maxMsgLength, int ttl);

    /**
     * 创建一个存在消息存活时间到期后会自动发送到其他队列的queue（持久化，不会删除，可用来实现延时队列）
     *
     * @param queueName  队列名
     * @param ttl        消息过期时间 （ms）
     * @param exchange   过期后投递的交换机
     * @param routingKey 过去后投递到固定的路由键
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, int ttl, String exchange, String routingKey);

    /**
     * 创建一个queue
     *
     * @param queueName  queue名字
     * @param durable    是否持久化
     * @param exclusive  是否排外
     * @param autoDelete 是否自动删除，在连接过后就会自动删除（但是要做到里面没有未消费的消息）
     * @param arguments  其他设置
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments);

    /**
     * 创建交换机
     *
     * @param exchangeName 交换机名字
     * @param exchangeType {@link ExchangeType}
     * @return {@link AbstractExchange} 交换机
     */
    AbstractExchange createExchange(String exchangeName, ExchangeType exchangeType);


    boolean bindQueue2Exchange(Queue queue, AbstractExchange exchange, String routingKey);

    boolean bindQueue2Exchange(String queueName, String exchangeName, String routingKey);

    boolean bindExchange2Exchange(AbstractExchange bindExchange, AbstractExchange exchange, String routingKey);

    boolean bindExchange2Exchange(String bindExchangeName, String exchangeName, String routingKey);

    /**
     * 删除queue
     *
     * @param queueName queue名字
     * @return true-删除成功
     */
    boolean deleteQueue(String queueName);

    /**
     * 从rabbitMQ服务器中删除指定的队列
     *
     * @param queueName 队列名称
     * @param unused    队列是否在使用，如果设置为true则该队列只能在没有被使用的情况下才能删除
     * @param empty     队列是否为空，如果设置为true则该队列只能在该队列没有消息时才会被删除
     */
    void deleteQueue(String queueName, boolean unused, boolean empty);

    /**
     * 传输交换机
     *
     * @param exchangeName 交换机名字
     * @return true-删除成功
     */
    boolean deleteExchange(String exchangeName);

    /**
     * 删除队列的绑定关系
     *
     * @param queueName    queue名字
     * @param exchangeName 交换机名字
     * @param routingKey   路由键
     * @return true-删除成功
     */
    boolean deleteBind(String queueName, String exchangeName, String routingKey);

    /**
     * 删除交换机的绑定关系
     *
     * @param bindExchangeName 交换机名字
     * @param exchangeName     交换机名字
     * @param routingKey       路由键
     * @return true-删除成功
     */
    boolean deleteBindExchange(String bindExchangeName, String exchangeName, String routingKey);


    /**
     * 判断队列是否存在
     *
     * @param queueName 队列名
     * @return true-存在 false-不存在
     */
    boolean isQueueExist(String queueName);

    /**
     * 判断交换机是否存在
     *
     * @param exchangeName 交换机名字
     * @return true-存在  false-失败
     */
    boolean isExchangeExist(String exchangeName);

    /**
     * 清空队列中的消息
     *
     * @param queueName 队列名
     * @return 清空的消息数量
     */
    int purgeQueue(String queueName);

}
