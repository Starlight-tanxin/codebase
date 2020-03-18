package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author tanxin
 * @Data <p>
 * 鉴赏记录（鉴赏订单）
 * </p>
 * @since 2019-10-27
 */
@Data
@TableName("tb_identify")
public class Identify extends Model<Identify> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id  修改时必传", example = Mock.NUMBER, required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id外键
     */
    @ApiModelProperty(hidden = true)
    private Long userId;

    @TableField(exist = false)
    private String userName;

    /**
     * 鉴赏得古董名字
     */
    @NotBlank
    @ApiModelProperty(value = "鉴赏古董的名字", example = "青花瓷", required = true)
    private String antiqueName;

    /**
     * 鉴赏得古董描述
     */
    @ApiModelProperty(value = "古董描述", example = "82年的雪碧", required = true)
    private String antiqueDetail;

    /**
     * 专家id外键
     */
    @NotNull
    @ApiModelProperty(value = "专家id", example = Mock.NUMBER, required = true)
    private Long userProId;

    @TableField(exist = false)
    private String userProName;

    /**
     * 本次鉴赏价格
     */
    @ApiModelProperty(value = "鉴赏价格 单位元", example = Mock.NUMBER)
    private BigDecimal amount;


    @ApiModelProperty(value = "鉴赏状态\n" +
            "1 - 创建鉴赏\n" +
            "2 - 提交鉴赏(支付完成才能提交)\n" +
            "3 - 鉴赏完成\n" +
            "4 - 鉴赏回退" +
            "5 - 完成评价", example = Mock.NUMBER)
    private Integer identify;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdTime;

    /**
     * 专家回复消息
     */
    @ApiModelProperty(value = "专家回复消息", example = "正统年产雪碧", required = true)
    private String proReplyMsg;

    @ApiModelProperty(value = "专家回复时间", required = true)
    private LocalDateTime proReplyTime;

    /**
     * 专家回复鉴赏得朝代
     * (外键，年代id)
     */
    @ApiModelProperty(value = "专家回复鉴赏得朝代id", example = Mock.NUMBER, required = true)
    private Integer proYearId;

    /**
     * 朝代
     */
    @TableField(exist = false)
    private String proYear;

    /**
     * 退回理由
     */
    @ApiModelProperty(value = "退回理由", example = "无理由退款")
    private String proBackMsg;

    /**
     * 退回得其他原因
     */
    @ApiModelProperty(value = "退回得其他原因", example = "没有原因")
    private String proBackDetail;

    /**
     * 退回得其他原因
     */
    @ApiModelProperty(value = "0-假 1-真 2-存疑", example = Mock.NUMBER)
    private Integer checkState;


    @TableField(exist = false)
    private String proName; //专家名称

    @TableField(exist = false) //鉴赏照片
    private List<IdentifyImg> identifyImgs;

    @TableField(exist = false) //评价
    private MaintainEvaluate maintainEvaluate;

    @ApiModelProperty(value = "图片 多张使用 英文,分割")
    @TableField(exist = false)
    private String imgAryStr;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
