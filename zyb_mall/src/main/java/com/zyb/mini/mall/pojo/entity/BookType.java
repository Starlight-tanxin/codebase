package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

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
@TableName("tb_book_type")
public class BookType extends Model<BookType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String bookType;

    private String typeIcon;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
