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
@ApiModel("添加修复的专家")
@Data
@Accessors
public class AddMaintainProParam {
    private static final long serialVersionUID = 8800993860253556075L;


    @ApiModelProperty(value = "手机号", example = Mock.NUMBER, required = true)
    @NotNull(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "真实姓名", example = Mock.NUMBER, required = true)
    @NotNull(message = "真实姓名不能为空")
    private String realname;

    @ApiModelProperty(value = "身份证号", example = Mock.NUMBER, required = true)
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty(value = "个人简历", example = Mock.NUMBER, required = true)
    @NotNull(message = "个人简历不能为空")
    private String proDetail;

    @ApiModelProperty(value = "优先级", example = Mock.NUMBER)
    private Integer maintainProPriority;

    @ApiModelProperty(value = "个人照片", example = Mock.NUMBER, required = true)
    @NotNull(message = "个人照片不能为空")
    private String proImg;

}
