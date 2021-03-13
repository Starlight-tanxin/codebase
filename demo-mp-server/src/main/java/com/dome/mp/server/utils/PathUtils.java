package com.dome.mp.server.utils;

import java.io.File;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2021/3/12 17:56
 */
public class PathUtils {

    public static String getFileDir() {
       // String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1) + "static/file";
        String path = "D:/static/file";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }
}
