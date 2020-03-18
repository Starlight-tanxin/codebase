package com.zyb.mini.mall.pojo.param.identify;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.entity.MaintainCompanyImg;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jpping
 * @Date 2019/11/12
 */
@ApiModel("平台回复信息")
@Data
@Accessors
public class MaintainMessageParam extends BasePageParam implements Serializable {
    private static final long serialVersionUID = -1615007190870968827L;

    @ApiModelProperty(value = "修复订单id", example = Mock.NUMBER)
    private Long id;

    @ApiModelProperty(value = "官方修复金额 单位元", example = Mock.NUMBER)
    private BigDecimal cmMaintainAmount;

    @ApiModelProperty(value = "官方修复天数 整数", example = Mock.NUMBER)
    private Integer cmMaintainDay;

    @ApiModelProperty(value = "修复描述")
    private String cmMaintainMsg;

    @ApiModelProperty(value = "官方回复的修复图片")
    private List<MaintainCompanyImg> maintainCompanyImgs;

}
