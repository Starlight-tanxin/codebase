package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.param.user.UserProParam;
import com.zyb.mini.mall.service.user.UserProService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * tzw
 */
@Api(tags = {"鉴赏专家"})
@Validated
@RequestMapping("/zyb/userPro")
@RestController
public class UserProController extends BaseController {


    private final UserProService UserProService;


    public UserProController(UserProService userProService) {
        UserProService = userProService;
    }

    @ApiOperation("鉴赏专家列表")
    @GetMapping("/index")
    R index(@Validated UserProParam param) {
        return success(UserProService.selectPageByParam(param));
    }


    @ApiOperation("鉴赏专家详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "鉴赏专家id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/getInfo")
    R getInfo(@NotNull(message = "主键不能为空") Long id) {
        return success(UserProService.getInfo(id));
    }

}
