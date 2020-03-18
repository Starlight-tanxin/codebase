package com.zyb.mini.mall.pojo.param.identify;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Jpping
 * @Date 2019/11//14
 */
@ApiModel("后台鉴赏记录分页参数对象")
@Data
public class IdentifyListParam extends BasePageParam implements Serializable {

    private static final long serialVersionUID = -4028548346666308065L;

    @ApiModelProperty(value = "鉴赏状态\n" +
            "1 - 创建鉴赏\n" +
            "2 - 提交鉴赏(支付完成才能提交)\n" +
            "3 - 鉴赏完成\n" +
            "4 - 鉴赏回退" +
            "5 - 完成评价", example = Mock.NUMBER)
    private Integer identify;

    @ApiModelProperty(value = "鉴赏价格 单位元", example = Mock.NUMBER)
    private BigDecimal amount;
}
