package com.zyb.mini.mall.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.dao.UserMapper;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.param.LoginParam;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.user.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Created by 谭健 on 2019/10/29. 星期二. 17:44.
 * © All Rights Reserved.
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final UserMapper userMapper;
    private final StringRedisTemplate redis;

    public UserServiceImpl(UserMapper userMapper, StringRedisTemplate redis) {
        this.userMapper = userMapper;
        this.redis = redis;
    }

    @Override
    public User registerOrGet(LoginParam param) {

        String openId = param.getOpenId();
        Optional<User> user = Optional.ofNullable(userMapper.selectByOpenId(openId));
        User u = user.orElseGet(() -> {
            User n = new User();
            n.setWxOpenId(openId);
            n.setUserType(1);
            n.setUserLevel(1);
            n.setMobile("");
            n.setCretaedTime(LocalDateTime.now());
            n.setUserRecId(0L);
            n.setWxInfo("");
            n.setActualAmount(BigDecimal.ZERO);
            n.setFreezeAmount(BigDecimal.ZERO);
            n.setNickname(param.getNickname());
            n.setHeadImg(param.getHeadImg());

            save(n);
            return n;
        });

        redis.opsForValue().set(RedisKeyNameConstant.REDIS_USER_KEY_PREFIX + openId, JSON.toJSONString(u));
        return u;
    }


}
