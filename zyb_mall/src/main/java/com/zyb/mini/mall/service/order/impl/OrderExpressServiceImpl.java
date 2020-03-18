package com.zyb.mini.mall.service.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.dao.OrderExpressMapper;
import com.zyb.mini.mall.framework.component.ExpressComponent;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import com.zyb.mini.mall.pojo.entity.OrderExpress;
import com.zyb.mini.mall.pojo.entity.ShopOrder;
import com.zyb.mini.mall.pojo.entity.UserAddress;
import com.zyb.mini.mall.service.order.OrderExpressService;
import com.zyb.mini.mall.service.order.OrderService;
import com.zyb.mini.mall.service.user.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单得物流信息表 服务实现类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-15
 */
@Service
public class OrderExpressServiceImpl extends ServiceImpl<OrderExpressMapper, OrderExpress> implements OrderExpressService {


    @Autowired
    private OrderService orderService;
    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private ExpressComponent expressComponent;


    @Override
    public ExpressDTO queryExpressInfo(Long orderId) {
        ShopOrder shopOrder = orderService.getById(orderId);
        UserAddress userAddress = userAddressService.getById(shopOrder.getUserAddressId());
        return expressComponent.queryExpress(shopOrder.getExpressNo(), shopOrder.getExpressCompanyNo(), userAddress.getMobile());
    }
}
