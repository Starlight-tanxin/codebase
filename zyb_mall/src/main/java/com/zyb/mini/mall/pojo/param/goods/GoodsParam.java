package com.zyb.mini.mall.pojo.param.goods;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanxin
 * @date 2019/10/29
 */
@ApiModel("商品搜索参数")
@Data
@Accessors
public class GoodsParam extends BasePageParam {
    private static final long serialVersionUID = 8800993860253556075L;

    @ApiModelProperty(value = "书名或者作者")
    private String name;

    @ApiModelProperty(value = "类别", example = Mock.NUMBER)
    private Integer bookTypeId;

    @ApiModelProperty(value = "其他的类别", example = Mock.NUMBER)
    private Integer bookOtherTypeId;

    @ApiModelProperty(value = "是否收藏 0-不是收藏商品 1-是收藏商品  PS：收藏类别得商品不能被购买", example = Mock.NUMBER)
    private Boolean isCollection;

    @ApiModelProperty(hidden = true)
    private Integer limit;
}
