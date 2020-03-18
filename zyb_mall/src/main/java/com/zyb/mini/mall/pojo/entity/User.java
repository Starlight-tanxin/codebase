package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String wxUnionId;

    private String wxOpenId;

    /**
     * 用户类型
     * 1- 普通用户
     * 2- 专家用户
     * 3 - 管理员用户
     */
    private Integer userType;

    /**
     * 用户等级
     */
    private Integer userLevel;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 创建时间
     */
    private LocalDateTime cretaedTime;

    /**
     * 授权后微信拿到得所有信息
     */
    private String wxInfo;

    /**
     * 专家id（外键）
     */
    private Long userRecId;

    /**
     * 实际金额 单位元
     */
    private BigDecimal actualAmount;

    /**
     * 冻结金额 单位元
     */
    private BigDecimal freezeAmount;

    /**
     * 支付密码
     */
    private String payPwd;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
