package com.zyb.mini.mall.pojo.vo.shop;

import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.entity.ShopBanner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/29
 */
@ApiModel(value = "商城初始化VO")
@Data
@Accessors
public class ShopIndexVO implements Serializable {
    private static final long serialVersionUID = -1349652059085288405L;

    @ApiModelProperty(value = "推荐商品数组")
    private List<GoodsBook> recommendGoods;

    @ApiModelProperty(value = "轮播图数组")
    private List<ShopBanner> bannerList;

    @ApiModelProperty(value = "官方收藏商品数据")
    private List<GoodsBook> collectionList;

    @ApiModelProperty(value = "全部商品数据")
    private List<GoodsBook> bookList;
}
