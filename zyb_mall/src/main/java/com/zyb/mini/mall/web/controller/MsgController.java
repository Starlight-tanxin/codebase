package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.dto.UserMsgDTO;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.service.msg.MsgServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:04.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Api(tags = {"消息"})
@RequestMapping("/zyb/msg")
@RestController
@Validated
public class MsgController extends BaseController {

    private final MsgServiceImpl msgService;

    public MsgController(MsgServiceImpl msgService) {
        this.msgService = msgService;
    }

    @ApiOperation("消息列表")
    @GetMapping("/list")
    R list() {
        User user = currentUser();
        List<Object> list = msgService.getMsgSessionList(user.getId());
        return success(list);
    }

    @ApiOperation("用户发起一个消息会话")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "消息", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/addMsgSession")
    R<List<UserMsgDTO>> addMsgSession(@NotNull String msg) {
        User user = currentUser();
        List<UserMsgDTO> msgList = msgService.addMsgSession(user, msg);
        return R.success(msgList);
    }

    @ApiOperation("用户发送一条消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionId", value = "会话id", example = Mock.NUMBER, paramType = "query", required = true),
            @ApiImplicitParam(name = "msg", value = "消息", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/sendMsg")
    R<List<UserMsgDTO>> sendMsg(@NotNull String sessionId, @NotNull String msg) {
        User user = currentUser();
        List<UserMsgDTO> msgList = msgService.sendMsgSession(user, sessionId, msg);
        return R.success(msgList);
    }


    @ApiOperation("提醒发货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/remindOrder")
    R<List<UserMsgDTO>> remindOrder(@NotNull String orderNo) {
        User user = currentUser();
        List<UserMsgDTO> msgList = msgService.addMsgSession(user, "客户等不及啦！请为订单：【" + orderNo + "】尽快发货!");
        return R.success(msgList);
    }


    @ApiOperation("获取消息的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sessionId", value = "会话id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/msgDetail")
    R<List<UserMsgDTO>> msgDetail(@NotNull String sessionId) {
        User user = currentUser();
        List<UserMsgDTO> msg = msgService.getMsgList(user.getId(), sessionId);
        return R.success(msg);
    }
}
