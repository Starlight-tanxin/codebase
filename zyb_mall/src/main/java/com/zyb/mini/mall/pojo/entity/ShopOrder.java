package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商城订单表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@Accessors
@TableName("tb_shop_order")
public class ShopOrder extends Model<ShopOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
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
    private Integer orderState;

    /**
     * 用户id外键
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime cretaedTime;

    /**
     * 完成时间
     */
    private LocalDateTime completeTime;

    /**
     * 外键
     */
    private Long userAddressId;

    /**
     * 快递费用
     * 单位元
     */
    private BigDecimal expressPrice;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递公司
     */
    private String expressCompanyNo;
    /**
     * 订单价格 单位元
     */
    private BigDecimal orderPrice;

    /**
     * 付款时间
     */
    private LocalDateTime payTime;
    /**
     * 发货时间
     */
    private LocalDateTime sendGoodsTime;

    @TableField(exist = false)
    private List<ShopOrderDetail> orderDetails;

    @ApiModelProperty(value = "物流信息")
    @TableField(exist = false)
    private ExpressDTO expressInfo;

    @ApiModelProperty(value = "收获地址")
    @TableField(exist = false)
    private UserAddress userAddress;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
