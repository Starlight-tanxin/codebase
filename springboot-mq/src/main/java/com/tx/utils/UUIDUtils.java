package com.tx.utils;

import java.util.UUID;

/**
 * describe:
 *
 * @author TanXin
 * @date 2020/7/13 10:22
 */
public class UUIDUtils {

    public static String generateUuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
