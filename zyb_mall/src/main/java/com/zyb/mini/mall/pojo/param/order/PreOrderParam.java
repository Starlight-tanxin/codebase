package com.zyb.mini.mall.pojo.param.order;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanxin
 * @date 2019/10/30
 */
@ApiModel("下单的商品对象")
@Data
public class PreOrderParam implements Serializable {
    private static final long serialVersionUID = -8210338855664012680L;

    @ApiModelProperty(value = "商品id", example = Mock.NUMBER)
    @NotNull
    private Long goodsId;

    @ApiModelProperty(value = "商品数量", example = Mock.NUMBER)
    @NotNull
    private Integer num;
}
