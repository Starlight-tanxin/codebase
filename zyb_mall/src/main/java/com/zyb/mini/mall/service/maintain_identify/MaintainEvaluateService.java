package com.zyb.mini.mall.service.maintain_identify;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.entity.MaintainEvaluate;

/**
 *  评价
 */
public interface MaintainEvaluateService extends IService<MaintainEvaluate> {

    Long add(MaintainEvaluate maintainEvaluate);

}
