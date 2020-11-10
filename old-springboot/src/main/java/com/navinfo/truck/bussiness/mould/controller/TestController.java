package com.navinfo.truck.bussiness.mould.controller;

import com.navinfo.truck.bussiness.mould.dao.rdb.BdMouldMapper;
import com.navinfo.truck.bussiness.mould.model.req.MouldReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/10 19:40
 */
@Api(tags = "模具模块接口")
@RestController
@RequestMapping("/api/test")
public class TestController {


    @Resource
    private BdMouldMapper bdMouldMapper;

    @ApiOperation(value = "商务数字化-查询所有模具")
    @GetMapping("/hello")
    public Object hello() {
        return bdMouldMapper.selectAllByParam(new MouldReq());
    }
}
