package com.zyb.mini.mall.pojo.dto;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/13
 */
@Data
public class UserMsgDTO implements Serializable {
    private static final long serialVersionUID = -1719234918914008791L;

    /**
     * 会话的id
     */
    @ApiModelProperty(value = "会话的id")
    private String sessionId;

    @ApiModelProperty(value = "用户id", example = Mock.NUMBER)
    private Long userId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
    /**
     * 头像
     */
    @ApiModelProperty(value = " 头像")
    private String headImg;

    @ApiModelProperty(value = " 消息内容")
    private String msg;

    /**
     * 发送消息的时间
     */
    @ApiModelProperty(value = "发送消息的时间-时间戳", example = Mock.MOBILE)
    private Long time;

    /**
     * 是不是官方的消息
     * true-是
     */
    @ApiModelProperty(value = "是不是官方的消息  true-是", example = Mock.FALSE)
    private Boolean isCompany;
}
