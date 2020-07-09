package com.tx.mq.fanout.receiver;

import com.tx.config.FanoutRabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = FanoutRabbitConfig.FANOUT_QUEUE_2)
public class FanTwoReceiver {

    private static Logger log = LoggerFactory.getLogger(FanTwoReceiver.class);

    @RabbitHandler
    public void process(String msg) {
        log.info("TwoReceiver : " + msg);
    }
}
