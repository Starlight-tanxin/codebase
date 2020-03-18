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
 * 商城轮播图
 * </p>
 *
 * @author tanxin
 * @since 2019-10-29
 */
@Data
@TableName("tb_shop_banner")
public class ShopBanner extends Model<ShopBanner> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品外键
     */
    private Long goodsBookId;

    /**
     * 主图
     */
    private String mainImg;

    /**
     * 轮播序列
     */
    private Integer bannerIdx;

    private LocalDateTime createdTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
