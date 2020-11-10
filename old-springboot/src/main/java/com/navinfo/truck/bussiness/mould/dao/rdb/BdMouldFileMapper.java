package com.navinfo.truck.bussiness.mould.dao.rdb;

import com.navinfo.truck.bussiness.mould.domain.BdMouldFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BdMouldFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BdMouldFile record);

    int insertSelective(BdMouldFile record);

    BdMouldFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BdMouldFile record);

    int updateByPrimaryKey(BdMouldFile record);

    /**
     * <p>按照模型表id查询这个模型的附件数据<p>
     *
     * @param mouldId:
     * @throws null
     * @author TanXin
     * @date 2020/11/4 17:00
     * @return: java.util.List<com.navinfo.truck.bussiness.mould.domain.BdMouldFile>
     */
    @Select("select id, file_name as fileName, file_url as fileUrl, create_time as createTime, create_by as createBy, create_name as createName from tb_bd_mould_file where tb_bd_mould_id = #{mouldId}")
    List<BdMouldFile> selectAllByMouldId(@Param("mouldId") Long mouldId);

    /**
     * <p>删除模具表数据后，吧相关的文件删除<p>
     *
     * @param mouldId:
     * @throws
     * @author TanXin
     * @date 2020/11/4 17:29
     * @return: java.lang.Integer
     */
    @Delete("delete from tb_bd_mould_file where tb_bd_mould_id = #{mouldId}")
    Integer deleteByMouldId(@Param("mouldId") Long mouldId);

    /**
     * <p>删除模具相关的一个附件<p>
     *
     * @param id: 附件id
     * @param mouldId: 模具id
     * @throws
     * @author TanXin
     * @date 2020/11/5 9:21
     * @return: java.lang.Integer
     */
    @Delete("delete from tb_bd_mould_file where id = #{id} and tb_bd_mould_id = #{mouldId}")
    Integer deleteByIdAndMouldId(@Param("id") Long id, @Param("mouldId") Long mouldId);

}