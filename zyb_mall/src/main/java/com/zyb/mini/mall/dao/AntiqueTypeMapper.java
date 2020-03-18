package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.AntiqueType;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface AntiqueTypeMapper extends BaseMapper<AntiqueType> {

    @Select("select  `id`, `type_name` from tb_antique_type")
    List<DownBoxVO> selectDownBox();

}
