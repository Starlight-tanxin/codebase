package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 省份信息表
 * </p>
 *
 * @author tanxin
 * @since 2019-08-28
 */
@Data
@TableName("tb_province")
public class Province extends Model<Province> {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String provinceNo;

    private String province;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
