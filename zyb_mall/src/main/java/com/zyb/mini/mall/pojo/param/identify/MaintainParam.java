package com.zyb.mini.mall.pojo.param.identify;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzw
 */
@ApiModel("修复订单参数对象")
@Data
public class MaintainParam extends BasePageParam implements Serializable {
    private static final long serialVersionUID = -1615007190870968827L;

    @ApiModelProperty(value = "\n" +
            "修复状态\n" +
            "1 - 创建修复订单(等待官方回复)\n" +
            "2 - 官方回复\n" +
            "3 - 支付首款（修复中）\n" +
            "4 - 修复完成（第二次官方回复）\n" +
            "5 - 支付尾款 \n" +
            "6 - 用户评价"
            , example = Mock.NUMBER)
    private Integer maintainState;
}
