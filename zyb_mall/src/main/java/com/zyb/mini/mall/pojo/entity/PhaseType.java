package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 品相类别
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_phase_type")
public class PhaseType extends Model<PhaseType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类别名
     */
    private String typeName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
