package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.ExpressCompany;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-11-15
 */
public interface ExpressCompanyMapper extends BaseMapper<ExpressCompany> {


    @Select("SELECT company_name AS typeName, company_no AS typeNo FROM tb_express_company")
    List<DownBoxVO> selectDownBox();
}
