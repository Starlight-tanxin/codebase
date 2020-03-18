package com.zyb.mini.mall.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.MaintainPro;
import com.zyb.mini.mall.pojo.param.user.UserProParam;
import com.zyb.mini.mall.service.maintain_identify.MaintainProService;
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
 * <p>
 * 修复得专家表 前端控制器
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
@Validated
@Api(tags = {"修复专家"})
@RestController
@RequestMapping("/zyb/maintain-pro")
public class MaintainProController extends BaseController {
    private final MaintainProService maintainProService;

    public MaintainProController(MaintainProService maintainProService) {
        this.maintainProService = maintainProService;
    }


    @ApiOperation("修复专家列表")
    @GetMapping("/index")
    R<Page<MaintainPro>> index(@Validated UserProParam param) {
        return R.success(maintainProService.selectPageByParam(param));
    }


    @ApiOperation("修复专家详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "修复专家id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/getInfo")
    R<MaintainPro> getInfo(@NotNull(message = "主键不能为空") Long id) {
        return R.success(maintainProService.getInfo(id));
    }
}

