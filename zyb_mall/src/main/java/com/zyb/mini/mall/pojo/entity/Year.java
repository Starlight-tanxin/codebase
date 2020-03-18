package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 年份列别表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-26
 */
@Data
@TableName("tb_year")
public class Year extends Model<Year> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年份名
     */
    private String yearName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
