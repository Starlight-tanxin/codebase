package com.tx.mq.manage.constants;

/**
 * <p>describe</p>
 *
 * @author tx
 * @since 2021/4/14 12:24
 **/
public final class MqQueueConfigConstants {

    private MqQueueConfigConstants() {
    }

    /**
     * 消息条数限制,该参数是非负整数值。限制加入queue中消息的条数。先进先出原则，超过10条后面的消息会顶替前面的消息。
     */
    public static final String X_MAX_LENGTH = "x-max-length";
    /**
     * 消息容量限制,该参数是非负整数值。该参数和x-max-length目的一样限制队列的容量，但是这个是靠队列大小（bytes）来达到限制。
     */
    public static final String X_MAX_LENGTH_BYTES = "x-max-length-bytes";
    /**
     * 消息存活时间,该参数是非负整数值.创建queue时设置该参数可指定消息在该queue中待多久，
     * 可根据x-dead-letter-routing-key和x-dead-letter-exchange生成可延迟的死信队列。
     */
    public static final String X_MESSAGE_TTL = "x-message-ttl";
    /**
     * 消息优先级,创建queue时arguments可以使用x-max-priority参数声明优先级队列 。该参数应该是一个整数，表示队列应该支持的最大优先级。
     * 建议使用1到10之间。目前使用更多的优先级将消耗更多的资源（Erlang进程）。
     * 设置该参数同时设置死信队列时或造成已过期的低优先级消息会在未过期的高优先级消息后面执行。
     * 该参数会造成额外的CPU消耗。
     */
    public static final String X_MAX_PRIORITY = "x-max-priority";
    /**
     * 存活时间,创建queue时参数arguments设置了x-expires参数，该queue会在x-expires到期后清空queue消息，
     * 亲身测试直接消失（哪怕里面有未消费的消息）。
     */
    public static final String X_EXPIRES = "x-expires";
    /**
     * 创建queue时参数arguments设置了x-dead-letter-routing-key和x-dead-letter-exchange，
     * 会在x-message-ttl时间到期后把消息放到x-dead-letter-routing-key和x-dead-letter-exchange指定的队列中达到延迟队列的目的。
     * 这里的routing-key也可以是队列名称，当消息过期后会转发到这个exchange对应的routing-key，达到延时队列效果
     */
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
}
