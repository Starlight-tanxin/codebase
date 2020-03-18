package com.zyb.mini.mall.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author tanxin
 * @date 2019/9/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommUtils {


    /**
     * 获取一个uuid
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static List<Map<String, Object>> mapList2StandardMapList(List<Map> list) {
        List<Map<String, Object>> standardMapList = new ArrayList<>();
        for (Map map : list) {
            Map<String, Object> standardMap = new HashMap<>();
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                standardMap.put(entry.getKey().toString(), entry.getValue());
            }
            standardMapList.add(standardMap);
        }
        return standardMapList;
    }

    /**
     * 元转分
     *
     * @param yuan 多少元
     * @return 多少分
     */
    public static int yuan2fen(double yuan) {
        double fen = yuan * 100;
        return new Double(fen).intValue();
    }


    public static void closeAll(InputStream is, OutputStream os) {
        close(is);
        close(os);
    }

    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(OutputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getUuid().length());
    }
}
