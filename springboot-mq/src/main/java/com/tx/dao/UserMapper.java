package com.tx.dao;

import com.tx.pojo.entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}