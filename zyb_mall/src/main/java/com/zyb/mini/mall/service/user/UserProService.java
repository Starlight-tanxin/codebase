package com.zyb.mini.mall.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.entity.UserPro;
import com.zyb.mini.mall.pojo.param.user.UserProParam;


public interface UserProService extends IService<UserPro> {
    UserPro getInfo(Long id);

    Page<UserPro> selectPageByParam(UserProParam param);
}
