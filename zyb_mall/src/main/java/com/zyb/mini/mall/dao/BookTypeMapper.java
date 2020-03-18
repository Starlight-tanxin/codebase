package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.BookType;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface BookTypeMapper extends BaseMapper<BookType> {

    @Select("select  `id`, `book_type` as typeName, `type_icon` AS typeIcon from tb_book_type")
    List<DownBoxVO> selectDownBox();

}
