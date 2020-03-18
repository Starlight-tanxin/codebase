package com.zyb.mini.mall.pojo.vo.Order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Jpping
 * @date 2019/11/10
 */
@Data
public class IdentifyOrderListVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 鉴赏得古董名字
     */
    @ApiModelProperty(value = "鉴赏得古董名字")
    private String antiqueName;


    /**
     * 用户id外键
     */
    @ApiModelProperty(value = "用户id外键")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 鉴赏得古董描述
     */
    @ApiModelProperty(value = "鉴赏得古董描述")
    private String antiqueDetail;

    /**
     * 本次鉴赏价格
     */
    @ApiModelProperty(value = "本次鉴赏价格 单位元")
    private BigDecimal amount;

    /**
     * 鉴赏状态
     * 1 - 创建鉴赏
     * 2 - 提交鉴赏(支付完成才能提交)
     * 3 - 鉴赏完成
     * 4 - 鉴赏回退
     */
    @ApiModelProperty(value = "鉴赏状态:\n1.创建鉴赏\n2.提交鉴赏\n3.鉴赏完成\n4.鉴赏回退")
    private Integer identify;

    /**
     * 专家id外键
     */
    @ApiModelProperty(value = "专家id外键")
    private Integer userProId;

    /**
     * 专家姓名
     */
    @ApiModelProperty(value = "专家姓名")
    private String userProName;
}
