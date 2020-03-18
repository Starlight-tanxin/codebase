package com.zyb.mini.mall.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 没打成jar可以用
 * 打成jar会读不到文件
 *
 * @author tanxin
 * @date 2019/5/22
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SpringReadFileUtils {


    /**
     * 获取按条导出排名得模板
     *
     * @param path 相对路径
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String path) throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:" + path);
    }

    /**
     * 获取按条导出排名得模板
     *
     * @param path 绝对路径
     * @return
     * @throws FileNotFoundException
     */
    public static File getFileByAbsolute(String path) throws FileNotFoundException {
        return ResourceUtils.getFile(path);
    }
}
