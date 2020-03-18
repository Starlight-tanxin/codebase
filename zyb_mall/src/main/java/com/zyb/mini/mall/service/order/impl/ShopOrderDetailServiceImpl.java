package com.zyb.mini.mall.service.order.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.dao.ShopOrderDetailMapper;
import com.zyb.mini.mall.pojo.dto.OrderDetailDTO;
import com.zyb.mini.mall.pojo.entity.ShopOrderDetail;
import com.zyb.mini.mall.pojo.vo.MngOrderDetailVo;
import com.zyb.mini.mall.service.order.ShopOrderDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tanxin
 * @since 2019-10-31
 */
@Service
public class ShopOrderDetailServiceImpl extends ServiceImpl<ShopOrderDetailMapper, ShopOrderDetail> implements ShopOrderDetailService {

    @Resource
    private ShopOrderDetailMapper shopOrderDetailMapper;

    @Override
    public BigDecimal computeOrderPrice(Long orderId) {
        List<OrderDetailDTO> dtos = shopOrderDetailMapper.selectOrderDetailPriceByOrderId(orderId);
        if (dtos == null)
            return BigDecimal.valueOf(0);
        BigDecimal orderPrice = BigDecimal.valueOf(0);
        for (OrderDetailDTO dto : dtos) {
            BigDecimal goodsPrice = dto.getGoodsPrice();
            Integer num = dto.getNum();
            goodsPrice = (goodsPrice == null) ? BigDecimal.valueOf(0) : goodsPrice;
            num = (num == null) ? 1 : num;
            orderPrice.add(goodsPrice.multiply(BigDecimal.valueOf(num)));
        }
        return orderPrice;
    }

    @Override
    public List<MngOrderDetailVo> LIST(long id) {
        return shopOrderDetailMapper.LIST(id);
    }

    @Override
    public List<ShopOrderDetail> selectAllByOrderIds(List<Long> ids) {
        return shopOrderDetailMapper.selectAllByOrderIds(ids);
    }
}
