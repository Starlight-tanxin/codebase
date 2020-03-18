package com.zyb.mini.mall.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.constant.DateFormatter;
import com.zyb.mini.mall.constant.MaintainState;
import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.entity.MaintainPro;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;
import com.zyb.mini.mall.service.maintain_identify.MaintainProService;
import com.zyb.mini.mall.service.maintain_identify.MaintainService;
import com.zyb.mini.mall.utils.CodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 修复订单 前端控制器
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
@Api(tags = {"修复"})
@RestController
@Validated
@RequestMapping("/zyb/maintain")
public class MaintainController extends BaseController {
    private final MaintainService maintainService;
    private final MaintainProService maintainProService;

    public MaintainController(MaintainService maintainService, MaintainProService maintainProService) {
        this.maintainService = maintainService;
        this.maintainProService = maintainProService;
    }


    @ApiOperation("修复记录列表")
    @GetMapping("/index")
    R index(@Validated MaintainListParam param) {
        User user = currentUser();
        param.setUserId(user.getId());
        Page<Maintain> page = maintainService.selectPageByParam(param);
        return success(page);
    }


    @ApiOperation("修复记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "修复记录id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/getInfo")
    R getInfo(@NotNull(message = "主键不能为空") Long id) {
        return success(maintainService.getInfo(id));
    }


    @ApiOperation("修复记录添加")
    @PostMapping("/add")
    @Transactional
    R add(@Validated Maintain param) {
        LocalDateTime now = LocalDateTime.now();
        String antiqueNo = now.format(DateFormatter.DATE_TIME_ORDER) + CodeUtils.createRandomCharData(16);
        param.setId(null);
        if (param.getUserAddressId() == null) {
            param.setUserAddressId(0L);
        }
        param.setUserId(currentUser().getId());
        param.setCreatedTime(now);
        param.setMaintainState(MaintainState.CREATED);
        param.setAntiqueNo(antiqueNo);
        MaintainPro maintainPro = maintainProService.getById(param.getMaintainProId());
        if (maintainPro == null) {
            R.error(Status.PRO_NOT_EXIST);
        }
        maintainService.add(param);
        return success(param);
    }

}

