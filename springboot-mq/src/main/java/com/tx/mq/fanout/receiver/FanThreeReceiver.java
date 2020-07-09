package com.tx.mq.fanout.receiver;

import com.tx.config.FanoutRabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = FanoutRabbitConfig.FANOUT_QUEUE_3)
public class FanThreeReceiver {

    private static Logger log = LoggerFactory.getLogger(FanThreeReceiver.class);

    @RabbitHandler
    public void process(String user) {
        log.info("ThreeRecevier : " + user);
    }
}
