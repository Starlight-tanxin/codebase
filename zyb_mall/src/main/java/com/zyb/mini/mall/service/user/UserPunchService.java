package com.zyb.mini.mall.service.user;

import com.zyb.mini.mall.pojo.entity.Surname;
import com.zyb.mini.mall.pojo.entity.UserPunch;
import com.zyb.mini.mall.pojo.vo.BillboardDetailVo;
import com.zyb.mini.mall.pojo.vo.surname.SurnameDetailVo;
import com.zyb.mini.mall.pojo.vo.surname.SurnameTopVo;
import com.zyb.mini.mall.service.BaseService;

import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 22:42.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 * <p>
 * 打卡服务
 */
public interface UserPunchService extends BaseService<UserPunch> {

    /**
     * 百家姓排行
     */
    List<SurnameTopVo> top();


    /**
     * 某姓打卡详情
     */
    SurnameDetailVo findBySurnameId(long surnameId);

    /**
     * 百家姓打卡总数排行
     * 打卡日榜，周榜，月榜
     */
    String billboard(String dateType);

    /**
     * 百家姓打卡总数排行详情
     */
    List<BillboardDetailVo> billboardDetail(String dateType);


    /**
     * 获取用户打卡过的姓，如果没有给任何姓打过，返回null
     */
    Surname getUserSurname(long userId);

    boolean isTopOne(long userId);
 }
