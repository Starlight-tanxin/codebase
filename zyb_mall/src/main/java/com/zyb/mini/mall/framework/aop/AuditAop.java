package com.zyb.mini.mall.framework.aop;

import cn.hutool.core.date.TimeInterval;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Created by 谭健 on 2019/10/31. 星期四. 11:29.
 * © All Rights Reserved.
 */

@EnableAspectJAutoProxy
@Component
@Aspect
@Slf4j
public class AuditAop {


    @Pointcut("@annotation( io.swagger.annotations.ApiOperation)")
    public void info() {

    }


    @Around("info()")
    public Object ar(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        TimeInterval interval = new TimeInterval();
        Object proceed = joinPoint.proceed();
        long time = interval.interval();
        if (log.isInfoEnabled()) {
            log.info("执行方法： [{}] , 业务逻辑 [{}] , 执行时间： [{}]", method.getName(), annotation.value(), time);
        }
        return proceed;
    }

}
