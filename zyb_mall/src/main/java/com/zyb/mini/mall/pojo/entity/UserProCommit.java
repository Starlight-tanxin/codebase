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
 * 专家审核信息表表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_user_pro_commit")
public class UserProCommit extends Model<UserProCommit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String mobile;

    private String realname;

    private String idCard;

    /**
     * 推荐专家名
     */
    private String recRealname;

    /**
     * 推荐专家手机号
     */
    private String recMobile;

    /**
     * 个人简历
     */
    private String proDetail;

    /**
     * 个人照片
     */
    private String proImg;

    /**
     * 鉴赏类别（id）
     */
    private String antiqueTypeId;

    /**
     * 用户id（外键）
     */
    private String userId;

    /**
     * 审核状态
     * 默认1 - 提交中
     * 2 - 审核通过
     * 3 - 驳回
     */
    private Integer state;

    private LocalDateTime cretaedTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
