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
@ApiModel("鉴赏记录分页参数对象")
@Data
public class IdentifyParam extends BasePageParam implements Serializable {

    private static final long serialVersionUID = -4028548346666308065L;

    @ApiModelProperty(value = "是否有专家回复 false 不做判断, true  有， 结果集里专家回复不能为空", example = Mock.FALSE)
    private Boolean isReplay;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(hidden = true)
    private Integer state;

    @ApiModelProperty(hidden = true)
    private Long userProId;


}
