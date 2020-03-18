package com.zyb.mini.mall.pojo.vo.Order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jpping
 * @date 2019/11/8
 */
@Data
public class OrderListVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 订单状态
     * 1 - 代支付
     * 2 - 代发货
     * 3 - 待收货
     * 4 - 待评价
     * 5 - 已评价
     * 6 - 取消订单
     * 7 - 申请退款
     * 0 - 已退款
     */
    @ApiModelProperty(value = "订单状态:\n1.待支付\n2.待发货\n3.待收货\n4.待评价\n5.已评价\n6.取消订单\n7.申请退款\n0.已退款", example = Mock.NUMBER)
    private Integer orderState;

    /**
     * 用户id外键
     */
    @ApiModelProperty(value = "用户id外键", example = Mock.NUMBER)
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String nickName;

    /**
     * 金额
     */
    @ApiModelProperty(value = "金额 单位元", example = Mock.NUMBER)
    private BigDecimal amount;

    /**
     * 头图
     */
    @ApiModelProperty(value = "头图")
    private String mainImg;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = Mock.DATETIME)
    private LocalDateTime cretaedTime;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间", example = Mock.DATETIME)
    private LocalDateTime completeTime;
}
