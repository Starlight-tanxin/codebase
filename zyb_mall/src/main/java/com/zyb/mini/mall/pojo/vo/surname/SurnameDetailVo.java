package com.zyb.mini.mall.pojo.vo.surname;

import lombok.Data;

import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/3 星期日 8:37.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 * <p>
 * 某姓打卡详情
 */
@Data
public class SurnameDetailVo {


    /**
     * 已经打卡人数
     */
    private Integer count;
    /**
     * 今日最早打卡
     */
    private String earlyBird;
    /**
     * 累计打卡第一
     */
    private String topOne;
    /**
     * 当前姓氏
     */
    private String surnameName;

    // 他今天有没有打过卡
    private boolean hasPunch;
    @Data
    public static class Column {
        /**
         * 用户名
         */
        private String nickname;
        /**
         * 累计打卡数量
         */
        private Integer count;
    }

    private List<Column> columns;
}
