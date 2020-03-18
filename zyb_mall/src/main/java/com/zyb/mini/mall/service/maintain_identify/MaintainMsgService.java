package com.zyb.mini.mall.service.maintain_identify;

import com.zyb.mini.mall.pojo.entity.MaintainMsg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
public interface MaintainMsgService extends IService<MaintainMsg> {
    Integer add(MaintainMsg maintainMsg);
}
