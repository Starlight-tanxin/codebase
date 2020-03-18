package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.config.OSSConfig;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.framework.component.OssComponent;
import com.zyb.mini.mall.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/3 星期日 20:56.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Validated
@Api(tags = "资源")
@RequestMapping("/zyb/resource")
@RestController
public class ResourceController extends BaseController {


    private final OssComponent oss;

    public ResourceController(OssComponent oss) {
        this.oss = oss;
    }

    @ApiOperation("文件上传")
    @PostMapping("/file-upload")
    public R fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        String url = oss.upload(file.getInputStream(), "png", OSSConfig.Dir.IMG);
        return success(url);
    }


}
