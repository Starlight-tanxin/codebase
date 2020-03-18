package com.zyb.mini.mall.utils;

import com.zyb.mini.mall.constant.DateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Created by 谭健 on 2018/9/29 0029. 星期六. 13:57.
 * © All Rights Reserved.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUID {


    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }


    /**
     * 产生随机的验证码数字
     */
    public static int randomNumber() {
        // 创建一个线程安全并且效率更高的 随机数种子
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return current.nextInt(8999) + 1000;
    }


    /**
     * 产生微信提现单号
     */
    public static String absolutelyUniqueStrNumId() {

        String prefix = Deviation.secureRandom(2, Deviation.SecretType.SECRET_KEY_WITHOUT_NUM);
        return prefix + absolutelyUniqueNumber();
    }


    /**
     * 生成一个随机的字符序列
     * 这个码比较短 是18位的，区分大小写
     */
    public static String absolutelyUniqueId() {
        long timeMillis = System.currentTimeMillis();
        String timeNow = Deviation.encode(timeMillis);
        String prefix = Deviation.secureRandom(2, Deviation.SecretType.SECRET);
        String finalValue = Deviation.secureRandom(9, Deviation.SecretType.SECRET);
        return prefix + timeNow + finalValue;
    }


    /**
     * 生成一个长度是18位的唯一码
     */
    public static String absolutelyUniqueNumber() {
        LocalDateTime now = LocalDateTime.now();
        int nowYear = now.getYear();
        int monthValue = now.getMonthValue();
        int nowDayOfMonth = now.getDayOfMonth();
        // 6位长度
        String prefix = Deviation.encode(nowYear) + (monthValue > 9 ? monthValue : "0" + monthValue) + (nowDayOfMonth > 9 ? nowDayOfMonth : "0" + nowDayOfMonth);
        String suffix = Deviation.secureRandom(12, Deviation.SecretType.NUMBER_ONLY);
        return prefix + suffix;
    }

    /**
     * .
     */
    @Deprecated
    public static String smallUUID() {
        char[] uuid = randomUUID().toCharArray();
        BigDecimal uuidValue = BigDecimal.ZERO;
        for (int i = 0; i < uuid.length; i++) {
            int now = uuid[i];
            uuidValue = uuidValue.add(
                    new BigDecimal(now).multiply(new BigDecimal(36).pow(uuid.length - i - 1))
            );
        }
        return Deviation.encode(uuidValue);
    }


    public static class Deviation {

        public enum SecretType {
            /**
             *
             */
            SECRET, NUMBER_ONLY, BASE_64, SECRET_KEY_WITHOUT_NUM
        }

        /**
         * 通过最大值和最小值限制，可以保证输出的号码一定是5位长度的
         */
        private static final long MAX_VALUE = 10_7374_1824;
        private static final long MIN_VALUE = 1677_7216;


        /**
         * 一个可调整的密钥，
         * 不同的位置可以构建不同的解密串
         */
        private static final char[] SECRET_KEY = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a',
                's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2',
                '7', '3', '6', '4', '0', '9', '1', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A',
                'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '_', '-'};

        private static final char[] SECRET_KEY_WITHOUT_NUM = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a',
                's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm',
                '1', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A',
                'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};

        /**
         * BASE_64 算法序列
         */
        private static final char[] BASE_64 = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };


        private static final int[] NUMBER_ONLY = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};


        /**
         * 通过加密随机算法来生成高强度的随机序列
         *
         * @param length 生成的长度
         */
        public static String secureRandom(int length, SecretType secretType) {
            StringBuilder builder = new StringBuilder();
            // 使用加密随机数
            ThreadLocalRandom random = ThreadLocalRandom.current();
            // 生成种子
            byte[] seed = SecureRandom.getSeed(16);
            for (int i = 0; i < length; i++) {

                switch (secretType) {
                    case SECRET:
                        builder.append(SECRET_KEY[random.nextInt(SECRET_KEY.length - 1)]);
                        break;
                    case NUMBER_ONLY:
                        builder.append(NUMBER_ONLY[random.nextInt(NUMBER_ONLY.length - 1)]);
                        break;
                    case BASE_64:
                        builder.append(BASE_64[random.nextInt(BASE_64.length - 1)]);
                        break;
                    case SECRET_KEY_WITHOUT_NUM:
                        builder.append(SECRET_KEY_WITHOUT_NUM[random.nextInt(SECRET_KEY_WITHOUT_NUM.length - 1)]);
                        break;
                    default:
                        break;
                }
            }
            return builder.toString();
        }


        /**
         * 当数据范围超大时，使用 BigDecimal 来传递
         */
        public static String encode(BigDecimal number) {
            Stack<Character> stack = new Stack<>();
            StringBuilder result = new StringBuilder(0);
            while (number.subtract(BigDecimal.ONE).compareTo(BigDecimal.ZERO) > 0) {
                stack.add(SECRET_KEY[number.divideAndRemainder(new BigDecimal(64))[1].intValue()]);
                number = number.divide(new BigDecimal(64), RoundingMode.HALF_UP);
            }
            for (; !stack.isEmpty(); ) {
                result.append(stack.pop());
            }
            return result.toString();
        }


        /**
         * 同  encode
         *
         * @see UUID {@link #encode(BigDecimal)}
         */
        public static String encode(long number) {
            double rest = number;
            Stack<Character> stack = new Stack<Character>();
            StringBuilder result = new StringBuilder(0);
            while (rest >= 1) {
                stack.add(SECRET_KEY[(int) rest % 64]);
                rest = rest / 64;
            }
            for (; !stack.isEmpty(); ) {
                result.append(stack.pop());
            }
            return result.toString();

        }


        /**
         * 同  decode2BigDecimal
         *
         * @see UUID {@link #decode2BigDecimal(String)}
         */
        public static double decode(String str) {
            int multiple = 1;
            double result = 0;
            Character c;
            for (int i = 0; i < str.length(); i++) {
                c = str.charAt(str.length() - i - 1);
                result += decodeChar(c) * multiple;
                multiple = multiple * 64;
            }
            return result;
        }


        /**
         * 翻转一个字符串到一个数字
         */
        public static BigDecimal decode2BigDecimal(String str) {
            BigDecimal multiple = BigDecimal.ONE;
            BigDecimal result = BigDecimal.ZERO;
            Character c;
            for (int i = 0; i < str.length(); i++) {
                c = str.charAt(str.length() - i - 1);
                result = result.add(new BigDecimal(decodeChar(c)).multiply(multiple));
                multiple = multiple.multiply(new BigDecimal(64));
            }
            return result;
        }


        private static int decodeChar(Character c) {
            for (int i = 0; i < SECRET_KEY.length; i++) {
                if (c == SECRET_KEY[i]) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10000000; i++) {
            //String orderNo = absolutelyUniqueStrNumId();
            String orderNo = LocalDateTime.now().format(DateFormatter.DATE_TIME_ORDER) + CodeUtils.createRandomCharData(16);
            System.out.println(orderNo);
            set.add(orderNo);
        }
        System.out.println("Set 长度 ： " + set.size());
    }
}
