package com.zyb.mini.mall.pojo.param.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/18
 */
@Data
public class UserParam implements Serializable {
    private static final long serialVersionUID = 2804244589381838531L;

    private String nickname;

    private String headImg;

    private String mobile;
}
