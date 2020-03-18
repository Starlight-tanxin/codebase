package com.zyb.mini.mall.framework.interceptor;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.framework.interceptor.user.UpdateUser;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.user.UserService;
import com.zyb.mini.mall.utils.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author Created by 谭健 on 2019/10/29. 星期二. 16:22.
 * © All Rights Reserved.
 * <p>
 * 登陆拦截器
 */


@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    private final StringRedisTemplate redis;
    private final UserService userService;

    public LoginInterceptor(StringRedisTemplate redis, UserService userService) {
        this.redis = redis;
        this.userService = userService;
    }



    /**
     * 如果信息不存在，则返回未授权
     *
     * @throws IOException .
     */
    private void unauthorized(HttpServletResponse response) throws IOException {
        NetUtils.objectMapper()
                .writer()
                .writeValue(
                        response.getOutputStream(), R.error(Status.UNAUTHORIZED));
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String authorization = request.getHeader(RedisKeyNameConstant.AUTH_HEADER);
        if (StringUtils.isNotBlank(authorization)) {
            String userInfo = redis.opsForValue().get(RedisKeyNameConstant.REDIS_USER_KEY_PREFIX + authorization);
            if (StringUtils.isNotBlank(userInfo)) {
                User user = JSON.parseObject(userInfo, User.class);
                return user.getId() != null && StringUtils.isNotBlank(user.getWxOpenId());
            }
        }
        unauthorized(response);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在后置方法跟新缓存
        if (!(handler instanceof HandlerMethod)) {
            log.debug("Expected handler class " + HandlerMethod.class.getSimpleName() + " but was " + handler.getClass());
            return;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        UpdateUser update = hm.getMethodAnnotation(UpdateUser.class);
        if (update == null) {
            return;
        }
        log.info("需要更新用户缓存 ----> 更新的消息 ： {} ; 更新开始的时间 : {} ", update.msg(), System.currentTimeMillis());
        String authorization = request.getHeader(RedisKeyNameConstant.AUTH_HEADER);
        if (StringUtils.isNotBlank(authorization)) {
            LambdaQueryWrapper<User> qw = new QueryWrapper<User>().lambda().eq(User::getWxOpenId, authorization);
            User user = userService.getOne(qw);
            if (user != null) {
                redis.opsForValue().set(RedisKeyNameConstant.REDIS_USER_KEY_PREFIX + authorization, JSON.toJSONString(user));
                log.info("更新用户缓存完成 ---->  更新完成的时间 : {} ", System.currentTimeMillis());
                return;
            }
        }
        log.info("更新用户缓存失败 ---->  更新完成的时间 : {} ", System.currentTimeMillis());
    }
}
