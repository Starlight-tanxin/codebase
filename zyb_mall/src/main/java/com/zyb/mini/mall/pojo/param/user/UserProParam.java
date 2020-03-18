package com.zyb.mini.mall.pojo.param.user;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: Tx
 * @date: 2019/11/22
 */
@Data
public class UserProParam extends BasePageParam {
    private static final long serialVersionUID = 7861244041504097762L;

    @ApiModelProperty(value = "古董类别", example = Mock.NUMBER)
    private Integer antiqueTypeId;

}
