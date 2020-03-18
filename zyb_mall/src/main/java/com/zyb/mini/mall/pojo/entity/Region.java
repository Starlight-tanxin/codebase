package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 区县信息表
 * </p>
 *
 * @author tanxin
 * @since 2019-08-28
 */
@Data
@TableName("tb_region")
public class Region extends Model<Region> {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区县编码
     */
    private String regionNo;

    /**
     * 区县名称
     */
    private String region;

    /**
     * 所属城市编码
     */
    private String cityNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
