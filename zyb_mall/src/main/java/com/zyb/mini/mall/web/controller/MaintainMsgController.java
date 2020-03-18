package com.zyb.mini.mall.web.controller;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.MaintainMsg;
import com.zyb.mini.mall.service.maintain_identify.MaintainMsgService;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 公司回复，回复 预回复都在这里
 * </p>
 *
 * @author tzw
 * @since 2019-11-03
 */
@RestController
@Validated
@RequestMapping("/zyb/maintainMsg")
public class MaintainMsgController extends BaseController {

    private final MaintainMsgService maintainMsgService;

    public MaintainMsgController(MaintainMsgService maintainMsgService) {
        this.maintainMsgService = maintainMsgService;
    }

    @ApiOperation("回复记录添加")
    @PostMapping("/add")
    @Transactional
    R add(@Validated MaintainMsg param) {
        return success(maintainMsgService.add(param));
    }
}
