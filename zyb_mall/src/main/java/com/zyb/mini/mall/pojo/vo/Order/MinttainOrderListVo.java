package com.zyb.mini.mall.pojo.vo.Order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Jpping
 * @date 2019/11/10
 */
@Data
public class MinttainOrderListVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 修复得古董名
     */
    @ApiModelProperty(value = "修复得古董名")
    private String antiqueName;

    /**
     * 修复状态
     * 1 - 创建修复订单(等待官方回复)
     * 2 - 官方回复完成
     * 3 - 支付收款（修复中）
     * 4 - 修复完成
     */
    @ApiModelProperty(value = "\n" +
            "修复状态\n" +
            "1 - 创建修复订单(等待官方回复)\n" +
            "2 - 官方回复\n" +
            "3 - 支付首款（修复中）\n" +
            "4 - 修复完成（第二次官方回复）\n" +
            "5 - 支付尾款 \n" +
            "6 - 用户评价"
            , example = Mock.NUMBER)
    private Integer maintainState;

    /**
     * 用户id外键
     */
    @ApiModelProperty(value = "用户id外键", example = Mock.NUMBER)
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 古董类别id
     */
    @ApiModelProperty(value = "古董类别id", example = Mock.NUMBER)
    private Long antiqueTypeId;

    /**
     * 古董类别名称
     */
    @ApiModelProperty(value = "古董类别名称")
    private String antiqueType;

    /**
     * 修复物品描述
     */
    @ApiModelProperty(value = "修复物品描述")
    private String maintainDetail;

    /**
     * 是否自提
     */
    @ApiModelProperty(value = "是否自提:\n1.是\n0.否", example = Mock.TRUE)
    private Boolean isSelfTake;

    /**
     * 到达时间
     */
    @ApiModelProperty(value = "到达时间", example = Mock.DATETIME)
    private LocalDateTime cmDate;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = Mock.DATETIME)
    private LocalDateTime createdTime;

    /**
     * 修复得专家
     */
    @ApiModelProperty(value = "修复得专家", example = Mock.NUMBER)
    private Long maintainProId;

    /**
     * 修复得专家姓名
     */
    @ApiModelProperty(value = "修复得专家姓名")
    private String maintainPro;
}
