package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 修复得专家表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_maintain_pro")
public class MaintainPro extends Model<MaintainPro> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 鉴赏种类分类
     */
    private Integer proTypeId;
    /**
     * 优先级，优先级数值越高优先级越高
     */
    private Integer maintainProPriority;

    @TableField(exist = false)
    private String typeName; //鉴定类别

    private LocalDateTime createdTime;
    @TableField(exist = false) //评价统计
    private Integer evaTypeCount;

    @TableField(exist = false) // 官方回复的图片
    private List<String> maintainCompanyStrImgAry;

    @TableField(exist = false) // 用户的图片
    private List<String> maintainStrImgAry;

//    @TableField(exist = false) //非预先回复的图片
//    private List<MaintainCompanyImg> maintainCompanyImgs;
//
//    @TableField(exist = false) //用户修复前提交的图片
//    private List<MaintainImg> maintainImgs;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public List<String> getMaintainCompanyStrImgAry() {
        if (this.maintainCompanyStrImgAry == null) {
            this.maintainCompanyStrImgAry = new ArrayList<>();
        }
        return this.maintainCompanyStrImgAry;
    }

    public List<String> getMaintainStrImgAry() {
        if (this.maintainStrImgAry == null) {
            this.maintainStrImgAry = new ArrayList<>();
        }
        return this.maintainStrImgAry;
    }
}
