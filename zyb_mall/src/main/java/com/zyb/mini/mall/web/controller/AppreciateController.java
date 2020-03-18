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
 * @author Created by 谭健 on 2019/10/27 星期日 11:03.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Api(tags = {"鉴赏"})
@RequestMapping("/zyb/appreciate")
@RestController
public class AppreciateController extends BaseController {


    // TODO 参数
    @ApiOperation("新增鉴赏")
    @PostMapping("/add")
    R add() {
        return success("");
    }

    @ApiOperation("退回鉴赏")
    @PostMapping("/{appreciateId}/return")
    R _return(@PathVariable("appreciateId") long appreciateId) {
        return success("");
    }

    @ApiOperation("某个鉴赏记录详情")
    @GetMapping("/{appreciateId}/detail")
    R detail(@PathVariable("appreciateId") long appreciateId) {
        return success("");
    }

    @ApiOperation("专家回复某个鉴赏 ")
    @PostMapping("/{appreciateId}/{expertId}/reply")
    R reply(@PathVariable("appreciateId") long appreciateId, @PathVariable("expertId") long expertId) {
        return success("");
    }
}
