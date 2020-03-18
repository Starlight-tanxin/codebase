package com.zyb.mini.mall.service.shop.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.dao.BookImgMapper;
import com.zyb.mini.mall.dao.GoodsBookMapper;
import com.zyb.mini.mall.pojo.entity.BookImg;
import com.zyb.mini.mall.pojo.entity.GoodsBook;
import com.zyb.mini.mall.pojo.param.goods.GoodsParam;
import com.zyb.mini.mall.pojo.vo.goods.GoodsBookVO;
import com.zyb.mini.mall.service.shop.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/29
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsBookMapper goodsBookMapper;


    @Resource
    private BookImgMapper bookImgMapper;

    @Override
    public List<GoodsBook> getGoodsList(GoodsParam param) {
        QueryWrapper<GoodsBook> qw = new QueryWrapper<>();
        if (param.getIsCollection() != null) {
            qw.eq("is_collection", param.getIsCollection());
        }
        if (param.getLimit() != null) {
            qw.last(" LIMIT " + param.getLimit());
        }
        return goodsBookMapper.selectList(qw);
    }

    @Override
    public Page<GoodsBook> getGoodsPage(GoodsParam param) {
        Page<GoodsBook> page = new Page<>(param.getPage(), param.getPageSize());
        List<GoodsBook> goodsList = goodsBookMapper.selectGoodsBookByParam(page, param);
        page.setRecords(goodsList);
        return page;
    }


    @Override
    public GoodsBookVO getGoodsDetail(Long goodsId) {
        GoodsBookVO goodsBook = goodsBookMapper.selectGoodsById(goodsId);
        if (goodsBook != null) {
            QueryWrapper<BookImg> qwImg = new QueryWrapper<>();
            qwImg.eq("goods_book_id", goodsId);
            List<BookImg> imgList = bookImgMapper.selectList(qwImg);
            goodsBook.setImgList(imgList);
            return goodsBook;
        }
        return null;
    }

    @Override
    public boolean checkGoodsExist(Long goodsId) {
        QueryWrapper<GoodsBook> qe = new QueryWrapper<>();
        qe.eq("id", goodsId);
        Integer count = goodsBookMapper.selectCount(qe);
        count = (count == null) ? 0 : count;
        return count > 0;
    }

    @Override
    public boolean checkGoodsNum(Long goodsId, Integer num) {
        GoodsBook goodsBook = goodsBookMapper.selectById(goodsId);
        if (goodsBook == null) {
            return false;
        }
        return goodsBook.getBookNum() >= num;
    }

    @Override
    public int updateGoodsNum(Long goodsId, Integer num) {
        return goodsBookMapper.updateGoodsNumSub(goodsId, num);
    }


    @Override
    public GoodsBookVO getGoodsByOrderId(Long orderId) {
        return null;
    }

    @Override
    public GoodsBook getGoodsById(Long goodsId) {
        return goodsBookMapper.selectById(goodsId);
    }
}
