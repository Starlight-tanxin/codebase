package com.zyb.mini.mall.pojo.vo.city;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanxin
 * @date 2019/8/28
 */
@Data
public class CityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 城市编码
     */
    private String cityNo;

    /**
     * 城市名称
     */
    private String city;

    private List<RegionVO> regionList;
}
