package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.ShopBanner;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商城轮播图 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-29
 */
public interface ShopBannerMapper extends BaseMapper<ShopBanner> {

    @Select("select * from tb_shop_banner order by created_time,banner_idx limit 4")
    List<ShopBanner> selectAllBanner();

}
