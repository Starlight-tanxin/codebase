package com.zyb.mini.mall.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Tx
 * @date: 2019/11/10
 */
public class CodeUtils {

    /**
     * 创建固定长度的数字字母字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String createRandomCharData(int length) {
        StringBuilder sb = new StringBuilder();
        Random randomSelect = ThreadLocalRandom.current();
        Random random = ThreadLocalRandom.current();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = randomSelect.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = random.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = random.nextInt(26) + 65;//保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = random.nextInt(26) + 97;//保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 生产固定长度的随机数字字符串
     *
     * @param length 长度
     * @return 数字字符串
     */
    public static String createRandomNumberByLength(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt(10));
        }
    }
}
