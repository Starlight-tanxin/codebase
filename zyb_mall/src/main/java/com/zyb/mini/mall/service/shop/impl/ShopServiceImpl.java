package com.zyb.mini.mall.service.shop.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zyb.mini.mall.core.RedisCache;
import com.zyb.mini.mall.dao.GoodsBookMapper;
import com.zyb.mini.mall.dao.ShopBannerMapper;
import com.zyb.mini.mall.pojo.dto.ShopCartDTO;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.entity.ShopBanner;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/28
 */
@Service
public class ShopServiceImpl implements ShopService {


    @Resource
    private GoodsBookMapper goodsBookMapper;

    @Resource
    private ShopBannerMapper shopBannerMapper;

    @Autowired
    private RedisCache redisCache;


    @Cacheable(value = "goods_recommend", key = "'randomId_' + #randomId")
    @Override
    public List<GoodsBook> recommend(int randomId, Integer num) {
        return goodsBookMapper.getRecommendGoods(num);
    }


    @Cacheable(value = "getShopBanner")
    @Override
    public List<ShopBanner> getShopBanner() {
        return shopBannerMapper.selectAllBanner();
    }

    @Override
    public List<ShopCartDTO> getUserCart(Long userId) {
        String key = RedisKeyNameConstant.SHOP_CART_BY_USER + userId;
        String str = redisCache.getStrCache(key);
        if (Strings.isNullOrEmpty(str)) {
            return new ArrayList<>();
        }
        List<ShopCartDTO> userCart = JSONObject.parseArray(str, ShopCartDTO.class);
        return userCart;
    }

    @Override
    public List<ShopCartDTO> addCart(Long userId, Long goodsId) {
        String key = RedisKeyNameConstant.SHOP_CART_BY_USER + userId;
        List<ShopCartDTO> userCart = getUserCart(userId);
        userCart = (userCart == null) ? new ArrayList<>() : userCart;
        for (ShopCartDTO dto : userCart) {
            if (dto.getGoodsId().equals(goodsId)) {
                dto.setNum(dto.getNum() + 1);
                redisCache.refreshStrCache(key, JSONObject.toJSONString(userCart));
                return userCart;
            }
        }
        GoodsBook goodsBook = goodsBookMapper.selectById(goodsId);
        ShopCartDTO dto = new ShopCartDTO()
                .setAuthor(goodsBook.getAuthor())
                .setGoodsId(goodsId)
                .setGoodsName(goodsBook.getGoodsName())
                .setMainImg(goodsBook.getMainImg())
                .setPrice(goodsBook.getGoodsPrice())
                .setNum(1);
        userCart.add(dto);
        redisCache.refreshStrCache(key, JSONObject.toJSONString(userCart));
        return userCart;
    }

    @Override
    public List<ShopCartDTO> removeCart(Long userId, Long goodsId) {
        String key = RedisKeyNameConstant.SHOP_CART_BY_USER + userId;
        List<ShopCartDTO> userCart = getUserCart(userId);
        if (userCart == null) {
            return new ArrayList<>();
        }
        for (int i = userCart.size(); i > 0; i--) {
            ShopCartDTO dto = userCart.get(i - 1);
            if (dto.getGoodsId().equals(goodsId)) {
                userCart.remove(i - 1);
            }
        }
        redisCache.refreshStrCache(key, JSONObject.toJSONString(userCart));
        return userCart;
    }

    @Override
    public List<ShopCartDTO> updateCart(Long userId, Long goodsId, Integer num) {
        String key = RedisKeyNameConstant.SHOP_CART_BY_USER + userId;
        List<ShopCartDTO> userCart = getUserCart(userId);
        if (userCart == null) {
            return new ArrayList<>();
        }
        for (int i = userCart.size(); i > 0; i--) {
            ShopCartDTO dto = userCart.get(i - 1);
            if (dto.getGoodsId().equals(goodsId)) {
                if (num.equals(0)) {
                    userCart.remove(i - 1);
                } else {
                    dto.setNum(num);
                }
                break;
            }
        }
        redisCache.refreshStrCache(key, JSONObject.toJSONString(userCart));
        return userCart;
    }
}
