package com.zyb.mini.mall.web.template;

import com.zyb.mini.mall.service.msg.MsgServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/13 星期三 21:13.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */

@RequestMapping("/template/mng/msg")
@Controller
@Validated
public class MngMsgTemplate {

    private final MsgServiceImpl msgService;

    public MngMsgTemplate(MsgServiceImpl msgService) {
        this.msgService = msgService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list",msgService.getMsgSessionListBack());
        return "mng/msg-list";
    }

    @GetMapping("/{id}/detail")
    public String detail(Model model,@PathVariable("id") String id) {
        model.addAttribute("list",msgService.getMsgList(null,id));
        model.addAttribute("id",id);
        return "mng/msg-detail";
    }



    @ResponseBody
    @PostMapping("/confirmMsg/{sessionId}")
    public void confirmMsg(@PathVariable("sessionId")@NotBlank String sessionId,@NotBlank String msg){

        msgService.sendMsgSessionBack(sessionId,msg);
    }
}
