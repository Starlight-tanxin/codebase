package com.zyb.mini.mall.web.controller.open;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.constant.IdentifyState;
import com.zyb.mini.mall.constant.MaintainState;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.param.identify.IdentifyParam;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;
import com.zyb.mini.mall.service.maintain_identify.IdentifyService;
import com.zyb.mini.mall.service.maintain_identify.MaintainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Tx
 * @date: 2019/11/21
 */
@Api(tags = {"开放接口：鉴赏和修复"})
@RequestMapping("/zyb/open/identify-maintain")
@RestController
@Validated
public class OpenMaintainController {

    private final MaintainService maintainService;
    private final IdentifyService identifyService;

    public OpenMaintainController(MaintainService maintainService, IdentifyService identifyService) {
        this.maintainService = maintainService;
        this.identifyService = identifyService;
    }


    @ApiOperation("鉴赏记录列表--全部的鉴赏记录--最终完成的")
    @PostMapping("/showAllIdentify")
    R<IPage<Identify>> showAllIdentify(@Validated IdentifyParam param) {
        param.setUserId(null);
        param.setState(IdentifyState.EVA);
        return R.success(identifyService.selectPageByParam(param));
    }

    @ApiOperation("修复记录列表--全部的修复记录--最终完成的")
    @PostMapping("/showAllMaintain")
    R<Page<Maintain>> showAllMaintain(@Validated MaintainListParam param) {
        param.setUserId(null);
        param.setMaintainState(MaintainState.EVA);
        Page<Maintain> page = maintainService.selectPageByParam(param);
        return R.success(page);
    }
}
