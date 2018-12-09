package com.tx.dao;

import com.tx.pojo.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {

    @Select("SELECT * FROM `admin` WHERE admin_id = #{adminId}")
    Admin selectById(@Param("adminId") Integer adminId);
}
