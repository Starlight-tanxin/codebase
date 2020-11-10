package com.navinfo.truck.bussiness.mould.dao.rdb;

import com.navinfo.truck.bussiness.mould.domain.BdMouldHistory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BdMouldHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BdMouldHistory record);

    int insertSelective(BdMouldHistory record);

    BdMouldHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BdMouldHistory record);

    int updateByPrimaryKeyWithBLOBs(BdMouldHistory record);

    int updateByPrimaryKey(BdMouldHistory record);

    /**
     * <p>根据模型id查询这个模型的所有操作记录<p>
     *
     * @param id: 模型id
     * @throws
     * @author TanXin
     * @date 2020/11/4 17:03
     * @return: java.util.List<com.navinfo.truck.bussiness.mould.domain.BdMouldHistory>
     */
    @Select("select id, tb_bd_mould_id as tbBdMouldId, operation_name as operationName, operation_id as operationId, " +
            "operation_time as operationTime, operation_item as operationItem,  operation_info as operationInfo, remarks " +
            "from tb_bd_mould_history where tb_bd_mould_id = #{id}")
    List<BdMouldHistory> selectAllByMouldId(@Param("id") Long id);

    /**
     * <p>删除模型数据后，删除相关的历史记录<p>
     *
     * @param id:
     * @throws
     * @author TanXin
     * @date 2020/11/4 17:30
     * @return: java.lang.Integer
     */
    @Delete("delete from tb_bd_mould_history where tb_bd_mould_id = #{id}")
    Integer deleteByMouldId(@Param("id") Long id);
}