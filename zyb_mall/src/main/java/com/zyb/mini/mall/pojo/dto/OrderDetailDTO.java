package com.zyb.mini.mall.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanxin
 * @date 2019/10/31
 */
@Data
public class OrderDetailDTO implements Serializable {
    private static final long serialVersionUID = 5795386477235292237L;

    private Long goodsBookId;

    private Integer num;

    private BigDecimal goodsPrice;

}
