package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 修复订单 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface MaintainMapper extends BaseMapper<Maintain> {

    List<Maintain> queryMaintainOrder(Page<Maintain> page, @Param("param") MaintainListParam param);


    @Select(" SELECT\n" +
            "b.realname AS maintainPro,\n" +
            "c.nickname AS userName,\n" +
            "d.type_name AS antiqueType,\n" +
            "a.*\n" +
            "FROM\n" +
            "tb_maintain a\n" +
            "LEFT JOIN tb_maintain_pro b ON b.id = a.maintain_pro_id\n" +
            "LEFT JOIN tb_user c ON a.user_id = c.id\n" +
            "LEFT JOIN tb_antique_type d ON a.antique_type_id = d.id\n" +
            "WHERE 1=1 AND a.id = #{id}")
    Maintain selectMaintainById(@Param("id") Long id);

}
