package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 收获地址管理
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_user_address")
public class UserAddress extends Model<UserAddress> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 外键用户
     */
    private Long userId;

    /**
     * 收货人名字
     */
    private String realname;

    /**
     * 收货人手机号
     */
    private String mobile;

    /**
     * 省编码
     */
    private String provinceNo;

    /**
     * 市
     */
    private String cityNo;

    /**
     * 区
     */
    private String regionNo;

    /**
     * 详细收货地址
     */
    private String addressDetail;

    /**
     * 是否默认收获地址
     * 1-是
     * 0-否
     */
    private Boolean isDefault;

    private LocalDateTime createdTime;

    /**
     * 省
     */
    @TableField(exist = false)
    private String province;

    /**
     * 市
     */
    @TableField(exist = false)
    private String city;

    /**
     * 区
     */
    @TableField(exist = false)
    private String region;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
