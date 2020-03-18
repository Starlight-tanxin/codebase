package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.dto.OrderDetailDTO;
import com.zyb.mini.mall.pojo.entity.ShopOrderDetail;
import com.zyb.mini.mall.pojo.vo.MngOrderDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface ShopOrderDetailMapper extends BaseMapper<ShopOrderDetail> {

    @Select("SELECT\n" +
            "\ta.goods_book_id,\n" +
            "\ta.num,\n" +
            "\ta.goods_price\n" +
            "FROM\n" +
            "\ttb_shop_order_detail a\n" +
            "WHERE\n" +
            "\ta.shop_order_id = #{orderId}")
    List<OrderDetailDTO> selectOrderDetailPriceByOrderId(@Param("orderId") Long orderId);


    @Select("SELECT\n" +
            "\ta.*\n" +
            "FROM\n" +
            "\ttb_shop_order_detail a\n" +
            "\tWHERE a.shop_order_id = #{orderId}")
    List<ShopOrderDetail> selectDetailByOrderId(@Param("orderId") Long orderId);


    List<MngOrderDetailVo> LIST(long  id);

    @Select("select * from tb_shop_order_detail a where a.shop_order_id=#{orderId} LIMIT 1")
    ShopOrderDetail selectDetailOne(@Param("orderId") Long orderId);

    List<ShopOrderDetail> selectAllByOrderIds(List<Long> ids);
}
