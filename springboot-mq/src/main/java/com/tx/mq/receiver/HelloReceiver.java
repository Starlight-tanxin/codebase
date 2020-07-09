package com.tx.mq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {
	
	private static Logger log = LoggerFactory.getLogger(HelloReceiver.class);
	
	@RabbitHandler
    public void process(String hello) {
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        log.info("Receiver  : " + hello);
    }


}
