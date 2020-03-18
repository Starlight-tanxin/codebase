package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.dto.BillboardOfStartDto;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.entity.UserPunch;
import com.zyb.mini.mall.pojo.vo.surname.SurnameDetailVo;
import com.zyb.mini.mall.pojo.vo.surname.SurnameTopVo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Repository
public interface UserPunchMapper extends BaseMapper<UserPunch> {


    List<SurnameTopVo> top();

    long thisSurnameTopOne(long surnameId);

    /**
     * 某姓累计打卡人数
     */
    Integer countPersonNumber(long surnameId);

    /**
     * 今日最早打卡的人
     */
    String earlyBirdUser(long surnameId);

    /**
     * 累计打卡第一的人
     */
    String topOneUser(long surnameId);

    /**
     * 家族打卡排行榜
     */
    List<SurnameDetailVo.Column> list(long surnameId);

    /**
     * 百家姓打卡总数排行
     * 打卡日榜，周榜，月榜
     */
    String billboardOfStart(LocalDateTime start);

    List<BillboardOfStartDto> billboardOfStartDetail(LocalDateTime start);
}
