package com.dome.mp.server.utils.sign;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/4/8 11:46
 */
public final class Base64Utils {

    private Base64Utils() {
    }

    private static final String BASE64_IMG_HEAD = "data:image";
    private static final String BASE64_IMG_MK = ",";

    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
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
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        return new String(Base64.encodeBase64(input.getBytes(StandardCharsets.UTF_8)));

    }

//	/**
//	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
//	 */
//	public static String encodeUrlSafeBase64(byte[] input) {
//		return Base64.encodeBase64URLSafe(input);
//	}

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base64解码.
     */
    public static String decodeBase64String(String input) {
        return new String(Base64.decodeBase64(input.getBytes()), StandardCharsets.UTF_8);

    }

    /**
     * data:image/jpg;base64,
     *
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
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
