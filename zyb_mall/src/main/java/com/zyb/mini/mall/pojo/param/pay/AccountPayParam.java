package com.zyb.mini.mall.pojo.param.pay;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>账户金额支付参数</p>
 *
 * @author: Tx
 * @date: 2019/11/9
 */
@ApiModel
@Data
public class AccountPayParam extends PayParam implements Serializable {
    private static final long serialVersionUID = -3129391704784575868L;

    @ApiModelProperty(value = "用户支付密码", example = Mock.PASSWORD)
    @NotBlank
    private String userPayPwd;

}
