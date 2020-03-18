package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 鉴赏专家表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-26
 */
@Data
@TableName("tb_user_pro")
public class UserPro extends Model<UserPro> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 鉴赏种类分类
     */
    private Integer proTypeId;

    /**
     * 用户id（外键）
     */
    private Long userId;

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
     * 优先级 ,优先级数值越高优先级越高
     */
    private Integer userProPriority;

    private LocalDateTime createdTime;

    /**
     * 专家价格（鉴赏） 单位元
     */
    private BigDecimal price;

    @TableField(exist = false)  //评价list
    private List<MaintainEvaluate> maintainEvaluates;

    @TableField(exist = false)
    private Integer IdentifyNum; //鉴定次数

    @TableField(exist = false)
    private String typeName; //鉴定类别


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
