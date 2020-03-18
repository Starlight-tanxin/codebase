package com.zyb.mini.mall.pojo.vo;

import lombok.Data;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/5 星期二 23:34.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */

@Data
public class BillboardDetailVo {



    // 姓氏id
    private long surnameId;
    // 姓氏
    private String name;
    // 总次数
    private long count;
    // 日均
    private long avg;

}
