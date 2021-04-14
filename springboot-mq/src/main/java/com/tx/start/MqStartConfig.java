package com.tx.start;

import com.tx.mq.demo.Test2MessageHandler;
import com.tx.mq.demo.TestMessageHandler;
import com.tx.mq.manage.listen.MessageListen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/13 11:26
 */
@Component
public class MqStartConfig implements CommandLineRunner, Ordered {

    @Autowired
    private TestMessageHandler testMessageHandler;

    @Autowired
    private Test2MessageHandler test2MessageHandler;

    @Autowired
    private MessageListen messageListen;

    @Override
    public void run(String... args) {
        messageListen.addMessageLister("hello", testMessageHandler, true);
        messageListen.addMessageLister("d2d", test2MessageHandler, true);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
