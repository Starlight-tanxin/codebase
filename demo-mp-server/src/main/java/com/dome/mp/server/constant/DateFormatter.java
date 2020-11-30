package com.dome.mp.server.constant;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 初始化一下常用的日期格式化规则
 *
 * @author tanxin
 * @version 0.1
 * @date 2019/3/22 14:08
 */

public interface DateFormatter {

    /**
     * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
     */
    DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * DateTimeFormatter.ofPattern("yyMMddHHmmss")
     */
    DateTimeFormatter DATE_TIME_ORDER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * DateTimeFormatter.ofPattern("yyyy-MM-dd")
     */
    DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * DateTimeFormatter.ofPattern("yyyy年MM月")
     */
    DateTimeFormatter DATE_Y_M = DateTimeFormatter.ofPattern("yyyy年MM月");

    /**
     * DateTimeFormatter.ofPattern("yyyyMMdd")
     */
    DateTimeFormatter DATE_BY_ID_CARD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * DateTimeFormatter.ofPattern("HH:mm:ss")
     */
    DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * DateTimeFormatter.ofPattern("HH:mm")
     */
    DateTimeFormatter TIME_TO_HM = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     */
    SimpleDateFormat SDF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


}
