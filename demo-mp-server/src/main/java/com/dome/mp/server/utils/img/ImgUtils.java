package com.dome.mp.server.utils.img;

import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/4/15 9:54
 */
public class ImgUtils {

    private static Logger log = LoggerFactory.getLogger(ImgUtils.class);

    /**
     * @param oldImg     旧图片文件
     * @param newImg     新图片文件
     * @param widthRatio 宽度。 会按照此宽度进行等比计算进行压缩
     * @return 新的图片
     */
    public static File compress(String oldImg, String newImg, int widthRatio) {
        File oldFile = new File(oldImg);
        File newFile = new File(newImg);
        if (!newFile.getParentFile().exists()) {
            if (!newFile.getParentFile().mkdirs()) {
                log.info("创建文件路径失败");
            }
        }
        BufferedImage bufImg = ImgUtil.read(oldFile);
        int width = bufImg.getWidth();
        int height = bufImg.getHeight();
        float w_f = (float) width / widthRatio;
        Img.from(oldFile).scale(widthRatio, (int) (height / w_f)).setQuality(1.0f).write(newFile);
        return newFile;
    }

    public static void main(String[] args) {
//        File file = new File("D:\\test\\P00415-100421.jpg");
//        BufferedImage img = ImgUtil.read(file);
//        int width = img.getWidth();
//        int height = img.getHeight();
//
//
//        System.out.println(String.format("width: %d; height : %d; size : %d kb", width, height, file.length() / 1024));
//        float w_f = (float) width / 1080;
//        System.out.println(w_f);
//        System.out.println(height / w_f);
//        Img.from(file).scale(1080, 1440).setQuality(1.0f).write(FileUtil.file("D:/test/ys_test.jpg"));

        compress("D:\\test\\P00415-100421.jpg", "D:/test/ys_test_1.jpg", 1080);

    }
}
