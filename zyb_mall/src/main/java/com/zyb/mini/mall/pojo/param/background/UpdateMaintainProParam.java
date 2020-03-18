package com.zyb.mini.mall.pojo.param.background;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author Jpping
 * @date 2019/11/7
 */
@ApiModel("添加商品参数")
@Data
@Accessors
public class UpdateMaintainProParam {
    private static final long serialVersionUID = 8800993860253556075L;


    @ApiModelProperty(value = "修复专家id", example = Mock.NUMBER, required = true)
    @NotNull(message = "修复专家id不能为空")
    private Long id;

    @ApiModelProperty(value = "优先级", example = Mock.NUMBER)
    private Integer maintainProPriority;

    @ApiModelProperty(value = "手机号", required = true)
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "真实姓名", required = true)
    @NotNull(message = "真实姓名不能为空")
    private String realname;

    @ApiModelProperty(value = "身份证号", required = true)
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty(value = "个人简历", required = true)
    @NotNull(message = "个人简历不能为空")
    private String proDetail;


    @ApiModelProperty(value = "个人照片", required = true)
    @NotNull(message = "个人照片不能为空")
    private String proImg;
}
