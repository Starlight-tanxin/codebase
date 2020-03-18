package com.zyb.mini.mall.web.controller.back;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.param.identify.*;
import com.zyb.mini.mall.pojo.param.background.*;
import com.zyb.mini.mall.pojo.param.order.QueryOrderListParam;
import com.zyb.mini.mall.pojo.vo.Order.IdentifyOrderListVo;
import com.zyb.mini.mall.pojo.vo.Order.MinttainOrderListVo;
import com.zyb.mini.mall.pojo.vo.Order.OrderListVo;
import com.zyb.mini.mall.service.background.BackgroundService;
import com.zyb.mini.mall.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author JPPing
 * @Date   2019/11/1
 */

@Api(tags = {"后台系统操作"})
@RequestMapping("/zyb/bookOperation")
@RestController
public class BackgroundController extends BaseController {


    private final BackgroundService addBookService;

    public BackgroundController(BackgroundService addBookService) {
        this.addBookService = addBookService;
    }

    @ApiOperation("新增图书信息")
    @ResponseBody
    @PostMapping("/addGoodsBook")
    R addGoodsBook(@Valid AddBookParam param) {
        return addBookService.addBook(param);
    }

    @ApiOperation("删除图书信息")
    @GetMapping("/deleteBook")
    R deleteBook(@Valid ByIdParam param) {
        return addBookService.deleteBook(param);
    }

    @ApiOperation("上架图书信息")
    @GetMapping("/putawayBook")
    R putawayBook(@Valid ByIdParam param) {
        return addBookService.putawayBook(param);
    }

    @ApiOperation("下架图书信息")
    @GetMapping("/soldOutBook")
    R soldOutBook(@Valid ByIdParam param) {
        return addBookService.soldOutBook(param);
    }

    @ApiOperation("商品全部订单信息")
    @PostMapping("/queryShopOrder")
    R<List<OrderListVo>> queryShopOrder(@Valid QueryOrderListParam  param) {
        return addBookService.queryShopOrder(param);
    }

    @ApiOperation("修改图书信息")
    @PostMapping("/updateBook")
    R updateBook(@Valid UpdateBookParam  param) {
        return addBookService.updateBook(param);
    }

    @ApiOperation("添加鉴赏专家")
    @PostMapping("/addIdentifySpecialist")
    R addIdentifySpecialist(@Valid AddUserProParam addUserProParam) {
        return addBookService.addIdentifySpecialist(addUserProParam);
    }

    @ApiOperation("修改鉴赏专家")
    @PostMapping("/updateIdentifySpecialist")
    R updateIdentifySpecialist(@Valid UpdateUserProParam updateUserProParam) {
        return addBookService.updateIdentifySpecialist(updateUserProParam);
    }

    @ApiOperation("删除鉴赏专家")
    @GetMapping("/deleteIdentifySpecialist")
    R deleteIdentifySpecialist(@Valid ByIdParam param) {
        return addBookService.deleteIdentifySpecialist(param);
    }

    @ApiOperation("添加修复专家")
    @PostMapping("/addMaintainSpecialist")
    R addMaintainSpecialist(@Valid AddMaintainProParam param) {
        return addBookService.addMaintainSpecialist(param);
    }

    @ApiOperation("修改修复专家")
    @PostMapping("/updateMaintainSpecialist")
    R updateMaintainSpecialist(@Valid UpdateMaintainProParam param) {
        return addBookService.updateMaintainSpecialist(param);
    }

    @ApiOperation("删除修复专家")
    @GetMapping("/deleteMaintainSpecialist")
    R deleteMaintainSpecialist(@Valid ByIdParam param) {
        return addBookService.deleteMaintainSpecialist(param);
    }

    @ApiOperation("鉴赏全部订单信息")
    @PostMapping("/queryIdentifyOrder")
    R<List<IdentifyOrderListVo>> queryIdentifyOrder(@Valid IdentifyListParam param) {
        return addBookService.queryIdentifyOrder(param);
    }

    @ApiOperation("修复全部订单信息")
    @PostMapping("/queryMaintainOrder")
    R<List<MinttainOrderListVo>> queryMaintainOrder(@Valid MaintainListParam param) {
        return addBookService.queryMaintainOrder(param);
    }

    @ApiOperation("修复预回复")
    @PostMapping("/AddPlatformMessage")
    R AddPlatformMessage(@Valid MaintainMessageParam param) {
        return addBookService.AddPlatformMessage(param);
    }

    @ApiOperation("修复完成回复")
    @PostMapping("/AddMaintainMsg")
    R AddMaintainMsg(@Valid  MaintainMessageTwoParam param) {
        return addBookService.AddMaintainMsg(param);
    }

    @ApiOperation("填入到达时间")
    @PostMapping("/AddReachTime")
    R AddReachTime(@Valid ByIdParam param) {
        return addBookService.AddReachTime(param);
    }

}
