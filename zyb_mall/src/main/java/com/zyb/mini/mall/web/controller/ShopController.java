package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.entity.ShopBanner;
import com.zyb.mini.mall.pojo.param.goods.GoodsParam;
import com.zyb.mini.mall.pojo.vo.shop.ShopIndexVO;
import com.zyb.mini.mall.service.shop.GoodsService;
import com.zyb.mini.mall.service.shop.ShopService;
import com.zyb.mini.mall.utils.RandomUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
@Api(tags = {"商城"})
@RequestMapping("/zyb/shop")
@RestController
public class ShopController extends BaseController {


    private final ShopService shopService;

    private final GoodsService goodsService;

    public ShopController(ShopService shopService, GoodsService goodsService) {
        this.shopService = shopService;
        this.goodsService = goodsService;
    }

    @ApiOperation("商城初始化")
    @GetMapping("/index")
    R<ShopIndexVO> index() {
        int randomId = RandomUtils.nextInt(ThreadLocalRandom.current(), 9);

        GoodsParam param = new GoodsParam().setIsCollection(true).setLimit(4);

        List<GoodsBook> collectionList = goodsService.getGoodsList(param);
        param.setIsCollection(false);
        List<GoodsBook> bookList = goodsService.getGoodsList(param);
        List<GoodsBook> list = shopService.recommend(randomId, 2);
        List<ShopBanner> bannerList = shopService.getShopBanner();
        return R.success(new ShopIndexVO()
                .setBannerList(bannerList)
                .setRecommendGoods(list)
                .setCollectionList(collectionList)
                .setBookList(bookList));
    }


}
