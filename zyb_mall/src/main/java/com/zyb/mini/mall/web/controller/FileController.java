package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * describe:
 *
 * @author: TanXin
 * @date: 2020/4/1 16:47
 */
@Slf4j
@Api(tags = {"测试"})
@RequestMapping("/zyb/flie/")
@RestController
public class FileController {

    @PostMapping("upload")
    public R upload(@RequestParam("data") MultipartFile file,@RequestParam("json") String json) {
        if (!file.isEmpty()) {
            try {
                file.transferTo(new File("D:/test.jpg"));
                log.info(json);
                return R.success();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return R.paramLost("错误");
    }
}
