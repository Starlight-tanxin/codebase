package com.dome.mp.server.utils;


/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/3/31 10:19
 */
public class IntUtils {

    private final static char ZERO = '0';

    private final static int[] SIZE_TABLE = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    public static int sizeOfInt(int num) {
        for (int i = 0; ; i++)
            if (num <= SIZE_TABLE[i])
                return i + 1;
    }

    /**
     * 填充0到多少位
     *
     * @param num    原数字
     * @param length 填充到多少位
     * @return 填充后字符串
     */
    public static String fillZeroByLength(int num, int length) {
        int old_size = sizeOfInt(num);
        char[] zeroAry = new char[length - old_size];
        for (int i = 0; i < zeroAry.length; i++) {
            zeroAry[i] = ZERO;
        }
        String zeroStr = new String(zeroAry);
        return zeroStr + num;
    }
    
}
