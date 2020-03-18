package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.entity.UserAddress;
import com.zyb.mini.mall.pojo.param.UserAddressParam;
import com.zyb.mini.mall.service.user.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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
@Api(tags = {"收货地址"})
@RequestMapping("/zyb/receive-address")
@RestController
@Validated
public class ReceiveAddressController extends BaseController {


    private final UserAddressService userAddressService;

    public ReceiveAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @ApiOperation("新增收货地址")
    @PostMapping("/add")
    R add(@Validated UserAddressParam param) {


        User user = currentUser();
        param.setUserId(user.getId());

        UserAddress address = new UserAddress();
        BeanUtils.copyProperties(param, address);
        userAddressService.addOne(address);
        return success();
    }


    @ApiOperation("删除收获地址")
    @PostMapping("/delete")
    R delete(@NotNull @Min(1) Long id) {
        UserAddress address = userAddressService.getById(id);
        if (address.getIsDefault()) {
            return fail("不能删除默认地址");
        }
        userAddressService.removeById(id);
        return success();
    }

    @ApiOperation("获取全部的收货地址")
    @GetMapping("/list")
    R<List<UserAddress>> list() {
        User user = currentUser();
        return R.success(userAddressService.selectListById(user.getId()));
    }

}
