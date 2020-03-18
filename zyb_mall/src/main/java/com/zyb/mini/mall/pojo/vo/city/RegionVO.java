package com.zyb.mini.mall.pojo.vo.city;

import lombok.*;

import java.io.Serializable;

/**
 * @author tanxin
 * @date 2019/8/28
 */
@Data
public class RegionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区县编码
     */
    private String regionNo;

    /**
     * 区县名称
     */
    private String region;
}
