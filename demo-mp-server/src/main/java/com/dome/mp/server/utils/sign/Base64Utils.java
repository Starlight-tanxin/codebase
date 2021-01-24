package com.dome.mp.server.utils.sign;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>base64工具类</p>
 *
 * @author TanXin
 * @date 2020/4/8 11:46
 */
public final class Base64Utils {

    private Base64Utils() {
    }

    private static final String BASE64_IMG_HEAD = "data:image";
    private static final String BASE64_IMG_MK = ",";

    /**
     * <p>本地图片转换Base64的方法</p>
     *
     * @param imgPath 图片所在地址
     * @return java.lang.String base64编码图片
     */
    public static String imageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try (InputStream in = new FileInputStream(imgPath);) {
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encodeBase64String(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * <p>Base64编码.</p>
     *
     * @param input: 需要编码的字节数组
     * @return java.lang.String base64编码后的字符串
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * <p>Base64编码.</p>
     *
     * @param input: 需要编码的字符串
     * @return java.lang.String base64编码后的字符串
     */
    public static String encodeBase64(String input) {
        return new String(Base64.encodeBase64(input.getBytes(StandardCharsets.UTF_8)));

    }

    /**
     * <p>Base64解码.</p>
     *
     * @param input: 需要解码的字符串
     * @return byte[] 解码后的字节数组
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * <p>Base64解码.</p>
     *
     * @param input: 需要解码的字符串
     * @return java.lang.String 解码后的字符串
     */
    public static String decodeBase64String(String input) {
        return new String(Base64.decodeBase64(input.getBytes()), StandardCharsets.UTF_8);

    }

    /**
     * <p>将base64编码字符串转换为图片<p>
     * <p>data:image/jpg;base64,<p>
     *
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return boolean true-成功
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        createFile(path);
        // 如果 排头存在 data:image 字样则去除
        if (imgStr.startsWith(BASE64_IMG_HEAD)) {
            int i = imgStr.indexOf(BASE64_IMG_MK);
            if (i > -1) {
                imgStr = imgStr.substring(i + 1);
            }
        }
        try (OutputStream out = new FileOutputStream(path)) {
            // Base64解码
            byte[] bytes = Base64.decodeBase64(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out.write(bytes);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * <p>创建文件<p>
     *
     * @param destFileName: 完整文件名
     * @return boolean true-成功
     */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            // System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            //   System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        if (!file.getParentFile().exists()) {
            //  System.out.println("目标文件所在目录不存在，准备创建它！");
            if (!file.getParentFile().mkdirs()) {
                //      System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        try {
            if (file.createNewFile()) {
                //  System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                //   System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //    System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }
}
