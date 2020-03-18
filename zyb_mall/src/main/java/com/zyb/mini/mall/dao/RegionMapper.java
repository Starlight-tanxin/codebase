package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.Region;
import com.zyb.mini.mall.pojo.vo.city.ProvinceVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 区县信息表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-08-28
 */
public interface RegionMapper extends BaseMapper<Region> {

    List<ProvinceVO> selectProvinceAndCityAndRegion();

    @Select("SELECT * FROM tb_region WHERE region = #{region}")
    Region selectOneByRegion(@Param("region") String region);

    @Select("SELECT * FROM tb_region WHERE region_no = #{regionNo}")
    Region selectOneByRegionNo(@Param("regionNo") String regionNo);
}
