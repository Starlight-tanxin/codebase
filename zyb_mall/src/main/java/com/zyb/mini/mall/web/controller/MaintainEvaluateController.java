package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.constant.IdentifyState;
import com.zyb.mini.mall.constant.MaintainState;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.entity.MaintainEvaluate;
import com.zyb.mini.mall.service.maintain_identify.IdentifyService;
import com.zyb.mini.mall.service.maintain_identify.MaintainEvaluateService;
import com.zyb.mini.mall.service.maintain_identify.MaintainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * tzw
 */
@Slf4j
@Api(tags = {"评价"})
@Validated
@RequestMapping("/zyb/eva")
@RestController
public class MaintainEvaluateController extends BaseController {

    private final MaintainEvaluateService maintainEvaluateService;
    private final MaintainService maintainService;
    private final IdentifyService identifyService;

    public MaintainEvaluateController(MaintainEvaluateService maintainEvaluateService, MaintainService maintainService, IdentifyService identifyService) {
        this.maintainEvaluateService = maintainEvaluateService;
        this.maintainService = maintainService;
        this.identifyService = identifyService;
    }

    @ApiOperation("评价添加")
    @PostMapping("/add")
    R add(@Validated MaintainEvaluate param) {
        if (param.getBusinessType()) {
            // TODO 鉴赏 需要进行状态变更
            Identify identify = identifyService.getById(param.getMaintainId());
            if (identify == null) {
                return R.error(Status.DATA_NO_EXIST);
            }
            // 鉴赏完成 或者 退回鉴赏
            param.setCreatedTime(LocalDateTime.now());
            if (IdentifyState.COMPLETE == identify.getIdentify() || IdentifyState.BACK == identify.getIdentify()) {
                log.info("修改鉴赏单的状态 --- 用户评价");
                param.setUserId(currentUser().getId());
                maintainEvaluateService.save(param);
                identify.setIdentify(IdentifyState.EVA);
                identifyService.updateById(identify);
            } else {
                return R.error(Status.EVA_NOT);
            }
        } else {
            // TODO 修复 需要进行状态变更
            Maintain maintain = maintainService.getById(param.getMaintainId());
            if (maintain == null) {
                return R.error(Status.DATA_NO_EXIST);
            }
            // 尾款支付完成
            if (MaintainState.TAIL_PAYING == maintain.getMaintainState()) {
                log.info("修改修复单的状态 --- 用户评价");
                param.setUserId(currentUser().getId());
                maintainEvaluateService.save(param);
                maintain.setMaintainState(MaintainState.EVA);
                maintainService.updateById(maintain);
            } else {
                return R.error(Status.EVA_NOT);
            }

        }
        return success();
    }

}
