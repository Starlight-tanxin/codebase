package com.zyb.mini.mall.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/13
 */
@Data
public class MsgSessionDTO implements Serializable {
    private static final long serialVersionUID = -6204762378865040455L;

    /**
     * 会话的id
     */
    private String sessionId;

    private Long userId;
    /**
     * 名称
     */
    private String nickname;
    /**
     * 头像
     */
    private String headImg;
}
