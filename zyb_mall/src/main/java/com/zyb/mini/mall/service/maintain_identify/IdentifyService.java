package com.zyb.mini.mall.service.maintain_identify;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.param.identify.IdentifyParam;


/**
 * tzw  鉴赏记录
 */
public interface IdentifyService extends IService<Identify> {
    Identify getInfo(Long id);

    Long add(Identify entity);

    Long updateByKey(Identify entity);

    Page<Identify> selectPageByParam(IdentifyParam param);
}
