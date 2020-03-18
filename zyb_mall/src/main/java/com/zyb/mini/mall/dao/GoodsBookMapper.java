package com.zyb.mini.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.param.goods.GoodsParam;
import com.zyb.mini.mall.pojo.vo.goods.GoodsBookVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
public interface GoodsBookMapper extends BaseMapper<GoodsBook> {


    /**
     * 获取推荐商品
     *
     * @param num 数量
     * @return 集合
     */
    List<GoodsBook> getRecommendGoods(@Param("num") Integer num);

    List<GoodsBook> selectGoodsBookByParam(Page<GoodsBook> page, @Param("param") GoodsParam param);

    /**
     * 减少商品库存
     *
     * @param goodsId 商品id
     * @param num     数量
     * @return
     */
    @Update("update `tb_goods_book` set `book_num`  = `book_num` - #{num} where `id` = #{goodsId}")
    int updateGoodsNumSub(Long goodsId, Integer num);

    /**
     * 增加商品库存
     *
     * @param goodsId 商品id
     * @param num     数量
     * @return
     */
    @Update("update `tb_goods_book` set `book_num`  = `book_num` + #{num} where `id` = #{goodsId}")
    int updateGoodsNumAdd(Long goodsId, Integer num);

    @Select("SELECT\n" +
            "\ta.*,\n" +
            "\tb.year_name AS yearName,\n" +
            "\tc.book_type AS bookTypeName,\n" +
            "\td.type_name AS bookOtherTypeName,\n" +
            "\te.type_name AS phaseTypeName \n" +
            "FROM\n" +
            "\ttb_goods_book a\n" +
            "\tLEFT JOIN tb_year b ON b.id = a.year_id\n" +
            "\tLEFT JOIN tb_book_type c ON c.id = a.book_type_id\n" +
            "\tLEFT JOIN tb_book_other_type d ON d.id = a.book_other_type_id\n" +
            "\tLEFT JOIN tb_phase_type e ON e.id = a.phase_type_id \n" +
            "WHERE\n" +
            "\ta.id = #{goodsId}")
    GoodsBookVO selectGoodsById(@Param("goodsId") Long goodsId);

}
