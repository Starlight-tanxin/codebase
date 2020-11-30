package com.dome.mp.server.utils.hex;

/**
 * describe: 16 进制工具
 *
 * @author TanXin
 * @date 2020/6/15 10:39
 */
public class HexUtils {

    private static final char[] HEX_16 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 字节数组转16进制字符串
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    public static String bytes2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_16[b[i] >> 4 & 0xf])
                    .append(HEX_16[b[i] & 0xf]);
        }
        return sb.toString();
    }

    /**
     * 把4个字节表示的16进制数转换成32位浮点数
     * 采用IEEE 754标准
     *
     * @param b 16进制字节数组
     * @return 浮点值
     */
    public static float byte2Float(byte b[]) {
        int bits = b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16
                | (b[0] & 0xff) << 24;
        int sign = ((bits & 0x80000000) == 0) ? 1 : -1;
        int exponent = ((bits & 0x7f800000) >> 23);
        int mantissa = (bits & 0x007fffff);
        mantissa |= 0x00800000;
        float f = (float) (sign * mantissa * Math.pow(2, exponent - 150));
        return f;
    }


    /**
     * 单灯控制器奇偶校验
     *
     * @param data             指令
     * @param start            从指令第多少位开始计算
     * @param ignoreLastLength 忽略指令的最后几位数据
     * @return 校验后的值
     */
    public static int parityCheck(byte[] data, int start, int ignoreLastLength) {
        int l = data.length;
        if (l <= ignoreLastLength || start < 0 || start >= l) {
            throw new RuntimeException("错误的指令");
        }
        l = l - ignoreLastLength;
        int parity = 0;
        for (int i = start; i < l; i++) {
            parity = parity + data[i];
        }
        parity = 0 - parity;
        return parity;
    }

    public static void main(String[] args) {
        //423C000041400000418000000000000000000000409000003DCCCCCD41EB803F4285476844780D71
        byte[] test = new byte[]{0x42, 0x3c, 0x00, 0x00};
        System.out.println(byte2Float(test));
        byte[] test1 = new byte[]{0x41, 0x40, 0x00, 0x00};
        System.out.println(byte2Float(test1));
        byte[] test2 = new byte[]{0x41, (byte) 0x80, 0x00, 0x00};
        System.out.println(byte2Float(test2));
        byte[] test3 = new byte[]{0x00, 0x00, 0x00, 0x00};
        System.out.println(byte2Float(test3));
    }
}
