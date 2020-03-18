package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 订单得物流信息表
 * </p>
 *
 * @author tanxin
 * @since 2019-11-15
 */
@Data
@TableName("tb_order_express")
public class OrderExpress extends Model<OrderExpress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 运单编码
     */
    private String expressNo;

    /**
     * 快递公司编码
     */
    private String expressCompanyNo;

    /**
     * 运单信息
     */
    private String expressInfoJson;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
