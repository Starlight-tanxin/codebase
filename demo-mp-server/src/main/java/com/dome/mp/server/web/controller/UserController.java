package com.dome.mp.server.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dome.mp.server.core.Result;
import com.dome.mp.server.pojo.entity.TimeTest;
import com.dome.mp.server.pojo.entity.User;
import com.dome.mp.server.service.IUserService;
import com.dome.mp.server.utils.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Validated
@RestController
@RequestMapping("/demo/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public Result<Page<User>> list(@NotNull(message = "页数不能为空") Integer pageNum, @NotNull(message = "每页大小不能为空") Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        // 或者如下
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
        // 这里为 lambada 设置参数
//        LambdaQueryWrapper<User> queryWrapper = new QueryWrapper<User>().lambda();
//        if (param.getEnable() != null) queryWrapper.eq(Demo::getEnable, param.getEnable());
//        if (StrUtil.isNotBlank(param.getJob())) queryWrapper.eq(Demo::getJob, param.getJob());
//        if (StrUtil.isNotBlank(param.getName())) queryWrapper.eq(Demo::getName, param.getName());
//        if (param.getType() != null) queryWrapper.eq(Demo::getType, param.getType());
//        page = userService.page(page, queryWrapper);
        page = userService.page(page);
        return Result.success(page);
    }


    @GetMapping("/test")
    public Result<Object> test(String path) throws IOException {
        log.info(path);
        InputStream inputStream = ResourceUtils.getResourceAsStream(path);
        byte[] buff = new byte[1024];
        int i = 0;
        String str = "";
        while ((i = inputStream.read(buff)) != -1) {
            str += new String(buff);
        }
        log.info("InputStream : " + (inputStream == null));
        return Result.success(str);
    }

    @PostMapping("/time")
    public Result<TimeTest> time(TimeTest timeTest) {
        return Result.success(timeTest);
    }

}
