package com.zyb.mini.mall.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车
 *
 * @author tanxin
 * @date 2019/10/29
 */
@Data
@Accessors
public class ShopCartDTO implements Serializable {
    private static final long serialVersionUID = -5033753043398390055L;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品主图
     */
    private String mainImg;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 作者
     */
    private String author;


    private Integer num;

    /**
     * 价格
     */
    private BigDecimal price;

}
