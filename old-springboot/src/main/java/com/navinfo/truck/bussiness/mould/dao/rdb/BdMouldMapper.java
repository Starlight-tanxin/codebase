package com.navinfo.truck.bussiness.mould.dao.rdb;

import com.navinfo.truck.bussiness.mould.domain.BdMould;
import com.navinfo.truck.bussiness.mould.model.req.MouldReq;

import java.util.List;

public interface BdMouldMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BdMould record);

    int insertSelective(BdMould record);

    BdMould selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BdMould record);

    int updateByPrimaryKey(BdMould record);

    List<BdMould> selectAllByParam(MouldReq mouldReq);
}