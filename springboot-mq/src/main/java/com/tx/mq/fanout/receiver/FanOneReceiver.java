package com.tx.mq.fanout.receiver;

import com.tx.config.FanoutRabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = FanoutRabbitConfig.FANOUT_QUEUE_1)
public class FanOneReceiver {


    private static Logger log = LoggerFactory.getLogger(FanOneReceiver.class);

    @RabbitHandler
    public void process(String param) {
        log.info("OneReceiver : " + param);
    }
}
