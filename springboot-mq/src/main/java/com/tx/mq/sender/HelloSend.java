package com.tx.mq.sender;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloSend {
	
	private static Logger log = LoggerFactory.getLogger(HelloSend.class);
	
	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send() {
		String context = "hello " + new Date();
		log.info(context);
		this.rabbitTemplate.convertAndSend("hello", context);
	}
}
