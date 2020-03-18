package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@TableName("tb_book_img")
public class BookImg extends Model<BookImg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long goodsBookId;

    /**
     * oss图片名
     */
    private String imgName;

    private String imgUrl;

    /**
     * 图片顺序
     */
    private Integer imgIdx;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
