package com.dome.mp.server.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author TanXin
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "userId", type = IdType.AUTO)
    private Integer userId;

    private String nickname;

    private String realname;

    private String username;

    private String password;

    private Integer sex;

    private String address;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
