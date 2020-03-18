package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyb.mini.mall.pojo.entity.BookOtherType;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 商品其他分类 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-30
 */
public interface BookOtherTypeMapper extends BaseMapper<BookOtherType> {

    @Select("select  `id`, `type_name` from tb_book_other_type")
    List<DownBoxVO> selectDownBox();

}
