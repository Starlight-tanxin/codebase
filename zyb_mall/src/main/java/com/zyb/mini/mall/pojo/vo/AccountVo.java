package com.zyb.mini.mall.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/11/6 星期三 22:31.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Data
public class AccountVo {

    /**
     * 实际金额
     */
    private BigDecimal actualAmount;

    /**
     * 冻结金额
     */
    private BigDecimal freezeAmount;

    private List<Column> columns;

    @Data
    public static class Column {
        /**
         * 1 - 鉴赏支付单
         * 2 - 修复得支付单（修复是2次支付）
         * 3 - 书城得支付单
         * 4 - 助理得支付单
         * 5 - 充值得支付单
         * 6 - 提现得支付单
         */
        private Integer payType;

        private LocalDateTime payCompleteTime;


        /**
         * 支付金额
         */
        private BigDecimal amont;

    }
}
