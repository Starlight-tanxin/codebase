package com.zyb.mini.mall.service.background.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.zyb.mini.mall.constant.MaintainState;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.background.*;
import com.zyb.mini.mall.pojo.param.identify.IdentifyListParam;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;
import com.zyb.mini.mall.pojo.param.identify.MaintainMessageParam;
import com.zyb.mini.mall.pojo.param.identify.MaintainMessageTwoParam;
import com.zyb.mini.mall.pojo.param.order.QueryOrderListParam;
import com.zyb.mini.mall.pojo.vo.Order.IdentifyOrderListVo;
import com.zyb.mini.mall.pojo.vo.Order.MinttainOrderListVo;
import com.zyb.mini.mall.pojo.vo.Order.OrderListVo;
import com.zyb.mini.mall.service.background.BackgroundService;
import com.zyb.mini.mall.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JPPing
 * @Date 2019/11/1
 */
@Service
public class BackgroundServiceImpl extends ServiceImpl<GoodsBookMapper, GoodsBook> implements BackgroundService {

    @Resource
    GoodsBookMapper goodsBookMapper;
    @Resource
    BookImgMapper bookImgMapper;
    @Resource
    UserProMapper userProMapper;
    @Resource
    MaintainProMapper maintainProMapper;
    @Resource
    ShopOrderMapper shopOrderMapper;
    @Resource
    MaintainCompanyImgMapper maintainCompanyImgMapper;
    @Resource
    MaintainMapper maintainMapper;
    @Resource
    MaintainMsgMapper maintainMsgMapper;
    @Resource
    IdentifyMapper identifyMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ShopOrderDetailMapper shopOrderDetailMapper;

    @Override
    @Transactional
    public R addBook(AddBookParam addBookParam) {
        GoodsBook goodsBook = new GoodsBook();
        BeanUtil.copyProperties(addBookParam, goodsBook);
        goodsBook.setCreatedTime(LocalDateTime.now());
        goodsBookMapper.insert(goodsBook);
        if (goodsBook.getImgList() != null) {
            for (int i = 0; i < goodsBook.getImgList().size(); i++) {
                goodsBook.getImgList().get(i).setGoodsBookId(goodsBook.getId());
                goodsBook.getImgList().get(i).setId(null);
                bookImgMapper.insert(goodsBook.getImgList().get(i));
            }
        }
        return R.success();
    }

    @Override
    @Transactional
    public R updateBook(UpdateBookParam updateBookParam) {
        if (goodsBookMapper.selectById(updateBookParam.getId()) == null) {
            return null;
        }
        GoodsBook goodsBook = new GoodsBook();
        BeanUtil.copyProperties(updateBookParam, goodsBook);
        goodsBookMapper.updateById(goodsBook);
        if (goodsBook.getImgList() != null) {
            if (goodsBook.getImgList().size() > 0) {
                bookImgMapper.delete(new QueryWrapper<BookImg>().lambda().eq(BookImg::getGoodsBookId, goodsBook.getId()));
            }
            for (int i = 0; i < goodsBook.getImgList().size(); i++) {
                goodsBook.getImgList().get(i).setId(null);
                goodsBook.getImgList().get(i).setGoodsBookId(goodsBook.getId());
                bookImgMapper.insert(goodsBook.getImgList().get(i));
            }
        }
        return R.success();
    }

    @Override
    public R putawayBook(ByIdParam param) {
        GoodsBook goodsBook = goodsBookMapper.selectById(param.getId());
        if (goodsBook == null) {
            return null;
        }
        if (goodsBook.getIsUp()) {
            return R.success("图书已上架！");
        } else {
            goodsBook.setIsUp(true);
            goodsBookMapper.updateById(goodsBook);
        }
        return R.success();
    }

    @Override
    public R soldOutBook(ByIdParam param) {
        GoodsBook goodsBook = goodsBookMapper.selectById(param.getId());
        if (goodsBook == null) {
            return null;
        }
        if (goodsBook.getIsUp()) {
            goodsBook.setIsUp(false);
            goodsBookMapper.updateById(goodsBook);
        } else {
            return R.success("图书已下架！");
        }
        return R.success();
    }

