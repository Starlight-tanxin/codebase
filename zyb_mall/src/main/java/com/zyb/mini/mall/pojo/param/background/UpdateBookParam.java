package com.zyb.mini.mall.pojo.param.background;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.entity.BookImg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jpping
 * @date 2019/11/7
 */
@ApiModel("修改商品参数")
@Data
@Accessors
public class UpdateBookParam {
    private static final long serialVersionUID = 8800993860253556075L;

    @ApiModelProperty(value = "商品编号", required = true)
    @NotNull(message = "商品编号不能为空")
    private String goodsNo;

    @ApiModelProperty(value = "商品主id", example = Mock.NUMBER, required = true)
    @NotNull(message = "商品id不能为空")
    private Long id;

    @ApiModelProperty(value = "商品主图", required = true)
    @NotNull(message = "商品主图不能为空")
    private String mainImg;

    @ApiModelProperty(value = "商品名字", required = true)
    @NotNull(message = "商品名字不能为空")
    private String goodsName;

    @ApiModelProperty(value = "作者", required = true)
    @NotNull(message = "商品名字不能为空")
    private String author;

    @ApiModelProperty(value = "是否收藏：\n0.不是收藏\n1.是收藏", example = Mock.TRUE, required = true)
    @NotNull(message = "是否收藏不能为空")
    private Boolean isCollection;

    @ApiModelProperty(value = "年份类别", example = Mock.NUMBER, required = true)
    @NotNull(message = "年份类别不能为空")
    private Integer yearId;

    @ApiModelProperty(value = "纸张", required = true)
    @NotNull(message = "纸张不能为空")
    private String bookPaper;

    @ApiModelProperty(value = "优先级", example = Mock.NUMBER)
    private Integer bookPriority;

    @ApiModelProperty(value = "尺寸", required = true)
    @NotNull(message = "尺寸不能为空")
    private String bookSize;

    @ApiModelProperty(value = "图书描述", required = true)
    @NotNull(message = "图书描述不能为空")
    private String bookDesc;

    @ApiModelProperty(value = "库存", example = Mock.NUMBER, required = true)
    @NotNull(message = "库存不能为空")
    private Long bookNum;

    @ApiModelProperty(value = "种类", example = Mock.NUMBER, required = true)
    @NotNull(message = "种类不能为空")
    private Integer bookTypeId;


    @ApiModelProperty(value = "其他种类", example = Mock.NUMBER, required = true)
    private Integer bookOtherTypeId;

    @ApiModelProperty(value = "是否上架：\n0.下架\n1.上架", example = Mock.NUMBER, required = true)
    @NotNull(message = "是否上架不能为空")
    private Boolean isUp;

    @ApiModelProperty(value = "商品价格 单位元", example = Mock.NUMBER, required = true)
    @NotNull(message = "商品价格不能为空")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "外键品相类别", example = Mock.NUMBER, required = true)
    @NotNull(message = "外键品相类别不能为空")
    private Integer phaseTypeId;

    @TableField(exist = false)
    private List<BookImg> imgList;
}
