package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户打卡表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_user_punch")
public class UserPunch extends Model<UserPunch> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓氏id
     */
    private Long surnameId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户打卡次数
     */
    private Long punchCount;

    private LocalDateTime punchDate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
