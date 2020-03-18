package com.zyb.mini.mall.redis;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.weixin4j.loader.ITokenLoader;
import org.weixin4j.model.base.Token;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by 谭健 on 2019/10/28. 星期一. 14:12.
 * © All Rights Reserved.
 */

@Slf4j
@Component
public class RedisTokenLoader implements ITokenLoader {




    private final StringRedisTemplate stringRedisTemplate;

    public RedisTokenLoader(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Token get() {
        String accessToken = stringRedisTemplate.opsForValue().get(RedisKeyNameConstant.REDIS_ACCESS_TOKEN_KEY);
        return JSON.parseObject(accessToken, Token.class);
    }

    @Override
    public void refresh(Token token) {
        log.info("微信登陆缓存过期，开始刷新，刷新后的 token信息 [{}]", token.toString());
        String accessToken = JSON.toJSONString(token);
        // 提前过期，防止热点事件
        long timeout = token.getExpires_in() - 600L;
        stringRedisTemplate.opsForValue().set(RedisKeyNameConstant.REDIS_ACCESS_TOKEN_KEY, accessToken, timeout, TimeUnit.SECONDS);
    }


    @PostConstruct
    void init() {
        if (log.isInfoEnabled()) {
            log.info("---------------- 把微信的 accessToken 票据交给spring 管理 ---------------------");
        }
    }
}
