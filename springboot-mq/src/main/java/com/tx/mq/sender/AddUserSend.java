package com.tx.mq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tx.pojo.entity.User;

@Component
public class AddUserSend {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void send(User user) {
		amqpTemplate.convertAndSend("addUser", user);
	}
}
