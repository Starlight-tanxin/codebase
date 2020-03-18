package com.zyb.mini.mall.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 21:34.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Data
public class UserAddressParam {


    private long userId;


    /**
     * 收货人名字
     */
    @NotBlank
    private String realname;

    /**
     * 收货人手机号
     */
    @NotBlank
    private String mobile;

    /**
     * 省编码
     */
    @NotBlank
    private String provinceNo;

    /**
     * 市
     */
    @NotBlank
    private String cityNo;

    /**
     * 区
     */
    @NotBlank
    private String regionNo;


    /**
     * 详细收货地址
     */
    @NotBlank
    private String addressDetail;

    /**
     * 是否默认收获地址
     * 1-是
     * 0-否
     */
    @NotNull
    private Boolean isDefault;


}
