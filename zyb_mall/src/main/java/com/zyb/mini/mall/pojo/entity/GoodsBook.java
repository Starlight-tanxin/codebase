package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zyb.mini.mall.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@ApiModel
@Data
@TableName("tb_goods_book")
public class GoodsBook extends Model<GoodsBook> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品编号
     */
    private String goodsNo;
    /**
     * 商品主图
     */
    private String mainImg;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 作者
     */
    private String author;

    /**
     * 是否收藏
     * 0 - 不是收藏
     * 1 - 是收藏
     */
    private Boolean isCollection;

    /**
     * 年份类别
     */
    private Integer yearId;

    /**
     * 纸张
     */
    private String bookPaper;

    /**
     * 尺寸
     */
    private String bookSize;

    private String bookDesc;

    /**
     * 库存
     */
    private Long bookNum;

    /**
     * 种类
     */
    private Integer bookTypeId;

    /**
     * 其他种类的id（外键）
     */
    private Integer bookOtherTypeId;

    /**
     * 是否上架
     * 0 - 下架
     * 1 - 上架
     */
    private Boolean isUp;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 商品价格 单位元
     */
    private BigDecimal goodsPrice;

    /**
     * 优先级,图书优先级,优先级数值越高优先级越高
     */
    private Integer bookPriority;

    /**
     * 外键品相类别
     */
    private Integer phaseTypeId;

    @TableField(exist = false)
    private List<BookImg> imgList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
