package com.zyb.mini.mall.dao;

import com.zyb.mini.mall.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


    User selectByOpenId(String openId);

    /**
     * 充值增加金额 线程安全型
     * @param userId 用户id
     * @param amount 金额
     * @return
     */
    @Update("UPDATE `tb_user` SET `actual_amount` = `actual_amount` + #{amount} WHERE `id` = #{userId}")
    Integer updateAmountById(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

    List<User>  queryUserName(List<Long> ids);
}
