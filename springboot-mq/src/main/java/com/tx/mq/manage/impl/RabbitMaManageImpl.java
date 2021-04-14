package com.tx.mq.manage.impl;

import com.rabbitmq.client.AMQP;
import com.tx.mq.manage.ExchangeType;
import com.tx.mq.manage.RabbitMqDefaultExchange;
import com.tx.mq.manage.RabbitMqDefaultQueue;
import com.tx.mq.manage.RabbitMqManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * describe: rabbitmq 操作
 *
 * @author TanXin
 * @date 2020/6/30 16:10
 */
@Component
public class RabbitMaManageImpl implements RabbitMqManage {

    private static final Logger log = LoggerFactory.getLogger(RabbitMaManageImpl.class);

    private final RabbitAdmin rabbitAdmin;


    public RabbitMaManageImpl(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
        log.info("初始化RabbitMQ开始------------------------------------------------");
        boolean isConfirmFail = isQueueExist(RabbitMqDefaultQueue.CONFIRM_FAIL_QUEUE);
        boolean isMsgSendFail = isQueueExist(RabbitMqDefaultQueue.MSG_SEND_FAIL_QUEUE);
        boolean isMsgHandleFail = isQueueExist(RabbitMqDefaultQueue.MSG_HANDLE_FAIL_QUEUE);
        if (!isConfirmFail) {
            createQueue(RabbitMqDefaultQueue.CONFIRM_FAIL_QUEUE, false);
        }
        if (!isMsgSendFail) {
            createQueue(RabbitMqDefaultQueue.MSG_SEND_FAIL_QUEUE, false);
        }
        if (!isMsgHandleFail) {
            createQueue(RabbitMqDefaultQueue.MSG_HANDLE_FAIL_QUEUE, false);
        }
        if (!isExchangeExist(RabbitMqDefaultExchange.FAIL_HANDLE_TOPIC_EXCHANGE)) {
            createExchange(RabbitMqDefaultExchange.FAIL_HANDLE_TOPIC_EXCHANGE, ExchangeType.TOPIC);
            bindQueue2Exchange(RabbitMqDefaultQueue.CONFIRM_FAIL_QUEUE, RabbitMqDefaultExchange.FAIL_HANDLE_TOPIC_EXCHANGE, RabbitMqDefaultQueue.CONFIRM_FAIL_QUEUE);
            bindQueue2Exchange(RabbitMqDefaultQueue.MSG_SEND_FAIL_QUEUE, RabbitMqDefaultExchange.FAIL_HANDLE_TOPIC_EXCHANGE, RabbitMqDefaultQueue.MSG_SEND_FAIL_QUEUE);
            bindQueue2Exchange(RabbitMqDefaultQueue.MSG_HANDLE_FAIL_QUEUE, RabbitMqDefaultExchange.FAIL_HANDLE_TOPIC_EXCHANGE, RabbitMqDefaultQueue.MSG_HANDLE_FAIL_QUEUE);
        }
        log.info("初始化RabbitMQ结束------------------------------------------------");
    }


    @Override
    public Queue createQueue(String queueName, boolean authDelete) {
        return createQueue(queueName, true, false, authDelete, null);
    }


