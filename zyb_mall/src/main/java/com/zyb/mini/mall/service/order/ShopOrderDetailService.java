package com.zyb.mini.mall.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.entity.ShopOrderDetail;
import com.zyb.mini.mall.pojo.vo.MngOrderDetailVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tanxin
 * @since 2019-10-31
 */
public interface ShopOrderDetailService extends IService<ShopOrderDetail> {

    BigDecimal computeOrderPrice(Long orderId);


    List<MngOrderDetailVo> LIST(long id);

    List<ShopOrderDetail> selectAllByOrderIds(List<Long> ids);
}
