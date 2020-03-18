package com.zyb.mini.mall.web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.constant.IdentifyState;
import com.zyb.mini.mall.constant.UserTypeConstant;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.UserMapper;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.entity.UserPro;
import com.zyb.mini.mall.pojo.param.identify.IdentifyParam;
import com.zyb.mini.mall.pojo.param.identify.ProBackParam;
import com.zyb.mini.mall.pojo.param.identify.ProReplyParam;
import com.zyb.mini.mall.service.maintain_identify.IdentifyService;
import com.zyb.mini.mall.service.user.UserProService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * tzw
 */
@Api(tags = {"鉴赏记录"})
@Validated
@RequestMapping("/zyb/identify")
@RestController
public class IdentifyController extends BaseController {

    private final IdentifyService identifyService;


    private final UserProService userProService;

    private final UserMapper userMapper;

    public IdentifyController(IdentifyService identifyService, UserProService userProService, UserMapper userMapper) {
        this.identifyService = identifyService;
        this.userProService = userProService;
        this.userMapper = userMapper;
    }

    @ApiOperation("鉴赏记录列表")
    @GetMapping("/index")
    R<Page<Identify>> index(@Validated IdentifyParam param) {
        User user = currentUser();
        param.setUserId(user.getId());
        return R.success(identifyService.selectPageByParam(param));
    }


    @ApiOperation("鉴赏记录列表")
    @GetMapping("/pro-index")
    R<Page<Identify>> proIndex(@Validated IdentifyParam param) {
        User user = currentUser();
        if (!user.getUserType().equals(UserTypeConstant.PRO_USER)) {
            return R.error(Status.NOT_PRO_USER);
        }
//        QueryWrapper<UserPro> qw = new QueryWrapper<UserPro>().eq("user_id", user.getId());
        param.setUserProId(user.getUserRecId());
        return R.success(identifyService.selectPageByParam(param));
    }

    @ApiOperation("鉴赏记录详情")
    @GetMapping("/getInfo")
    R getInfo(@NotNull(message = "主键不能为空") Long id) {
        return success(identifyService.getInfo(id));
    }


    @ApiOperation("鉴赏记录添加")
    @PostMapping("/add")
    R add(@Validated Identify param) {
        param.setId(null);
        param.setUserId(currentUser().getId());
        param.setCreatedTime(LocalDateTime.now());
        param.setIdentify(IdentifyState.CREATED);
        UserPro userPro = userProService.getById(param.getUserProId());
        if (userPro == null) {
            return R.error(Status.PRO_NOT_EXIST);
        }
        param.setAmount(userPro.getPrice());
        identifyService.add(param);
        return success(param);
    }


//    @ApiOperation("鉴赏记录修改")
//    @PostMapping("/update")
//    R update(@Validated identify param) {
//        if (param.getId() == null) {
//            return R.error(Status.PARAM_LOST);
//        }
//        UserPro userPro = userProService.getById(param.getUserProId());
//        if (userPro == null) {
//            return R.error(Status.PRO_NOT_EXIST);
//        }
//        return success(identifyService.updateById(param));
//    }

    @ApiOperation("专家鉴赏回复")
    @PostMapping("/pro-reply")
    R<?> reply(ProReplyParam param) {
        User user = currentUser();
        if (!user.getUserType().equals(UserTypeConstant.PRO_USER)) {
            return R.error(Status.NOT_PRO_USER);
        }
        Identify identify = identifyService.getById(param.getId());
        if (identify == null || !identify.getIdentify().equals(IdentifyState.COMMIT)) {
            return R.error(Status.ORDER_ERROR_STATE);
        }
        if (!identify.getUserProId().equals(user.getUserRecId())) {
            return R.error(Status.IDENTIFY_NOT_PRO);
        }
        synchronized (this) {
            identify.setProYearId(param.getProYearId());
            identify.setProReplyMsg(param.getProReplyMsg());
            identify.setProReplyTime(LocalDateTime.now());
            identify.setCheckState(param.getCheckState());
            identify.setIdentify(IdentifyState.COMPLETE);
            if (identifyService.updateById(identify)) {
                BigDecimal amount = identify.getAmount().multiply(CommonConstant.IDENTIFY_AMOUNT_PERCENTAGE);
                userMapper.updateAmountById(user.getId(), amount);
            }
        }
        return R.success();
    }


    @ApiOperation("专家鉴赏回退")
    @PostMapping("/pro-back")
    R<?> back(ProBackParam param) {
        User user = currentUser();
        if (!user.getUserType().equals(UserTypeConstant.PRO_USER)) {
            return R.error(Status.NOT_PRO_USER);
        }
        Identify identify = identifyService.getById(param.getId());
        if (identify == null || !identify.getIdentify().equals(IdentifyState.COMMIT)) {
            return R.error(Status.ORDER_ERROR_STATE);
        }
        if (!identify.getUserProId().equals(user.getUserRecId())) {
            return R.error(Status.IDENTIFY_NOT_PRO);
        }
        identify.setProBackMsg(param.getProBackMsg());
        identify.setProBackDetail(param.getProBackDetail());
        identify.setIdentify(IdentifyState.BACK);
        boolean isUpdate = identifyService.updateById(identify);
        return R.success(isUpdate);
    }
}
