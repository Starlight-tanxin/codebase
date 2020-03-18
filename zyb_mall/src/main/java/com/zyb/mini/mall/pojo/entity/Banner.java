package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by 谭健 on 2019/11/8. 星期五. 11:45.
 * © All Rights Reserved.
 */

@Data
@TableName("tb_banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = -5224217447518232519L;
    @TableId(type = IdType.AUTO)
    private long id;

    private String mainImg;
    private String url;

}
