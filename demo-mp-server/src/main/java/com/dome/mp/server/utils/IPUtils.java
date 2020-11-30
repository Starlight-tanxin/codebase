package com.dome.mp.server.utils;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/4/7 17:24
 */
public class IPUtils {

    public static boolean isIP(String str){
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        // 判断ip地址是否与正则表达式匹配
        if (str.matches(regex)) {
            String[] arr = str.split("\\.");
            for (int i = 0; i < 4; i++) {
                int temp = Integer.parseInt(arr[i]);
                //如果某个数字不是0到255之间的数 就返回false
                if (temp < 0 || temp > 255) {
                   return false;
                }
            }
           return true;
        } else {
            return false;
        }

    }
}
