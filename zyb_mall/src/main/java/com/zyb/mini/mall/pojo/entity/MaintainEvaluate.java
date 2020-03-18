package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评价表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@ApiModel
@Data
@TableName("tb_maintain_evaluate")
public class MaintainEvaluate extends Model<MaintainEvaluate> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评价对象的id （鉴赏就是鉴赏的id） 修复就是修复的id", example = Mock.NUMBER, required = true)
    @NotNull
    private Long maintainId;

    @ApiModelProperty(value = "评价内容", required = true)
    @NotBlank
    private String evaMsg;

    /**
     * 评价度
     * 1 - 满意
     * 2 - 一般
     * 3 - 不满意
     */
    @ApiModelProperty(value = "评价度（满意度）  1 - 满意\n  2 - 一般\n 3 - 不满意", example = Mock.NUMBER, required = true)
    @NotNull
    private Integer evaType;

    @ApiModelProperty(value = "图片1")
    private String evaImg_1;

    @ApiModelProperty(value = "图片2")
    private String evaImg_2;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdTime;

    //创建者id
    @ApiModelProperty(hidden = true)
    private Long userId;

    // 专家id
    @ApiModelProperty(value = "专家id", example = Mock.NUMBER, required = true)
    @NotNull
    private Long proId;
    /**
     * 业务类型 false  修复， true 鉴赏
     */
    @ApiModelProperty(value = "业务类型 0-修复  1-鉴赏", example = Mock.TRUE, required = true)
    @NotNull
    private Boolean businessType;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