    @Override
    public Queue createQueue(String queueName, boolean autoDelete, int maxMsgLength) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(X_MAX_LENGTH, maxMsgLength);
        return createQueue(queueName, true, false, autoDelete, arguments);
    }

    @Override
    public Queue createQueue(String queueName, boolean autoDelete, int maxMsgLength, int ttl) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(X_MAX_LENGTH, maxMsgLength);
        arguments.put(X_MESSAGE_TTL, ttl);
        return createQueue(queueName, true, false, autoDelete, arguments);
    }

    @Override
    public Queue createQueue(String queueName, int ttl, String exchange, String routingKey) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(X_MESSAGE_TTL, ttl);
        arguments.put(X_DEAD_LETTER_EXCHANGE, exchange);
        arguments.put(X_DEAD_LETTER_ROUTING_KEY, routingKey);
        return createQueue(queueName, true, false, false, arguments);
    }

    @Override
    public Queue createQueue(String queueName, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        Queue queue = new Queue(queueName, durable, exclusive, autoDelete, arguments);
        try {
            String result = rabbitAdmin.declareQueue(queue);
            log.info("创建queue的结果返回 ： {}", result);
            return queue;
        } catch (Exception e) {
            throw new RuntimeException("创建交换机失败");
        }
    }

    @Override
    public AbstractExchange createExchange(String exchangeName, ExchangeType exchangeType) {
        AbstractExchange exchange = null;
        switch (exchangeType) {
            case TOPIC:
                exchange = new TopicExchange(exchangeName, true, false);
                break;
            case DIRECT:
                exchange = new DirectExchange(exchangeName, true, false);
                break;
            case FANOUT:
                exchange = new FanoutExchange(exchangeName, true, false);
                break;
            default:
                throw new RuntimeException("不支持的交换机类型");
        }
        try {
            rabbitAdmin.declareExchange(exchange);
            return exchange;
        } catch (Exception e) {
            throw new RuntimeException("创建交换机失败");
        }
    }

    @Override
    public boolean bindQueue2Exchange(Queue queue, AbstractExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(exchange).to(exchange).with(routingKey).and(new HashMap<>());
        try {
            rabbitAdmin.declareBinding(binding);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Queue绑定到交换机失败");
        }
    }

    @Override
    public boolean bindQueue2Exchange(String queueName, String exchangeName, String routingKey) {
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
        try {
            rabbitAdmin.declareBinding(binding);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Queue绑定到交换机失败");
        }
    }

    @Override
    public boolean bindExchange2Exchange(AbstractExchange bindExchange, AbstractExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(bindExchange).to(exchange).with(routingKey).and(new HashMap<>());
        try {
            rabbitAdmin.declareBinding(binding);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("交换机绑定到交换机失败");
        }
    }

    @Override
    public boolean bindExchange2Exchange(String bindExchangeName, String exchangeName, String routingKey) {
        Binding binding = new Binding(bindExchangeName, Binding.DestinationType.EXCHANGE, exchangeName, routingKey, null);
        try {
            rabbitAdmin.declareBinding(binding);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("交换机绑定到交换机失败");
        }
    }


    @Override
    public boolean deleteQueue(String queueName) {
        return rabbitAdmin.deleteQueue(queueName);
    }

    @Override
    public void deleteQueue(String queueName, boolean unused, boolean empty) {
        rabbitAdmin.deleteQueue(queueName, unused, empty);
    }

    @Override
    public boolean deleteExchange(String exchange) {
        return rabbitAdmin.deleteExchange(exchange);
    }

    private boolean deleteBindQueueOrExchange(Binding binding) {
        try {
            rabbitAdmin.removeBinding(binding);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("删除绑定失败");
        }
    }

    @Override
    public boolean deleteBind(String queueName, String exchangeName, String routingKey) {
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingKey, null);
        return deleteBindQueueOrExchange(binding);
    }


    @Override
    public boolean deleteBindExchange(String bindExchangeName, String exchangeName, String routingKey) {
        Binding binding = new Binding(bindExchangeName, Binding.DestinationType.EXCHANGE, exchangeName, routingKey, null);
        return deleteBindQueueOrExchange(binding);
    }

    @Override
    public synchronized boolean isQueueExist(String queueName) {
        log.info("isQueueExist queue is : {}", queueName);
        String isExist = rabbitAdmin.getRabbitTemplate().execute((channel -> {
            try {
                AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive(queueName);
                return declareOk.getQueue();
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    throw new RuntimeException("判断Queue是否存在出现未知错误");
                }
                return null;
            }
        }));
        log.info("The queue {} is exist : {}", queueName, isExist);
        return !StringUtils.isEmpty(isExist);


    }

    @Override
    public synchronized boolean isExchangeExist(String exchangeName) {
        String isExist = rabbitAdmin.getRabbitTemplate().execute((channel -> {
            try {
                AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclarePassive(exchangeName);
                return declareOk.protocolMethodName();
            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    throw new RuntimeException("判断Exchange是否存在出现未知错误");
                }
                return null;
            }
        }));
        log.info("The  exchange : {}", isExist);
        return !StringUtils.isEmpty(isExist);
    }

    @Override
    public int purgeQueue(String queueName) {
        return rabbitAdmin.purgeQueue(queueName);
    }


}
