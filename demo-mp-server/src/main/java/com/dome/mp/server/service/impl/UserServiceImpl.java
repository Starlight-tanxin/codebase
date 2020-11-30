package com.dome.mp.server.service.impl;

import com.dome.mp.server.pojo.entity.User;
import com.dome.mp.server.dao.UserMapper;
import com.dome.mp.server.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TanXin
 * @since 2020-10-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
