package com.zyb.mini.mall.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zyb.mini.mall.constant.DateType;
import com.zyb.mini.mall.dao.SurnameMapper;
import com.zyb.mini.mall.dao.UserPunchMapper;
import com.zyb.mini.mall.pojo.dto.BillboardOfStartDto;
import com.zyb.mini.mall.pojo.entity.Surname;
import com.zyb.mini.mall.pojo.entity.UserPunch;
import com.zyb.mini.mall.pojo.vo.BillboardDetailVo;
import com.zyb.mini.mall.pojo.vo.surname.SurnameDetailVo;
import com.zyb.mini.mall.pojo.vo.surname.SurnameTopVo;
import com.zyb.mini.mall.service.user.UserPunchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 22:43.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Service
public class UserPunchServiceImpl extends ServiceImpl<UserPunchMapper, UserPunch> implements UserPunchService {


    private final UserPunchMapper userPunchMapper;
    private final SurnameMapper surnameMapper;

    public UserPunchServiceImpl(UserPunchMapper userPunchMapper, SurnameMapper surnameMapper) {
        this.userPunchMapper = userPunchMapper;
        this.surnameMapper = surnameMapper;
    }

    @Override
    public List<SurnameTopVo> top() {
        // TODO 还有bug
        return userPunchMapper.top();
    }

    @Override
    public SurnameDetailVo findBySurnameId(long surnameId) {
        SurnameDetailVo vo = new SurnameDetailVo();

        Surname surname = surnameMapper.selectById(surnameId);
        Integer countPersonNumber = userPunchMapper.countPersonNumber(surnameId);
        String user = userPunchMapper.earlyBirdUser(surnameId);
        String topOneUser = userPunchMapper.topOneUser(surnameId);
        List<SurnameDetailVo.Column> list = userPunchMapper.list(surnameId);

        vo.setSurnameName(surname.getName());
        vo.setCount(countPersonNumber);
        vo.setEarlyBird(user);
        vo.setTopOne(topOneUser);
        vo.setColumns(list);

        return vo;
    }

    @Override
    public String billboard(String dateType) {
        LocalDateTime start;
        LocalTime of = LocalTime.of(0, 0, 0);

        String defaultName = surnameMapper.findFirst().getName();
        switch (dateType) {
            case DateType.DAY:
                start = LocalDateTime.of(LocalDate.now(), of);
                break;
            case DateType.WEEK:
                start = LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), of);
                break;
            case DateType.MONTH:
                start = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), of);
                break;
            default:
                return defaultName;
        }
        String billboard = userPunchMapper.billboardOfStart(start);
        return StringUtils.isNotBlank(billboard) ? billboard : defaultName;
    }

    @Override
    public List<BillboardDetailVo> billboardDetail(String dateType) {

        List<BillboardDetailVo> list = Lists.newArrayList();
        LocalDateTime start;
        LocalTime of = LocalTime.of(0, 0, 0);
        int dayNumber;

        switch (dateType) {
            case DateType.DAY:
                start = LocalDateTime.of(LocalDate.now(), of);
                dayNumber = 1;
                break;
            case DateType.WEEK:
                start = LocalDateTime.of(LocalDate.now().with(DayOfWeek.MONDAY), of);
                dayNumber = 7;
                break;
            case DateType.MONTH:
                start = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), of);
                dayNumber = 30;
                break;
            default:
                return list;
        }
        List<BillboardOfStartDto> billboardOfStartDtos = userPunchMapper.billboardOfStartDetail(start);

        final int finalNumber = dayNumber;

        billboardOfStartDtos.forEach(billboardOfStartDto -> {
            BillboardDetailVo v = new BillboardDetailVo();
            BeanUtils.copyProperties(billboardOfStartDto, v);
            v.setAvg(v.getCount() / finalNumber);
            list.add(v);
        });

        return list;
    }


    @Override
    public Surname getUserSurname(long userId) {
        LambdaQueryWrapper<UserPunch> last = new QueryWrapper<UserPunch>().lambda().eq(UserPunch::getUserId, userId).last(" limit 1");
        Optional<Long> optional = Optional.ofNullable(getOne(last)).map(UserPunch::getSurnameId);
        if (optional.isPresent()) {
            return surnameMapper.selectById(optional.get());
        }
        return null;
    }

    @Override
    public boolean isTopOne(long userId) {
        Surname surname = getUserSurname(userId);
        Integer integer = Optional.ofNullable(surname).map(Surname::getId).orElse(0);
        if (!integer.equals(0)){
            Optional<Long> one = Optional.ofNullable(userPunchMapper.thisSurnameTopOne(integer));
            return one.orElse(0L).equals(userId);
        }
        return false;
    }
}
