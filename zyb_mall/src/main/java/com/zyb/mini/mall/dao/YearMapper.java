package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.Year;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 年份列别表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-26
 */
public interface YearMapper extends BaseMapper<Year> {

    @Select("select  `id`, `year_name` as typeName from tb_year")
    List<DownBoxVO> selectDownBox();

}
