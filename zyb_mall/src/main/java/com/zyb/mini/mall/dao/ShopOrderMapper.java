package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.ShopOrder;
import com.zyb.mini.mall.pojo.param.order.OrderIndexParam;
import com.zyb.mini.mall.pojo.param.order.QueryOrderListParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商城订单表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface ShopOrderMapper extends BaseMapper<ShopOrder> {

    List<ShopOrder> queryShopOrder(Page<ShopOrder> page, @Param("param") QueryOrderListParam param);


    List<ShopOrder> selectPageByParam(Page<ShopOrder> page, @Param("param") OrderIndexParam param);
}
