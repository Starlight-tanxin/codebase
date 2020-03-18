package com.zyb.mini.mall.pojo.param.background;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Jpping
 * @date 2019/11/8
 */
@ApiModel("修改鉴赏专家参数")
@Data
@Accessors
public class UpdateUserProParam {
    private static final long serialVersionUID = 8800993860253556075L;

    @ApiModelProperty(value = "鉴赏专家id", example = Mock.NUMBER, required = true)
    @NotNull(message = "鉴赏专家id不能为空")
    private Long id;

    @ApiModelProperty(value = "鉴赏种类分类", example = Mock.NUMBER, required = true)
    @NotNull(message = "鉴赏种类分类不能为空")
    private Integer proTypeId;

    @ApiModelProperty(value = "用户id", example = Mock.NUMBER, required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "优先级", example = Mock.NUMBER)
    private Integer userProPriority;

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

    @ApiModelProperty(value = "鉴赏价格 单位元", example = Mock.NUMBER, required = true)
    @NotNull(message = "鉴赏价格不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "鉴定类别", required = true)
    @NotNull(message = "鉴定类别不能为空")
    @TableField(exist = false)
    private String typeName;
}
