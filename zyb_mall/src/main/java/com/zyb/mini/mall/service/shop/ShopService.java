package com.zyb.mini.mall.service.shop;

import com.zyb.mini.mall.pojo.dto.ShopCartDTO;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.entity.ShopBanner;

import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/28
 */
public interface ShopService {

    /**
     * 获取推荐
     *
     * @param randomId 随机id
     * @param num      数量
     * @return
     */
    List<GoodsBook> recommend(int randomId, Integer num);

    /**
     * 获取轮播图
     *
     * @return
     */
    List<ShopBanner> getShopBanner();

    /**
     * 获取用户的购物车
     *
     * @param userId 用户id
     * @return 购物车
     */
    List<ShopCartDTO> getUserCart(Long userId);

    /**
     * 添加商品到购物车
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 购物车
     */
    List<ShopCartDTO> addCart(Long userId, Long goodsId);

    /**
     * 删除购物车条码
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @return 购物车
     */
    List<ShopCartDTO> removeCart(Long userId, Long goodsId);

    /**
     * 更新购物车商品数量
     *
     * @param userId  用户id
     * @param goodsId 商品id
     * @param num     数量
     * @return 购物车
     */
    List<ShopCartDTO> updateCart(Long userId, Long goodsId, Integer num);
}
