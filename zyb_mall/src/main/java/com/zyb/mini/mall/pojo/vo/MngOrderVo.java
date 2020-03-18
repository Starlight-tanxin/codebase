package com.zyb.mini.mall.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/10 星期日 13:14.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */

@Data
public class MngOrderVo {


    private long orderId;

    private String orderNo;
    private String nickname;
    private String mainImg;
    private String amount;
    private Date time;
    private String wl;
    private Integer state;
}
