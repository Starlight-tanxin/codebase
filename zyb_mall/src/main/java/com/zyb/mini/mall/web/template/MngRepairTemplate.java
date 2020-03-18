package com.zyb.mini.mall.web.template;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.zyb.mini.mall.config.OSSConfig;
import com.zyb.mini.mall.framework.component.OssComponent;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;
import com.zyb.mini.mall.pojo.param.identify.MaintainMessageParam;
import com.zyb.mini.mall.pojo.param.identify.MaintainMessageTwoParam;
import com.zyb.mini.mall.pojo.param.background.ByIdParam;
import com.zyb.mini.mall.pojo.vo.Order.MinttainOrderListVo;
import com.zyb.mini.mall.service.background.BackgroundService;
import com.zyb.mini.mall.service.maintain_identify.MaintainCompanyImgService;
import com.zyb.mini.mall.service.maintain_identify.MaintainImgService;
import com.zyb.mini.mall.service.maintain_identify.MaintainMsgService;
import com.zyb.mini.mall.service.maintain_identify.MaintainService;
import com.zyb.mini.mall.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by 谭健 on 2019/11/12. 星期二. 10:46.
 * © All Rights Reserved.
 */
@RequestMapping("/template/mng/repair")
@Controller
@Validated
public class MngRepairTemplate {

    private final BackgroundService backgroundService;
    private final OssComponent oss;
    private final MaintainService maintainService;

    public MngRepairTemplate(BackgroundService backgroundService, OssComponent oss,
                             MaintainService maintainService) {
        this.backgroundService = backgroundService;
        this.oss = oss;
        this.maintainService = maintainService;
    }

    @GetMapping
    public String repair(Model model) {
        MaintainListParam param = new MaintainListParam();
        param.setPage(1);
        param.setPageSize(Integer.MAX_VALUE);
        List<MinttainOrderListVo> list = backgroundService.queryMaintainOrder(param).getBody();
        model.addAttribute("list", list);
        return "mng/repair";
    }


    @GetMapping("/repair-one-msg/{id}")
    public String repairOneMsg(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        return "mng/repair-one-msg";
    }


    // 预回复
    @PostMapping("/addPlatformMessage")
    public String addPlatformMessage(MaintainMessageParam param, List<MultipartFile> files) throws IOException {

        param.setMaintainCompanyImgs(Lists.newArrayList());

        for (MultipartFile multipartFile : files) {
            if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                MaintainCompanyImg img = new MaintainCompanyImg();

                String ossImg = oss.upload(multipartFile.getInputStream(), FileUtils.getFileSuffix(multipartFile.getOriginalFilename()), OSSConfig.Dir.IMG);

                img.setImgName(ossImg.substring(ossImg.lastIndexOf("/")));
                img.setImgUrl(ossImg);

                param.getMaintainCompanyImgs().add(img);
            }
        }

        backgroundService.AddPlatformMessage(param);

        return "redirect:/template/mng/repair";
    }


    @GetMapping("/repair-two-msg/{id}")
    public String repairTwoMsg(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        return "mng/repair-two-msg";
    }

    //填入时间
    @GetMapping("/repair-update-arriveTime/{id}")
    public String updateArriveTime(Model model, @PathVariable("id") long id) {
        backgroundService.AddReachTime(new ByIdParam().setId(id));
        return "redirect:/template/mng/repair";
    }

    //修复的订单详情
    @GetMapping("/repair-Info-maintainInfo/{id}")
    public String maintainInfo(Model model, @PathVariable("id") long id) {
        Maintain info = maintainService.getInfo(id);
        model.addAttribute("info", info);
        model.addAttribute("stringList", info.getUserMaintainImgs());
        model.addAttribute("listImg", info.getPreMaintainMsgImgs());
        model.addAttribute("ListImgTwo", info.getMaintainMsgImgs());
        return "mng/mintorder-detailmint";
    }


    // 二次回复
    @PostMapping("/addMaintainMsg")
    public String addMaintainMsg(MaintainMessageTwoParam param, List<MultipartFile> files) throws IOException {

        param.setMaintainCompanyImgs(Lists.newArrayList());

        for (MultipartFile multipartFile : files) {
            if (StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
                MaintainCompanyImg img = new MaintainCompanyImg();

                String ossImg = oss.upload(multipartFile.getInputStream(), FileUtils.getFileSuffix(multipartFile.getOriginalFilename()), OSSConfig.Dir.IMG);

                img.setImgName(ossImg.substring(ossImg.lastIndexOf("/")));
                img.setImgUrl(ossImg);

                param.getMaintainCompanyImgs().add(img);
            }
        }

        backgroundService.AddMaintainMsg(param);

        return "redirect:/template/mng/repair";
    }

}
