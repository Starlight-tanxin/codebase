package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.param.LoginParam;
import com.zyb.mini.mall.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.component.AuthComponent;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 13:12.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Api(tags = {"微信授权登陆模块"})
@RequestMapping("/zyb/login")
@RestController
@Validated
public class LoginController extends BaseController {

    @Value("${weixin4j.config.appid}")
    private String appid;
    @Value("${weixin4j.config.secret}")
    private String secret;

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("识别用户，换取操作凭据")
    @ApiImplicitParam(name = "code", value = "微信前端登陆得到的临时代码")
    @GetMapping("/getCredential/{code}")
    R credential(@PathVariable("code") String code) throws WeixinException {
        AuthComponent component = new AuthComponent(new WeixinMiniprogram());
        return success(component.code2Session(code));
    }


    @ApiOperation("登陆或者注册")
    @PostMapping("/")
    R login(@Valid LoginParam param) {
        return success(userService.registerOrGet(param));
    }


    @ApiOperation("服务器启动判断接口")
    @GetMapping("/ping")
    String ping() {
        return "pong" + LocalDateTime.now();
    }
}
