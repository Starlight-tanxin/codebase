package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author tanxin
 * @since 2019-11-15
 */
@Data
@TableName("tb_express_company")
public class ExpressCompany extends Model<ExpressCompany> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 快递公司名字
     */
    private String companyName;

    /**
     * 快递公司编号
     */
    private String companyNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
