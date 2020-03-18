package com.zyb.mini.mall.service.user;

import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.param.LoginParam;
import com.zyb.mini.mall.pojo.vo.mine.MineVo;
import com.zyb.mini.mall.pojo.vo.mine.UserVo;
import com.zyb.mini.mall.service.BaseService;

/**
 * @author Created by 谭健 on 2019/10/29. 星期二. 17:44.
 * © All Rights Reserved.
 */
public interface UserService extends BaseService<User> {


    User registerOrGet(LoginParam loginDto);

}
