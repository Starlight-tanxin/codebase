package com.zyb.mini.mall.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Created by 谭健 on 2018/11/30. 星期五. 10:40.
 * © All Rights Reserved.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OSSConfig {


    public static final String ACCESS_KEY_ID = "LTAI4FtqtEHwQLezPjggiFrY";

    public static final String ACCESS_KEY_SECRET = "YYOXmnMNfJgZ2TvaOGJ3tdBFbE0cJy";

    public static final String END_POINT = "oss-cn-zhangjiakou.aliyuncs.com";
    public static final String BUCKET = "hnsxyts";
    public static final String HOST = "https://" + BUCKET + "." + END_POINT;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Dir {
        public static final String IMG = "img/";
    }


}
