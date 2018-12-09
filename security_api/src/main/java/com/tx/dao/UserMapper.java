package com.tx.dao;

import com.tx.pojo.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    @Select("SELECT * FROM `user`")
    List<User> selectAll();
}