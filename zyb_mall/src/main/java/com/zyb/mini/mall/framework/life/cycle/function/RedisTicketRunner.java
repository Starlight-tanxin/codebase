package com.zyb.mini.mall.framework.life.cycle.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.weixin4j.model.base.Token;
import org.weixin4j.model.js.Ticket;
import org.weixin4j.spring.WeixinTemplate;

/**
 * @author Created by 谭健 on 2019/10/28. 星期一. 14:36.
 * © All Rights Reserved.
 */
@Slf4j
@Configuration
public class RedisTicketRunner {


    private final WeixinTemplate weixin;

    public RedisTicketRunner(WeixinTemplate weixin) {
        this.weixin = weixin;
    }

    @Bean
    ApplicationRunner redisTicketRunnerDo() {

        return args -> {
            Token token = weixin.getToken();
            Ticket jsApiTicket = weixin.getJsApiTicket();
            log.info("-------------- 微信配置开始 ---------------------------");
            log.info("微信登陆信息 ：Access_token [{}]", token.getAccess_token());
            log.info("微信登陆信息 ：Access_token有效时间 [{}] 秒", token.getExpires_in());
            log.info("微信登陆信息 ：操作JSAPI 票据 [{}]", jsApiTicket.getTicket());
            log.info("微信登陆信息 ：JSAPI有效时间 [{}] 秒", jsApiTicket.getExpires_in());
            log.info("-------------- 微信配置结束 ---------------------------");
        };
    }
}
