package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.param.identify.IdentifyListParam;
import com.zyb.mini.mall.pojo.param.identify.IdentifyParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 鉴赏记录（鉴赏订单） Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface IdentifyMapper extends BaseMapper<Identify> {
    List<Identify> queryIdentifyOrder(Page<Identify> page,@Param("param") IdentifyListParam param);

    List<Identify> selectPageByParam(IPage<Identify> page, @Param("param") IdentifyParam param);

}
