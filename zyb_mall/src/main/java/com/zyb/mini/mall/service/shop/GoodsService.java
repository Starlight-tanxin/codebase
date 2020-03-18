package com.zyb.mini.mall.service.shop;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.param.goods.GoodsParam;
import com.zyb.mini.mall.pojo.vo.goods.GoodsBookVO;

import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/29
 */
public interface GoodsService {

    /**
     * 获取商品集合
     *
     * @param param
     * @return
     */
    List<GoodsBook> getGoodsList(GoodsParam param);


    /**
     * 获取商品的分页数据
     *
     * @param param
     * @return
     */
    Page<GoodsBook> getGoodsPage(GoodsParam param);

    /**
     * 获取商品详情
     *
     * @param goodsId 主键
     * @return
     */
    GoodsBookVO getGoodsDetail(Long goodsId);


    /**
     * 检查商品是否存在
     *
     * @param goodsId
     * @return
     */
    boolean checkGoodsExist(Long goodsId);

    /**
     * 检查商品是否又足够库存
     *
     * @param goodsId
     * @param num
     * @return
     */
    boolean checkGoodsNum(Long goodsId, Integer num);

    /**
     * 更新库存
     *
     * @param goodsId
     * @param num
     * @return
     */
    int updateGoodsNum(Long goodsId, Integer num);

    /**
     * 获取订单的第一个商品
     *
     * @param orderId 订单id
     * @return
     */
    GoodsBookVO getGoodsByOrderId(Long orderId);

    GoodsBook getGoodsById(Long goodsId);
}
