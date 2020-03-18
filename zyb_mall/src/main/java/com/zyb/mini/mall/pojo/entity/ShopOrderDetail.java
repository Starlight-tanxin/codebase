package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyb.mini.mall.constant.CommonConstant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@Accessors
@TableName("tb_shop_order_detail")
public class ShopOrderDetail extends Model<ShopOrderDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer num;

    /**
     * 外键
     */
    private Long shopOrderId;

    /**
     * 外键
     */
    private Long goodsBookId;

    /**
     * 主图
     */
    private String mainImg;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品价格 单位元
     */
    private BigDecimal goodsPrice;

    /**
     * 描述
     */
    private String bookDesc;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