    @Override
    @Transactional
    public R deleteBook(ByIdParam param) {
        GoodsBook goodsBook = goodsBookMapper.selectById(param.getId());
        if (goodsBook == null) {
            return null;
        }
        bookImgMapper.delete(new QueryWrapper<BookImg>().lambda().eq(BookImg::getGoodsBookId, goodsBook.getId()));
        goodsBookMapper.deleteById(param.getId());
        return R.success();
    }

    @Override
    public R<List<OrderListVo>> queryShopOrder(QueryOrderListParam param) {
        List<ShopOrder> shopOrderList = shopOrderMapper.queryShopOrder(new Page<>(param.getPage(), param.getPageSize()), param);
        if (shopOrderList == null) {
            return null;
        }
        List<Long> ids = shopOrderList.stream().map(ShopOrder::getId).collect(Collectors.toList());
        List<Long> userIds = shopOrderList.stream().map(ShopOrder::getUserId).collect(Collectors.toList());
        List<User> users = userMapper.queryUserName(userIds);
        List<ShopOrderDetail> detailList = shopOrderDetailMapper.selectAllByOrderIds(ids);
        List<OrderListVo> orderListVos = shopOrderList.stream().map(shopOrder -> {
            OrderListVo orderListVo = new OrderListVo();
            BeanUtil.copyProperties(shopOrder, orderListVo);
            orderListVo.setAmount(shopOrder.getOrderPrice());
            return orderListVo;
        }).collect(Collectors.toList());
        for (OrderListVo vo : orderListVos) {
            for (User user : users) {
                String nickname = vo.getNickName();
                if (Strings.isNullOrEmpty(nickname)) {
                    if (user.getId().equals(vo.getUserId())) {
                        vo.setNickName(user.getNickname());
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        for (OrderListVo vo : orderListVos) {
            for (ShopOrderDetail detail : detailList) {
                String mainImg = vo.getMainImg();
                if (Strings.isNullOrEmpty(mainImg)) {
                    if (detail.getShopOrderId().equals(vo.getId())) {
                        vo.setMainImg(detail.getMainImg());
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return R.success(orderListVos);
    }


    @Override
    public R addIdentifySpecialist(AddUserProParam addUserProParam) {
        UserPro userPro = new UserPro();
        BeanUtil.copyProperties(addUserProParam, userPro);
        userPro.setCreatedTime(DateUtils.getLocalDateTime());

        userProMapper.insert(userPro);
        return R.success();
    }

    @Override
    public R updateIdentifySpecialist(UpdateUserProParam updateUserProParam) {
        if (userProMapper.selectById(updateUserProParam.getId()) == null) {
            return null;
        }
        UserPro userPro = new UserPro();
        BeanUtil.copyProperties(updateUserProParam, userPro);
        userProMapper.updateById(userPro);
        return R.success();
    }

    @Override
    public R deleteIdentifySpecialist(ByIdParam param) {
        if (userProMapper.selectById(param.getId()) == null) {
            return null;
        }
        userProMapper.deleteById(param.getId());
        return R.success();
    }

    @Override
    @Transactional
    public R AddPlatformMessage(MaintainMessageParam param) {
        Maintain maintain = maintainMapper.selectById(param.getId());
        if (maintain == null) {
            return null;
        }
        maintain.setCmMaintainAmount(param.getCmMaintainAmount());
        maintain.setCmMaintainDay(param.getCmMaintainDay());
        maintain.setCmName("湘阅图书");
        maintain.setCmAddress("湖南省长沙市芙蓉区韭菜园街道文艺路16号融都公寓1幢A单元16层16018");
        maintain.setCmMobile("0731-84121691");
        maintain.setMaintainState(MaintainState.CM_EVALUATE);
        maintainMapper.updateById(maintain);
        MaintainMsg maintainMsg = new MaintainMsg();
        maintainMsg.setCmMaintainMsg(param.getCmMaintainMsg());
        maintainMsg.setMaintainId(maintain.getId());
        maintainMsg.setCreatedTime(LocalDateTime.now());
        maintainMsg.setIsFixed(true);
        maintainMsgMapper.insert(maintainMsg);
        if (param.getMaintainCompanyImgs() != null) {
            for (MaintainCompanyImg maintainCompanyImg : param.getMaintainCompanyImgs()) {
                maintainCompanyImg.setMaintainMsgId(maintainMsg.getId());
                maintainCompanyImgMapper.insert(maintainCompanyImg);
            }
        }
        return R.success();
    }

    @Override
    @Transactional
    public R AddMaintainMsg(MaintainMessageTwoParam param) {
        Maintain maintain = maintainMapper.selectById(param.getId());
        if (maintain == null) {
            return null;
        }
        if (!maintain.getMaintainState().equals(MaintainState.FIRST_PAYING)) {
            return R.error(Status.ORDER_ERROR_STATE);
        }
        MaintainMsg maintainMsg = new MaintainMsg();
        maintainMsg.setCmMaintainMsg(param.getCmMaintainMsg());
        maintainMsg.setMaintainId(maintain.getId());
        maintainMsg.setCreatedTime(LocalDateTime.now());
        maintainMsg.setIsFixed(false);
        maintainMsgMapper.insert(maintainMsg);
        if (param.getMaintainCompanyImgs() != null) {
            for (MaintainCompanyImg maintainCompanyImg : param.getMaintainCompanyImgs()) {
                maintainCompanyImg.setMaintainMsgId(maintainMsg.getId());
                maintainCompanyImgMapper.insert(maintainCompanyImg);
            }
        }
        // 改变状态
        maintain.setMaintainState(MaintainState.COMPLETE);
        maintain.setFixedEndTime(LocalDateTime.now());
        maintainMapper.updateById(maintain);
        return R.success();
    }

    @Override
    public R AddReachTime(ByIdParam param) {
        Maintain maintain = maintainMapper.selectById(param.getId());
        if (maintain == null) {
            return null;
        }
        maintain.setCmDate(LocalDateTime.now());
        maintain.setFixedStartTime(LocalDateTime.now());
        maintainMapper.updateById(maintain);
        return R.success();
    }

    @Override
    public R<List<IdentifyOrderListVo>> queryIdentifyOrder(IdentifyListParam param) {
        List<Identify> identifies = identifyMapper.queryIdentifyOrder(new Page<>(param.getPage(), param.getPageSize()), param);
        if (identifies == null) {
            return null;
        }
        List<IdentifyOrderListVo> identifyOrderListVos = identifies.stream().map(identify -> {
            IdentifyOrderListVo identifyOrderListVo = new IdentifyOrderListVo();
            BeanUtil.copyProperties(identify, identifyOrderListVo);
            return identifyOrderListVo;
        }).collect(Collectors.toList());
        return R.success(identifyOrderListVos);
    }

    @Override
    public R<List<MinttainOrderListVo>> queryMaintainOrder(MaintainListParam param) {
        List<Maintain> maintains = maintainMapper.queryMaintainOrder(new Page<>(param.getPage(), param.getPageSize()), param);
        if (maintains == null) {
            return null;
        }
        List<MinttainOrderListVo> minttainOrderListVos = maintains.stream().map(minttain -> {
            MinttainOrderListVo minttainOrderListVo = new MinttainOrderListVo();
            BeanUtil.copyProperties(minttain, minttainOrderListVo);
            return minttainOrderListVo;
        }).collect(Collectors.toList());
        return R.success(minttainOrderListVos);
    }

    @Override
    @Transactional
    public R addMaintainSpecialist(AddMaintainProParam addMaintainProParam) {
        MaintainPro maintainPro = new MaintainPro();
        BeanUtil.copyProperties(addMaintainProParam, maintainPro);
        maintainPro.setCreatedTime(LocalDateTime.now());
        maintainProMapper.insert(maintainPro);
        return R.success();
    }

    @Override
    @Transactional
    public R updateMaintainSpecialist(UpdateMaintainProParam updateMaintainProParam) {
        if (updateMaintainProParam.getId() == null) {
            return null;
        }
        MaintainPro maintainPro = new MaintainPro();
        BeanUtil.copyProperties(updateMaintainProParam, maintainPro);
        maintainProMapper.updateById(maintainPro);
        return R.success();
    }

    @Override
    @Transactional
    public R deleteMaintainSpecialist(ByIdParam param) {
        MaintainPro maintainPro = maintainProMapper.selectById(param.getId());
        if (maintainPro == null) {
            return null;
        }
        maintainProMapper.deleteById(param.getId());
        return R.success();
    }

}
