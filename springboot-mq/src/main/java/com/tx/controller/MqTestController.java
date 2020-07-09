package com.tx.controller;

import com.tx.config.FanoutRabbitConfig;
import com.tx.mq.manage.RabbitMqManage;
import com.tx.mq.sender.AddUserSend;
import com.tx.mq.sender.HelloSend;
import com.tx.mq.topic.send.TopicSend;
import com.tx.pojo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mq")
public class MqTestController {

    static Logger log = LoggerFactory.getLogger(MqTestController.class);

    @Autowired
    private HelloSend helloSend;

    @Autowired
    private AddUserSend addUserSend;

    @Autowired
    private TopicSend topicSend;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMqManage rabbitMqManage;

    @GetMapping(value = "sendMq")
    public String sendMq() {
        helloSend.send();
        return "ok";
    }

    @GetMapping(value = "sendAdd")
    public String sendAddUser() {
        User user = new User();
        user.setNickname("白色相簿2");
        user.setRealname("谭鑫");
        user.setAddress("湖南");
        user.setUsername("tanxin");
        user.setPassword("123456");
        user.setSex(1);
        addUserSend.send(user);
        return "ok";
    }

    @GetMapping(value = "sendTopic")
    public String sendTopic() {
        User user = new User();
        user.setNickname("大老师");
        user.setRealname("谭志文");
        user.setAddress("湖南");
        user.setUsername("tzw");
        user.setPassword("123456");
        user.setSex(1);

        Map<String, String> param = new HashMap<>(2);
        param.put("status", "200");
        param.put("msg", "ok");

        topicSend.sendMsg("hello!RabbitMQ");
        topicSend.sendUser(user);
        topicSend.sendHttp(param);
        return "ok";
    }

    @GetMapping(value = "sendFanout")
    public String sendFanout() {
        rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE, "", "测试");
        return "ok";
    }

    @GetMapping(value = "addQueue")
    public String addQueue(String queue) {
        rabbitMqManage.createQueue(queue, 10, 30000);
        return "ok";
    }

    @GetMapping(value = "bindQueue")
    public String bindQueue(String queue, String exchange, String key) {
//        Queue myQueue = rabbitMqManage.createQueue(queue);
//        AbstractExchange myExchange = rabbitMqManage.createExchange(exchange, ExchangeType.FANOUT);
        rabbitMqManage.bindQueue2Exchange(queue, exchange, key);
        return "ok";
    }

    @GetMapping(value = "delBind")
    public String delBind(String queue, String exchange, String key) {
        boolean b = rabbitMqManage.isQueueExist(queue);
        log.info("是否存在 ： {} ", b);
        rabbitMqManage.deleteBind(queue, exchange, key);
        return "ok";
    }
}
