package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.UserAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 收获地址管理 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    @Select("SELECT\n" +
            "\ta.* ,\n" +
            "\tb.province,\n" +
            "\tc.city,\n" +
            "\td.region\n" +
            "FROM\n" +
            "\ttb_user_address a\n" +
            "\tLEFT JOIN tb_province b ON b.province_no = a.province_no\n" +
            "\tLEFT JOIN tb_city c ON c.city_no = a.city_no\n" +
            "\tLEFT JOIN tb_region d ON d.region_no = a.region_no WHERE a.user_id = #{userId}")
    List<UserAddress> selectUserAddressByUserId(@Param("userId") Long userId);

    @Select("SELECT\n" +
            "\ta.* ,\n" +
            "\tb.province,\n" +
            "\tc.city,\n" +
            "\td.region\n" +
            "FROM\n" +
            "\ttb_user_address a\n" +
            "\tLEFT JOIN tb_province b ON b.province_no = a.province_no\n" +
            "\tLEFT JOIN tb_city c ON c.city_no = a.city_no\n" +
            "\tLEFT JOIN tb_region d ON d.region_no = a.region_no WHERE a.user_id = #{userId} AND a.is_default = 1 LIMIT 1")
    UserAddress selectUserAddressByUserDefault(@Param("userId") Long userId);


    @Select("SELECT\n" +
            "\ta.* ,\n" +
            "\tb.province,\n" +
            "\tc.city,\n" +
            "\td.region\n" +
            "FROM\n" +
            "\ttb_user_address a\n" +
            "\tLEFT JOIN tb_province b ON b.province_no = a.province_no\n" +
            "\tLEFT JOIN tb_city c ON c.city_no = a.city_no\n" +
            "\tLEFT JOIN tb_region d ON d.region_no = a.region_no WHERE a.id = #{id}")
    UserAddress getUserAddressById(@Param("id") Long id);
}
