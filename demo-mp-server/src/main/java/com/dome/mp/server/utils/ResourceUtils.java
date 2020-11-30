package com.dome.mp.server.utils;

import java.io.InputStream;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/10 15:25
 */
public class ResourceUtils {

    /**
     * <p>获取jar包内资源文件<p>
     *
     * @param path: 文件地址
     * @throws
     * @author TanXin
     * @date 2020/11/10 15:34
     * @return: java.io.InputStream
     */
    public static InputStream getResourceAsStream(String path) {
        return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    }
}
