package com.dome.mp.server.utils;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/3/27 9:55
 */
public class PropertiesUtils {

    private static Properties prop = new Properties();

    private static ResourceBundle rb = ResourceBundle.getBundle("config", Locale.getDefault());

    static {
        try {
            prop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 根据Name获取Property
     *
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        return prop.getProperty(name) == null ? rb.getString(name) : prop.getProperty(name);
    }
}
