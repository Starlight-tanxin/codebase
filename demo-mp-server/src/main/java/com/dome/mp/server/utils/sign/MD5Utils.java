package com.dome.mp.server.utils.sign;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author tanxin
 * @date 2019/6/14
 */
public final class MD5Utils {

    private MD5Utils() {
    }

    private static final String DEFAULT_CHART = "UTF-8";

    private static final String MD5 = "MD5";

    private static final char[] HEX_DIGITS_LOW = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] HEX_DIGITS_UP = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String byteArrayToHexString(byte[] b) {
//        StringBuilder resultSb = new Str
//        ingBuilder();
//        for (byte value : b) resultSb.append(byteToHexString(value));
//        return resultSb.toString();
        char[] str = new char[b.length * 2];
        int k = 0;
        for (byte byte0 : b) {
            str[k++] = HEX_DIGITS_LOW[byte0 >>> 4 & 0xf];
            str[k++] = HEX_DIGITS_LOW[byte0 & 0xf];
        }
        return new String(str);
    }

//    private static String byteToHexString(byte b) {
//        int n = b;
//        if (n < 0)
//            n += 256;
//        int d1 = n / 16;
//        int d2 = n % 16;
//        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
//    }

    public static String MD5Encode(String origin) {
        return MD5Encode(origin, DEFAULT_CHART);
    }

    public static String MD5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance(MD5);
            if (charsetName == null || "".equals(charsetName))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }


    public static String MD5EncodeUpper(String s) {
        try {
            byte[] btInput = s.getBytes(StandardCharsets.UTF_8);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(MD5);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = HEX_DIGITS_UP[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS_UP[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
