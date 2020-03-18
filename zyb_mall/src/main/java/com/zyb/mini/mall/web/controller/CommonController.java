package com.zyb.mini.mall.web.controller;

import com.google.common.base.Strings;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.RedisCache;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.framework.component.SmsComponent;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.vo.city.ProvinceVO;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.common.CommonService;
import com.zyb.mini.mall.utils.CodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Tx
 * @date: 2019/10/27
 */
@Api(tags = {"通用接口"})
@RequestMapping("/zyb/common/")
@RestController
public class CommonController extends BaseController {

    private final CommonService commonService;
    private final SmsComponent smsComponent;
    private final RedisCache redisCache;

    public CommonController(CommonService commonService, SmsComponent smsComponent, RedisCache redisCache) {
        this.commonService = commonService;
        this.smsComponent = smsComponent;
        this.redisCache = redisCache;
    }

    @ApiOperation(value = "获取省市区")
    @GetMapping("/selectProvinceAndCityAndRegion")
    public R<List<ProvinceVO>> selectProvinceAndCityAndRegion() {
        return R.success(commonService.selectProvinceAndCityAndRegion());
    }

    @ApiOperation(value = "获取古董类别")
    @GetMapping("/selectAntiqueTypeBox")
    public R<List<DownBoxVO>> selectAntiqueTypeBox() {
        return R.success(commonService.selectAntiqueTypeBox());
    }

    @ApiOperation(value = "获取年份类别")
    @GetMapping("/selectYearTypeBox")
    public R<List<DownBoxVO>> selectYearTypeBox() {
        return R.success(commonService.selectYearTypeBox());
    }


    @ApiOperation(value = "获取品相类别")
    @GetMapping("/selectPhaseTypeBox")
    public R<List<DownBoxVO>> selectPhaseTypeBox() {
        return R.success(commonService.selectPhaseTypeBox());
    }

    @ApiOperation(value = "获取书籍类别")
    @GetMapping("/selectBookTypeBox")
    public R<List<DownBoxVO>> selectBookTypeBox() {
        return R.success(commonService.selectBookTypeBox());
    }


    @ApiOperation(value = "获取书籍其他类别")
    @GetMapping("/selectBookOtherTypeBox")
    public R<List<DownBoxVO>> selectBookOtherTypeBox() {
        return R.success(commonService.selectBookOtherTypeBox());
    }

    @ApiOperation(value = "发送身份验证短信 - 1min过期")
    @GetMapping("/sendCode")
    public R<String> sendCode() {
        User user = currentUser();
        String mobile = user.getMobile();
        if (Strings.isNullOrEmpty(mobile)) {
            return R.error(Status.MOBILE_NOT_SET);
        }
        String key = RedisKeyNameConstant.USER_CODE_BY_MOBILE + mobile;
        String code = CodeUtils.createRandomNumberByLength(4);
        redisCache.refreshStrCacheByTime(key, code, 60);
        smsComponent.sendMsgByCode(user.getMobile(), code);
        return R.success();
    }

    @ApiOperation(value = "获取快递公司得下拉框")
    @GetMapping("/selectExpressCompanyTypeBox")
    public R<List<DownBoxVO>> selectExpressCompanyTypeBox() {
        return R.success(commonService.selectExpressTypeBox());
    }
}
