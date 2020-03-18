package com.zyb.mini.mall.web.controller.open;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.constant.DateType;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.entity.Surname;
import com.zyb.mini.mall.pojo.vo.IndexVo;
import com.zyb.mini.mall.service.background.BackgroundService;
import com.zyb.mini.mall.service.surname.BannerService;
import com.zyb.mini.mall.service.surname.SurnameService;
import com.zyb.mini.mall.service.user.UserPunchService;
import com.zyb.mini.mall.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Created by 谭健 on 2019/11/8. 星期五. 11:50.
 * © All Rights Reserved.
 */

@Api(tags = {"开放接口：打卡"})
@RequestMapping("/zyb/open/surname")
@RestController
@Validated
public class OpenSurnameCtrl extends BaseController {

    private final BannerService bannerService;
    private final SurnameService surnameService;
    private final UserPunchService userPunchService;
    private final BackgroundService backgroundService;

    public OpenSurnameCtrl(BannerService bannerService, SurnameService surnameService, UserPunchService userPunchService, BackgroundService backgroundService) {
        this.bannerService = bannerService;
        this.surnameService = surnameService;
        this.userPunchService = userPunchService;
        this.backgroundService = backgroundService;
    }


    @ApiOperation("获取打卡轮播图")
    @GetMapping("/index")
    public R index(){
        IndexVo vo =  new IndexVo();

        vo.setD(userPunchService.billboard(DateType.DAY));
        vo.setW(userPunchService.billboard(DateType.WEEK));
        vo.setM(userPunchService.billboard(DateType.MONTH));

        vo.setBanners(bannerService.list());
        return success(vo);
    }

    @ApiOperation("获取打卡轮播图")
    @GetMapping("/banner")
    public R banner() {
        return success(bannerService.list());
    }


    @ApiOperation("搜索页面初始化")
    @GetMapping("/search")
    public R search() {
        return success(surnameService.list());
    }

    @ApiImplicitParam(name = "keyword", value = "关键词")
    @ApiOperation("搜索姓氏")
    @GetMapping("/search/s")
    public R search(@NotBlank String keyword) {
        LambdaQueryWrapper<Surname> like = new QueryWrapper<Surname>().lambda().like(Surname::getName, keyword);
        return success(surnameService.list(like));
    }

    @ApiImplicitParam(name = "surnameId", value = "姓氏id")
    @ApiOperation("姓氏详情")
    @GetMapping("/{surnameId}/detail")
    public R detail(@NotNull @Min(1) long surnameId) {
        return success(surnameService.getById(surnameId));
    }


    @ApiImplicitParam(name = "dateType", value = "排行类型")
    @ApiOperation("百家姓打卡总数排行")
    @GetMapping("/billboard/{dateType}")
    public R billboard(@PathVariable("dateType") @NotBlank String dateType) {
        return success(userPunchService.billboard(dateType));
    }

    @ApiImplicitParam(name = "dateType", value = "排行类型")
    @ApiOperation("百家姓打卡总数排行详情")
    @GetMapping("/billboard/{dateType}/detail")
    public R billboardDetail(@PathVariable("dateType") @NotBlank String dateType) {
        return success(userPunchService.billboardDetail(dateType));
    }
}
