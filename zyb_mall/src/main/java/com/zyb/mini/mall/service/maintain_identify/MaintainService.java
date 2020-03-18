package com.zyb.mini.mall.service.maintain_identify;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;

/**
 * @author: Tx
 * @date: 2019/10/27
 */
public interface MaintainService extends IService<Maintain> {

    Maintain getInfo(Long id);

    Long add(Maintain entity);

    Long updateByKey(Maintain entity);

    /**
     * 获取分页数据
     *
     * @param param 参数
     * @return
     */
    Page<Maintain> selectPageByParam(MaintainListParam param);
}
