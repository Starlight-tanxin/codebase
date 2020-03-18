package com.zyb.mini.mall.pojo.vo.city;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanxin
 * @date 2019/8/28
 */
@Data
public class ProvinceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String provinceNo;

    private String province;

    private List<CityVO> cityList;
}
