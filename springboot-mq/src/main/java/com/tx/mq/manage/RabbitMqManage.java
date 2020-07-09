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
     * 消息条数限制,该参数是非负整数值。限制加入queue中消息的条数。先进先出原则，超过10条后面的消息会顶替前面的消息。
     */
    String X_MAX_LENGTH = "x-max-length";
    /**
     * 消息容量限制,该参数是非负整数值。该参数和x-max-length目的一样限制队列的容量，但是这个是靠队列大小（bytes）来达到限制。
     */
    String X_MAX_LENGTH_BYTES = "x-max-length-bytes";
    /**
     * 消息存活时间,该参数是非负整数值.创建queue时设置该参数可指定消息在该queue中待多久，
     * 可根据x-dead-letter-routing-key和x-dead-letter-exchange生成可延迟的死信队列。
     */
    String X_MESSAGE_TTL = "x-message-ttl";
    /**
     * 消息优先级,创建queue时arguments可以使用x-max-priority参数声明优先级队列 。该参数应该是一个整数，表示队列应该支持的最大优先级。
     * ​​建议使用1到10之间。目前使用更多的优先级将消耗更多的资源（Erlang进程）。
     * 设置该参数同时设置死信队列时或造成已过期的低优先级消息会在未过期的高优先级消息后面执行。
     * 该参数会造成额外的CPU消耗。
     */
    String X_MAX_PRIORITY = "x-max-priority";
    /**
     * 存活时间,创建queue时参数arguments设置了x-expires参数，该queue会在x-expires到期后清空queue消息，
     * 亲身测试直接消失（哪怕里面有未消费的消息）。
     */
    String X_EXPIRES = "x-expires";
    /**
     * 创建queue时参数arguments设置了x-dead-letter-routing-key和x-dead-letter-exchange，
     * 会在x-message-ttl时间到期后把消息放到x-dead-letter-routing-key和x-dead-letter-exchange指定的队列中达到延迟队列的目的。
     * 这里的routing-key也可以是队列名称，当消息过期后会转发到这个exchange对应的routing-key，达到延时队列效果
     */
    String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 创建一个长度不做限制，在消费完所有的消息且没有监听者后就会自动删除的queue
     *
     * @param queueName queue名字
     * @return {@link Queue}
     */
    Queue createQueue(String queueName);

    /**
     * 创建一个限制长度，持久化的queue
     *
     * @param queueName    queue名字
     * @param autoDelete   是否自动山粗
     * @param maxMsgLength 消息长度限制
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, boolean autoDelete, int maxMsgLength);

    /**
     * 创建一个限制长度，且存在消息过期时间的queue（持久化，会定时清空消息，可代替websocket做数据推送）
     * 即死信队列
     *
     * @param queueName    队列名
     * @param maxMsgLength 消息长度限制
     * @param ttl          消息过期时间 (ms)
     * @return {@link Queue}
     */
    Queue createQueue(String queueName, int maxMsgLength, int ttl);

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
     * @return
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
     * 情况队列中的消息
     *
     * @param queueName 队列名
     * @return 清空的消息数量
     */
    int purgeQueue(String queueName);

}
