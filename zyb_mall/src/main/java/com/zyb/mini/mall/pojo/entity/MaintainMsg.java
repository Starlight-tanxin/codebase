package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 修复的官方回复表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_maintain_msg")
public class MaintainMsg extends Model<MaintainMsg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 修复单id
     */
    @NotNull
    @ApiModelProperty(value = "修复单id", example = Mock.NUMBER, required = true)
    private Long maintainId;

    /**
     * 是不是预修复回复
     * 1 - 是预修复回复
     * 0 - 不是预修复回复
     */
    @NotNull
    @ApiModelProperty(value = "是不是预修复回复\n" +
            "     * 1 - 是预修复回复\n" +
            "     * 0 - 不是预修复回复", example = Mock.TRUE, required = true)
    private Boolean isFixed;

    /**
     * 回复的消息
     */
    @NotBlank
    @ApiModelProperty(value = "回复的消息", example = "111212312312", required = true)
    private String cmMaintainMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdTime;

    @TableField(exist = false) //回复图片
    private List<MaintainCompanyImg> maintainCompanyImgs;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
