package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.UserPro;
import com.zyb.mini.mall.pojo.param.user.UserProParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 专家表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface UserProMapper extends BaseMapper<UserPro> {

    @Select("SELECT t.*, ty.type_name AS typeName FROM tb_user_pro t LEFT JOIN tb_antique_type ty ON ty.id = t.pro_type_id WHERE t.id = #{id}")
    UserPro selectOneByKey(@Param("id") Long id);

    List<UserPro> selectPageByParam(Page<UserPro> page,@Param("param") UserProParam param);
}
