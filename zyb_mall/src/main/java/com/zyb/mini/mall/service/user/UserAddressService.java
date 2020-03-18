package com.zyb.mini.mall.service.user;

import com.zyb.mini.mall.pojo.entity.UserAddress;
import com.zyb.mini.mall.service.BaseService;

import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 21:36.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
public interface UserAddressService extends BaseService<UserAddress> {


    /**
     * 新增一个收货地址
     */
    void addOne(UserAddress address);

    List<UserAddress> selectListById(Long userId);
}
