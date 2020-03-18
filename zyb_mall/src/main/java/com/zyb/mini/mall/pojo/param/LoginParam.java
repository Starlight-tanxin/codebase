package com.zyb.mini.mall.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Created by 谭健 on 2019/10/29. 星期二. 17:55.
 * © All Rights Reserved.
 */
@Data
public class LoginParam {


    /**
     * getCredential 接口获取的
     */
    @NotBlank
    private String openId;
    @NotBlank
    private String headImg;
    @NotBlank
    private String nickname;

}
