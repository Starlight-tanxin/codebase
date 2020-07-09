package com.tx.mq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tx.dao.UserMapper;
import com.tx.pojo.entity.User;

@Component
@RabbitListener(queues = "addUser")
public class AddUserReceiver {
	
	private static Logger log = LoggerFactory.getLogger(AddUserReceiver.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@RabbitHandler
	public void process(User user) {
		log.info("Receiver : " + user.toString());
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		userMapper.insertSelective(user);
	}
}
