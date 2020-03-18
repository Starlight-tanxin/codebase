package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.Surname;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 姓氏表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Repository
public interface SurnameMapper extends BaseMapper<Surname> {



    Surname findFirst();

}
