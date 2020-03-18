package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 城市信息表
 * </p>
 *
 * @author tanxin
 * @since 2019-08-28
 */
@Data
@TableName("tb_city")
public class City extends Model<City> {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 城市编码
     */
    private String cityNo;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 所属省份编码
     */
    private String provinceNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
