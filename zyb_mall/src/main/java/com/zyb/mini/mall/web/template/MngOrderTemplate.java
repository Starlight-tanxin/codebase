package com.zyb.mini.mall.web.template;

import com.zyb.mini.mall.pojo.entity.ShopOrder;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.entity.UserAddress;
import com.zyb.mini.mall.pojo.param.order.QueryOrderListParam;
import com.zyb.mini.mall.pojo.vo.MngOrderDetailVo;
import com.zyb.mini.mall.pojo.vo.Order.OrderListVo;
import com.zyb.mini.mall.service.background.BackgroundService;
import com.zyb.mini.mall.service.common.CommonService;
import com.zyb.mini.mall.service.order.OrderService;
import com.zyb.mini.mall.service.order.ShopOrderDetailService;
import com.zyb.mini.mall.service.user.UserAddressService;
import com.zyb.mini.mall.service.user.UserService;
import com.zyb.mini.mall.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/10 星期日 12:22.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@RequestMapping("/template/mng/order")
@Controller
@Validated
public class MngOrderTemplate {


    private final BackgroundService backgroundService;
    private final OrderService orderService;
    private final UserAddressService userAddressService;
    private final CommonService commonService;
    private final UserService userService;
    private final ShopOrderDetailService shopOrderDetailService;

    public MngOrderTemplate(BackgroundService backgroundService, OrderService orderService, UserAddressService userAddressService, ShopOrderDetailService shopOrderDetailService, CommonService commonService, UserService userService) {
        this.backgroundService = backgroundService;
        this.orderService = orderService;
        this.userAddressService = userAddressService;
        this.commonService = commonService;
        this.shopOrderDetailService = shopOrderDetailService;
        this.userService = userService;
    }


    @PostMapping("/tk/{id}")
    public void tk(@PathVariable("id") long id) {
        orderService.refund(id);
    }


    @ResponseBody
    @PostMapping("/{id}/setExpressNo/{cn}/{cn_nb}")
    public void setExpressNo(@PathVariable("cn") String nb, @PathVariable("cn_nb") String cn_nb, @PathVariable("id") long id) {
        ShopOrder order = orderService.getById(id);
        order.setExpressNo(cn_nb);
        order.setExpressCompanyNo(nb);
        order.setSendGoodsTime(DateUtils.getLocalDateTime());
        order.setOrderState(3);
        orderService.updateById(order);
    }


    @GetMapping("/order-detail/{id}")
    public String orderDetail(@PathVariable("id") long id, Model model) {
        // 商品清单
        List<MngOrderDetailVo> list = shopOrderDetailService.LIST(id);
        model.addAttribute("list", list);

        // 订单总金额
        Optional<BigDecimal> reduce =list.stream().map(amount->{
            return amount.getPrice().multiply(BigDecimal.valueOf(amount.getNum()));
        }).reduce(BigDecimal::add);
        //垃圾代码
        //Optional<BigDecimal> reduce = list.stream().map(MngOrderDetailVo::getPrice).reduce(BigDecimal::add);
        model.addAttribute("total", reduce.orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP));

        // 订单详情
        ShopOrder order = orderService.getById(id);
        model.addAttribute("order", order);

        //用户
        User user =userService.getById(order.getUserId());
        model.addAttribute("userName",user.getNickname());

        // 收货地址信息
        UserAddress address = userAddressService.getById(order.getUserAddressId());
        model.addAttribute("address", Optional.ofNullable(address).orElse(new UserAddress()));
        //快递公司编码
        model.addAttribute("ExpressType", commonService.selectExpressTypeBox());
        return "mng/order-detail";
    }

    // unshipped
    // shipped
    // complete
    // refund

    @GetMapping("{state}")
    public String mngBook(Model model, @PathVariable("state") @NotBlank String state) {
        QueryOrderListParam param = new QueryOrderListParam();
        param.setPage(1);
        param.setPageSize(Integer.MAX_VALUE);
        switch (state) {
            case "unshipped":
                param.setOrderState(2);
                break;
            case "shipped":
                param.setOrderState(3);
                break;
            case "complate":
                param.setOrderState(4);
                break;
            case "refund":
                param.setOrderState(7);
                break;
            default:
        }

        List<OrderListVo> list = backgroundService.queryShopOrder(param).getBody();

        model.addAttribute("list", list);
        return "mng/order-" + state;
    }
}
