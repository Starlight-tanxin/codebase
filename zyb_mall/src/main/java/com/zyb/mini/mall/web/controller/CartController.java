package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.dto.ShopCartDTO;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.service.shop.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = {"购物车"})
@RequestMapping("/zyb/cart")
@RestController
@Validated
public class CartController extends BaseController {


    private final ShopService shopService;

    public CartController(ShopService shopService) {
        this.shopService = shopService;
    }

    @ApiOperation("购物车初始化")
    @GetMapping("/index")
    R<List<ShopCartDTO>> index() {
        User user = currentUser();
        List<ShopCartDTO> userCart = shopService.getUserCart(user.getId());
        return R.success(userCart);
    }

    @ApiOperation("添加到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/addCart")
    R<List<ShopCartDTO>> addCart(@NotNull Long goodsId) {
        User user = currentUser();
        List<ShopCartDTO> userCart = shopService.addCart(user.getId(), goodsId);
        return R.success(userCart);
    }

    @ApiOperation("删除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/removeCart")
    R<List<ShopCartDTO>> removeCart(@NotNull Long goodsId) {
        User user = currentUser();
        List<ShopCartDTO> userCart = shopService.removeCart(user.getId(), goodsId);
        return R.success(userCart);
    }


    @ApiOperation("更新购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", example = Mock.NUMBER, paramType = "query", required = true),
            @ApiImplicitParam(name = "num", value = "商品数量", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/updateCart")
    R<List<ShopCartDTO>> updateCart(@NotNull Long goodsId, @NotNull Integer num) {
        User user = currentUser();
        List<ShopCartDTO> userCart = shopService.updateCart(user.getId(), goodsId, num);
        return R.success(userCart);
    }

}
