package com.zyb.mini.mall.service.background;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.param.identify.*;
import com.zyb.mini.mall.pojo.param.background.*;
import com.zyb.mini.mall.pojo.param.order.QueryOrderListParam;
import com.zyb.mini.mall.pojo.vo.Order.IdentifyOrderListVo;
import com.zyb.mini.mall.pojo.vo.Order.MinttainOrderListVo;
import com.zyb.mini.mall.pojo.vo.Order.OrderListVo;

import java.util.List;

/**
 * @author JPPing
 * @Date 2019/11/6
 */
public interface BackgroundService {

    //图书操作
    //添加图书
    R addBook(AddBookParam addBookParam);

    //修改图书
    R updateBook(UpdateBookParam updateBookParam);

    //下架图书
    R putawayBook(ByIdParam param);

    //上架图书
    R soldOutBook(ByIdParam param);

    //删除图书
    R deleteBook(ByIdParam param);

    //商城订单操作
    //查询所有订单分页
    R<List<OrderListVo>> queryShopOrder(QueryOrderListParam param);

    //鉴赏专家操作
    R addIdentifySpecialist(AddUserProParam addUserProParam);

    R updateIdentifySpecialist(UpdateUserProParam updateUserProParam);

    R deleteIdentifySpecialist(ByIdParam param);

    //鉴赏订单操作
    R<List<IdentifyOrderListVo>> queryIdentifyOrder(IdentifyListParam param);

    //修复预回复订单
    R AddPlatformMessage(MaintainMessageParam param);

    //修复回复订单
    R AddMaintainMsg(MaintainMessageTwoParam param);

    //写入到达时间
    R AddReachTime(ByIdParam param);

    //修复专家操作
    R addMaintainSpecialist(AddMaintainProParam maintainProParam);

    R updateMaintainSpecialist(UpdateMaintainProParam updateMaintainProParam);

    R deleteMaintainSpecialist(ByIdParam param);

    //修复订单操作
    R<List<MinttainOrderListVo>> queryMaintainOrder(MaintainListParam param);
}




