package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户提交的损坏，需要修复的图片图片
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_maintain_img")
public class MaintainImg extends Model<MaintainImg> {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long maintainId;

    private String imgName;

    private String imgUrl;

    private Integer imgIdx;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
