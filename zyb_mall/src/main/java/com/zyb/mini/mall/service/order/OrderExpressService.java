package com.zyb.mini.mall.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import com.zyb.mini.mall.pojo.entity.OrderExpress;

/**
 * <p>
 * 订单得物流信息表 服务类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-15
 */
public interface OrderExpressService extends IService<OrderExpress> {


    /**
     * 查询物流信息
     *
     * @param orderId 订单id
     * @return
     */
    ExpressDTO queryExpressInfo(Long orderId);

}
