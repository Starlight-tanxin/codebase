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
 * 订单评价表
 * </p>
 *
 * @author tanxin
 * @since 2019-11-01
 */
@ApiModel("订单评价")
@Data
@TableName("tb_shop_order_evaluate")
public class ShopOrderEvaluate extends Model<ShopOrderEvaluate> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 外键订单id
     */
    @ApiModelProperty(value = "订单id", example = Mock.NUMBER, required = true)
    @NotNull
    private Long shopOrderId;

    /**
     * 用户id（外键）
     */
    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(value = "评价内容", required = true)
    @NotBlank
    private String eavMsg;

    /**
     * 评价度
     * 1 - 满意
     * 2 - 一般
     * 3 - 不满意
     */
    @ApiModelProperty(value = "满意度\n 1 - 满意\n  2 - 一般\n  3 - 不满意", example = Mock.NUMBER, required = true)
    @NotNull
    private Integer eavType;

    @ApiModelProperty(value = "图片1")
    private String evaImg_1;

    @ApiModelProperty(value = "图片2")
    private String evaImg_2;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
