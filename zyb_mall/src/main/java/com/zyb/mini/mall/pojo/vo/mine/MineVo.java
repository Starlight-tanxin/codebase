package com.zyb.mini.mall.pojo.vo.mine;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 21:20.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Data
public class MineVo {


    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImg;
    /**
     * 余额
     */
    private BigDecimal actualAmount;
    /**
     * 他的姓
     */
    private String surnameName;


    /**
     * 手机号
     */
    private String mobile;


    // 是不是这个姓打卡第一名
    private boolean topOne;


    private Integer userType;
}
