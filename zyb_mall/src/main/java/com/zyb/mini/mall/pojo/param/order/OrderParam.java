package com.zyb.mini.mall.pojo.param.order;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/30
 */
@Data
public class OrderParam implements Serializable {
    private static final long serialVersionUID = -8210338855664012680L;

    @NotNull
    @Size(min = 1)
    @Valid
    @ApiModelProperty(value = "下单的商品", required = true)
    List<PreOrderParam> perGoodsArray;

//    @ApiModelProperty(value = "当前的下单时间", example = Mock.DATETIME, required = true)
//    @NotNull
//    LocalDateTime now;

//    @ApiModelProperty(value = "用户的收获地址", example = Mock.NUMBER, required = true)
//    @NotNull
//    Long userAddressId;
}
