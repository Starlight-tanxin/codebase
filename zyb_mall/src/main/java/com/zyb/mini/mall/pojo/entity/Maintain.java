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
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 修复订单
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_maintain")
public class Maintain extends Model<Maintain> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 用户id（外键）
     */
    @ApiModelProperty(hidden = true)
    private Long userId;

    @TableField(exist = false)
    private String userName;


    /**
     * 修复订单编号
     */
    private String antiqueNo;

    /**
     * 修复得古董名
     */
    @NotBlank
    @ApiModelProperty(value = "修复得古董名", example = "青花瓷", required = true)
    private String antiqueName;

    /**
     * 古董类别id
     */
    @NotNull
    @ApiModelProperty(value = "古董类别id", example = Mock.NUMBER, required = true)
    private Long antiqueTypeId;

    @TableField(exist = false)
    private String antiqueType;

    /**
     * 修复物品描述
     */
    @NotBlank
    @ApiModelProperty(value = "修复物品描述", example = "没救了 救不了 告辞", required = true)
    private String maintainDetail;

    /**
     * 收获地址外键
     */
    @ApiModelProperty(value = "收获地址外键id", example = Mock.NUMBER)
    private Long userAddressId;

    @TableField(exist = false)
    private String userAddress;

    /**
     * 是否自提
     * 0 - 否
     * 1 - 是
     */
    @NotNull
    @ApiModelProperty(value = "是否自提 false :否，true:是", example = Mock.FALSE, required = true)
    private Boolean isSelfTake;

    /**
     * 修复得专家
     */
    @NotNull
    @ApiModelProperty(value = "专家id", example = Mock.NUMBER, required = true)
    private Long maintainProId;

    @TableField(exist = false)
    private String maintainPro;


    @ApiModelProperty(value = "\n" +
            "修复状态\n" +
            "1 - 创建修复订单(等待官方回复)\n" +
            "2 - 官方回复\n" +
            "3 - 支付首款（修复中）\n" +
            "4 - 修复完成（第二次官方回复）\n" +
            "5 - 支付尾款 (等待用户评价)\n" +
            "6 - 用户评价",
            example = Mock.NUMBER,
            required = true)
    private Integer maintainState;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdTime;

    /**
     * 官方修复金额 单位元
     */
    @ApiModelProperty(value = "官方修复金额 单位元", example = Mock.NUMBER, required = true)
    private BigDecimal cmMaintainAmount;

    /**
     * 官方修复天数 整数
     */
    @ApiModelProperty(value = "官方修复天数 整数", example = Mock.NUMBER, required = true)
    private Integer cmMaintainDay;

    /**
     * 官方给得到达时间
     */
    @ApiModelProperty(value = "官方给得到达时间", required = true)
    private LocalDateTime cmDate;

    /**
     * 官方的收获人
     */
    @ApiModelProperty(value = "官方的收获人", example = "我", required = true)
    private String cmName;

    /**
     * 官方的收获地址
     */
    @ApiModelProperty(value = "官方的收获地址", example = "湖南", required = true)
    private String cmAddress;

    /**
     * 官方的联系方式
     */
    @ApiModelProperty(value = "官方的联系方式", example = "176xxxxxxxx", required = true)
    private String cmMobile;
    /**
     * 修复开始时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime fixedStartTime;
    /**
     * 修复结束时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime fixedEndTime;

    @ApiModelProperty(value = "官方的回复", example = "123")
    @TableField(exist = false)
    private String preMaintainMsgStr;

    @TableField(exist = false) //预回复
    private MaintainMsg preMaintainMsg;

    @TableField(exist = false) //回复
    private MaintainMsg maintainMsg;


//    @TableField(exist = false) //用户提交修复前的图片
//    private List<MaintainImg> userMaintainImgs;

    @TableField(exist = false)
    private List<String> userMaintainImgs;

    @ApiModelProperty(value = "图片 多张使用 英文,分割")
    @TableField(exist = false)
    private String imgAryStr;


    @ApiModelProperty(value = "官方预回复的图片")
    @TableField(exist = false)
    private List<String> preMaintainMsgImgs;

    @ApiModelProperty(value = "正式官方回复的图片")
    @TableField(exist = false)
    private List<String> maintainMsgImgs;

    @ApiModelProperty(value = "支付的金额", example = Mock.NUMBER)
    @TableField(exist = false)
    private BigDecimal payAmount;

    public BigDecimal getPayAmount() {
        if (this.cmMaintainAmount != null) {
            this.payAmount = this.cmMaintainAmount.divide(BigDecimal.valueOf(2), 2, BigDecimal.ROUND_HALF_UP);
        }
        return payAmount;
    }

    public List<String> getPreMaintainMsgImgs() {
        if (this.preMaintainMsgImgs == null) {
            this.preMaintainMsgImgs = new ArrayList<>();
        }
        return preMaintainMsgImgs;
    }

    public List<String> getMaintainMsgImgs() {
        if (this.maintainMsgImgs == null) {
            this.maintainMsgImgs = new ArrayList<>();
        }
        return maintainMsgImgs;
    }

    public List<String> getUserMaintainImgs() {
        if (this.userMaintainImgs == null) {
            this.userMaintainImgs = new ArrayList<>();
        }
        return userMaintainImgs;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
