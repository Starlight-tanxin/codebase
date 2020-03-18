package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.framework.interceptor.user.UpdateUser;
import com.zyb.mini.mall.pojo.entity.Surname;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.param.user.UserParam;
import com.zyb.mini.mall.pojo.vo.mine.MineVo;
import com.zyb.mini.mall.service.user.UserPunchService;
import com.zyb.mini.mall.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:05.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Api(tags = {"个人中心"})
@RequestMapping("/zyb/mine")
@RestController
public class MineController extends BaseController {


    private final UserPunchService userPunchService;
    private final UserService userService;

    public MineController(UserPunchService userPunchService, UserService userService) {
        this.userPunchService = userPunchService;
        this.userService = userService;
    }

    @ApiOperation("个人中心：个人信息")
    @GetMapping("/")
    R index() {
        User user = currentUser();
        MineVo vo = new MineVo();
        BeanUtils.copyProperties(user, vo);

        Surname userSurname = userPunchService.getUserSurname(user.getId());
        String s = Optional.ofNullable(userSurname).map(Surname::getName).orElse("");
        vo.setSurnameName(s);
        vo.setTopOne(userPunchService.isTopOne(user.getId()));

        return success(vo);
    }

    @ApiOperation("个人中心：修改个人信息")
    @GetMapping("/update")
    @UpdateUser(msg = "修改个人信息")
    R update(@Validated UserParam param) {
        User user = currentUser();
        if (param != null) {
            user.setMobile(param.getMobile());
            user.setHeadImg(param.getHeadImg());
            user.setNickname(param.getNickname());
            userService.updateById(user);
            return success(user);
        }
        return success("");
    }

}
