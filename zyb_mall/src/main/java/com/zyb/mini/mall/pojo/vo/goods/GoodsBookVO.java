package com.zyb.mini.mall.pojo.vo.goods;

import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.pojo.entity.BookImg;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanxin
 * @date 2019/11/11
 */
@Data
public class GoodsBookVO implements Serializable {
    private static final long serialVersionUID = -6214493034346148677L;

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
    private String yearName;

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
    private String bookTypeName;

    /**
     * 其他种类的id（外键）
     */
    private String bookOtherTypeName;

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
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 优先级,图书优先级,优先级数值越高优先级越高
     */
    private Integer bookPriority;

    /**
     * 外键品相类别
     */
    private String phaseTypeName;

    private List<BookImg> imgList;

    private List<String> imgArray;

    public List<String> getImgArray() {
        if (this.imgArray == null && this.imgList != null) {
            imgArray = imgList.stream().map(BookImg::getImgUrl).collect(Collectors.toList());
        }
        if (this.imgArray == null) {
            imgArray = new ArrayList<>();
        }
        return imgArray;
    }
}
