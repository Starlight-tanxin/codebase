package com.zyb.mini.mall.utils;

import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.exception.ApiException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;

/**
 * @author tanxin
 * @date 2019/5/22
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {



    public static final String XLS = ".xls";

    public static final String XLSX = ".xlsx";

    /**
     * 常见excel格式
     */
    public static final String[] EXCEL_SUFFIX = {XLS, XLSX, ".xlsm", ".xlsb", ".xlt", ".xltm", "xltx"};
    /**
     * 常见图片格式
     */
    public static final String[] IMG_SUFFIX = {".jpg", ".png", ".jpeg"};

    /**
     * 取得文件后缀
     *
     * @param filePath 文件路径（文件名）
     * @return
     */
    public static String getFileSuffix(String filePath) {
        String suffix = filePath.substring(filePath.lastIndexOf("."), filePath.length());
        return suffix;
    }


    /**
     * 获取一个新的文件名
     *
     * @param suffix 文件后缀
     * @return 新的文件名
     */
    public static String getNewFileName(String suffix) {
        String time = Long.toString(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().replace("-", "");
        StringBuffer sb = new StringBuffer();
        sb.append(uuid).append("_").append(time).append(suffix);
        return sb.toString();
    }

    /**
     * 检查是不是支持的格式
     *
     * @param suffixArray
     * @param suffix
     * @return true 满足  false 不满足
     */
    public static boolean checkSuffix(String[] suffixArray, String suffix) {
        for (String suf : suffixArray) {
            if (suf.equalsIgnoreCase(suffix.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建文件所在文件夹
     *
     * @param file 文件
     */
    public static void mKdirsByFile(File file) {
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new ApiException(Status.REQUEST_BAD, "创建文件夹失败");
            }
        }
    }

    /**
     * 创建文件所在文件夹
     *
     * @param filePath 文件路径 非文件夹路径
     */
    public static void mKdirsByPath(String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new ApiException(Status.REQUEST_BAD, "创建文件夹失败");
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param file 文件
     * @return true 成功 false 失败
     */
    public static boolean deleteFile(File file) {
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.info("删除单个文件 : {}  \t 成功！", file.getName());
                return true;
            } else {
                log.info("删除单个文件 : {}  \t 失败！", file.getName());
                return false;
            }
        } else {
            log.info("删除单个文件 : {}  \t 失败！文件不存在！", file.getName());
            return false;
        }
    }

    /**
     * 删除文件夹下得文件 和这个文件假
     *
     * @param file 文件夹
     * @return true 成功 false 失败
     */
    public static boolean deleteFileDir(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return deleteFile(file);
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteFile(f);
            }
            if (file.delete()) {
                log.info("删除文件夹 : {}  \t 成功！", file.getName());
                return true;
            } else {
                log.info("删除文件夹 : {}  \t 失败！", file.getName());
                return false;
            }
        }
    }


}
