package com.tx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@MapperScan("com.tx.dao")
public class App {

    public static ConcurrentHashMap<String, List<String>> roleUrlMap = new ConcurrentHashMap<>(2);

    public static void main(String[] args) {
        // 此处为启动缓存，偷懒了，直接写启动类里面了。
        // 正常流程应把对应角色的权限存到redis
        // 了解启动缓存设置
        List<String> adminUrl = new ArrayList<>();
        adminUrl.add("*");
        List<String> financeUrl = new ArrayList<>();
        financeUrl.add("/user/showAll");
        financeUrl.add("/user/a");
        financeUrl.add("user/b");
        roleUrlMap.put("ADMIN",adminUrl);
        roleUrlMap.put("FINANCE",financeUrl);
        SpringApplication.run(App.class, args);
    }
}