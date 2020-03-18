package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:05.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Api(tags = {"专家认证"})
@RequestMapping("/zyb/auth")
@RestController
public class AuthController extends BaseController {

    // TODO 参数
    @ApiOperation("申请专家认证")
    @PostMapping("/add")
    R add() {
        return success("");
    }

    @ApiOperation("申请的专家列表")
    @GetMapping("/list")
    R list() {
        return success("");
    }

    @ApiOperation("审核某个专家")
    @PostMapping("/{expertId}/examine")
    R examine(@PathVariable("expertId") long expertId) {
        return success("");
    }

    @ApiOperation("查询专家认证状态")
    @GetMapping("/{expertId}/check")
    R check(@PathVariable("expertId") long expertId) {
        return success("");
    }
}
