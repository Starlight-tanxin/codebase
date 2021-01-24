package com.dome.mp.server.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Tx
 * @date: 2019/11/10
 */
public final class RandomStringUtils {

    private RandomStringUtils() {
    }

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
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset of = ZoneOffset.of("+8");
        long l = now.toEpochSecond(of);
        System.out.println(l);


        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now());
        System.out.println(Instant.now().getEpochSecond());
        System.out.println(Instant.now());
        System.out.println(new Date().toInstant());

        System.out.println("-------------------------------------");

        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).toEpochSecond());
        System.out.println(LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).toInstant().getEpochSecond());
        System.out.println(LocalDateTime.now().atOffset(OffsetDateTime.now().getOffset()).toInstant().toEpochMilli());

        System.out.println("========================================");

        Instant instant = Instant.now();
        System.out.println(new Date(instant.getEpochSecond() * 1000));
        System.out.println(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));


        DateTimeFormatter DATE_TIME_ORDER_2 = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        System.out.println(LocalDateTime.now().format(DATE_TIME_ORDER_2));
    }

}
