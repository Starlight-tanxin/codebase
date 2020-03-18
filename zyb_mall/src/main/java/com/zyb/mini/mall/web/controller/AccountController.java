package com.zyb.mini.mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.constant.PayOrderState;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.RedisCache;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.pojo.entity.PayOrder;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import com.zyb.mini.mall.pojo.vo.AccountVo;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.trade.PayOrderService;
import com.zyb.mini.mall.service.user.UserService;
import com.zyb.mini.mall.utils.sign.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:06.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Validated
@Api(tags = {"账户"})
@RequestMapping("/zyb/account")
@RestController
public class AccountController extends BaseController {

    private final PayOrderService payOrderService;
    private final UserService userService;
    private final RedisCache redisCache;

    /**
     * 运行环境
     */
    @Value("${spring.profiles.active}")
    private String profiles;

    public AccountController(PayOrderService payOrderService, UserService userService, RedisCache redisCache) {
        this.payOrderService = payOrderService;
        this.userService = userService;
        this.redisCache = redisCache;
    }

    @ApiOperation("账户明细")
    @GetMapping("/")
    R index(@Valid BasePageParam param) {
        User user = currentUser();
        LambdaQueryWrapper<PayOrder> wrapper = new QueryWrapper<PayOrder>().lambda()
                .eq(PayOrder::getUserId, user.getId())
                .eq(PayOrder::getState, PayOrderState.PAY_FINAL)
                .orderByDesc(PayOrder::getCreatedTime);
        Page<PayOrder> page = new Page<>(param.getPage(), param.getPageSize());
        IPage<PayOrder> iPage = payOrderService.page(page, wrapper);
        AccountVo v = new AccountVo();

        BeanUtils.copyProperties(user, v);
        List<AccountVo.Column> list = Lists.newArrayList();
        iPage.getRecords().forEach(payOrder -> {
            AccountVo.Column column = new AccountVo.Column();
            BeanUtils.copyProperties(payOrder, column);
            list.add(column);

        });
        v.setColumns(list);
        return success(v);
    }


    @ApiOperation("检查是否设置了支付密码----账户金额支付")
    @GetMapping("/checkSetPayPwd")
    R<?> checkSetPayPwd() {
        User user = currentUser();
        String payPwd = user.getPayPwd();
        if (Strings.isNullOrEmpty(payPwd)) {
            R.error(Status.NOT_SET_PWD);
        }
        return R.success();
    }


    @ApiOperation("设置支付密码----账户金额支付 测试环境-密码验证码1234")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "短信验证码", paramType = "query", required = true),
            @ApiImplicitParam(name = "pwd", value = "支付密码", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @PostMapping("/setPayPwd")
    R<?> setPayPwd(@NotBlank String code, @NotBlank String pwd) {
        User user = currentUser();
        String key = RedisKeyNameConstant.USER_CODE_BY_MOBILE + user.getMobile();
        String codeCache = redisCache.getStrCache(key);
        if ((code.equals("1234") && !profiles.equals(CommonConstant.PROD)) || code.equals(codeCache)) {
            String md5Pwd = MD5Utils.MD5Encode(pwd);
            user.setPayPwd(md5Pwd);
            userService.updateById(user);
            return R.success();
        }
        return R.error(Status.CODE_ERROR);
    }

}
