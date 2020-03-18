package com.zyb.mini.mall.framework.component;

import com.aliyun.oss.OSSClient;
import com.zyb.mini.mall.config.OSSConfig;
import com.zyb.mini.mall.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author Created by 谭健 on 2018/12/3. 星期一. 16:32.
 * © All Rights Reserved.
 * <p>
 * oss 组件
 */
@Component
@Slf4j
public class OssComponent {


    /**
     * 文件上传
     *
     * @param inputStream 输入流
     * @param dir         see
     * @param suffix      文件后缀
     * @see OSSConfig.Dir
     */
    public String upload(InputStream inputStream, String suffix, String dir) {
        if (inputStream == null) {
            log.error("通过OSS 简单上传时，输入流为空");
            return "";
        }
        String filename = dir + UUID.randomUUID() + "." + suffix;
        OSSClient client = new OSSClient(OSSConfig.END_POINT, OSSConfig.ACCESS_KEY_ID, OSSConfig.ACCESS_KEY_SECRET);
        client.putObject(OSSConfig.BUCKET, filename, inputStream);
        client.shutdown();
        return OSSConfig.HOST + "/" + filename;

    }

}
