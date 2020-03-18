package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.PhaseType;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 品相类别 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface PhaseTypeMapper extends BaseMapper<PhaseType> {

    @Select("select  `id`, `type_name` from tb_phase_type")
    List<DownBoxVO> selectDownBox();

}
