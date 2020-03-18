package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 官方回复的修复图片
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_maintain_company_img")
public class MaintainCompanyImg extends Model<MaintainCompanyImg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * oss图片名
     */
    private String imgName;

    private String imgUrl;

    /**
     * 图片顺序
     */
    private Integer imgIdx;

    /**
     * 外汇官放的消息回复
     */
    private Integer maintainMsgId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
