package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 商品其他分类
 * </p>
 *
 * @author tanxin
 * @since 2019-10-30
 */
@Data
@TableName("tb_book_other_type")
public class BookOtherType extends Model<BookOtherType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String typeName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
