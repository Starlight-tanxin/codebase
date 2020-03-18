package com.zyb.mini.mall.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.param.goods.GoodsParam;
import com.zyb.mini.mall.pojo.vo.goods.GoodsBookVO;
import com.zyb.mini.mall.service.shop.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:04.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Api(tags = {"商品"})
@RequestMapping("/zyb/goods")
@RestController
@Validated
public class GoodsController extends BaseController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }


    @ApiOperation("商品详情")
    @GetMapping("/{goodsId}/detail")
    R<GoodsBookVO> detail(@PathVariable("goodsId") @NotNull long goodsId) {
        return R.success(goodsService.getGoodsDetail(goodsId));
    }


    @ApiOperation("搜索商品")
    @GetMapping("/searchGoods")
    R<Page<GoodsBook>> searchGoods(@Validated GoodsParam param) {
        Page<GoodsBook> page = goodsService.getGoodsPage(param);
        return R.success(page);
    }


}
