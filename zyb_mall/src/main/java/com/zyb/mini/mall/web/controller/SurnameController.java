package com.zyb.mini.mall.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.entity.UserPunch;
import com.zyb.mini.mall.pojo.vo.surname.SurnameDetailVo;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.user.UserPunchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 10:50.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */

@Api(tags = {"打卡"})
@RequestMapping("/zyb/surname")
@RestController
@Validated
public class SurnameController extends BaseController {


    private final UserPunchService userPunchService;
    private final StringRedisTemplate stringRedisTemplate;

    public SurnameController(UserPunchService userPunchService, StringRedisTemplate stringRedisTemplate) {
        this.userPunchService = userPunchService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

//    @ApiImplicitParam(name = "surnameId", value = "姓氏id")
//    @ApiOperation("助力打卡")
//    @GetMapping("{surnameId}/help")
//    public R help(@NotNull @Min(1) long surnameId) {
//        return success("");
//    }

//    @ApiOperation("百家姓排行")
//    @GetMapping("/top")
//    public R top() {
//        return success(userPunchService.top());
//    }

    @ApiImplicitParam(name = "surnameId", value = "姓氏id")
    @ApiOperation("每日打卡")
    @PostMapping("/{surnameId}/punch")
    public R punch(@PathVariable("surnameId") @NotNull @Min(1) long surnameId) {

        User user = currentUser();
        String key = RedisKeyNameConstant.USER_PUNCH_DATA + user.getId();

        String data = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(data)) {
            return fail("今天已经打过卡了");
        }


        LambdaQueryWrapper<UserPunch> q = new QueryWrapper<UserPunch>().lambda().eq(UserPunch::getUserId, user.getId()).last(" limit 1");
        // 之前打过卡的id
        Long oldSurnameId = Optional.ofNullable(userPunchService.getOne(q)).map(UserPunch::getSurnameId).orElse(0L);
        if (!oldSurnameId.equals(surnameId) && !oldSurnameId.equals(0L)) {
            return fail("不能帮其他姓氏打卡");
        }

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        long last = localDateTime.toInstant(ZoneOffset.of("+8")).getEpochSecond();
        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).getEpochSecond();
        // 晚上12点过后缓存失效
        stringRedisTemplate.opsForValue().set(key, String.valueOf(surnameId), last - now, TimeUnit.SECONDS);

        UserPunch punch = new UserPunch();
        punch.setPunchCount(1L);
        punch.setSurnameId(surnameId);
        punch.setUserId(user.getId());
        userPunchService.save(punch);

        return success();
    }


    @ApiImplicitParam(name = "surnameId", value = "姓氏id")
    @ApiOperation("某姓打卡详情")
    @GetMapping("/{surnameId}/top")
    public R top(@PathVariable("surnameId") @NotNull @Min(1) long surnameId) {
        SurnameDetailVo vo = userPunchService.findBySurnameId(surnameId);
        User user = currentUser();
        String key = RedisKeyNameConstant.USER_PUNCH_DATA + user.getId();
        String data = stringRedisTemplate.opsForValue().get(key);
        vo.setHasPunch(StringUtils.isNotBlank(data));
        return success(vo);
    }


}
