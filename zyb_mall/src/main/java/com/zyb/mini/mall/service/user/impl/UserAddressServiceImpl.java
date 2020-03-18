package com.zyb.mini.mall.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.dao.UserAddressMapper;
import com.zyb.mini.mall.pojo.entity.UserAddress;
import com.zyb.mini.mall.service.user.UserAddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;

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
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {


    private final Executor executor;

    private final UserAddressMapper userAddressMapper;

    public UserAddressServiceImpl(Executor executor, UserAddressMapper userAddressMapper) {
        this.executor = executor;
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    public void addOne(UserAddress address) {
        if (address.getIsDefault()) {
            List<UserAddress> list = list(new QueryWrapper<UserAddress>().lambda().eq(UserAddress::getUserId, address.getUserId()));
            list.forEach(o -> {
                if (o.getIsDefault()) {
                    o.setIsDefault(false);
                    updateById(o);
                }
            });
        }
        save(address);
    }


    @Override
    public List<UserAddress> selectListById(Long userId) {
        return userAddressMapper.selectUserAddressByUserId(userId);
    }
}
