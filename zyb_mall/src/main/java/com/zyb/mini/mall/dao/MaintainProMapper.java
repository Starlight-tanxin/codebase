package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.MaintainPro;
import com.zyb.mini.mall.pojo.param.user.UserProParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 修复得专家表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface MaintainProMapper extends BaseMapper<MaintainPro> {

    @Select("SELECT t.*, t1.type_name AS typeName FROM tb_maintain_pro t LEFT JOIN tb_antique_type t1 ON t1.id = t.pro_type_id WHERE 1=1 AND t.id = #{id}")
    MaintainPro selectOneById(@Param("id") Long id);

    List<MaintainPro> selectPageByParam(Page<MaintainPro> page, @Param("param") UserProParam param);
}
