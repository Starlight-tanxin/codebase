package com.zyb.mini.mall.service.maintain_identify;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zyb.mini.mall.pojo.entity.MaintainPro;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import com.zyb.mini.mall.pojo.param.user.UserProParam;

import java.util.List;

/**
 * <p>
 * 修复得专家表 服务类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
public interface MaintainProService extends IService<MaintainPro> {
    MaintainPro getInfo(Long id);

    Page<MaintainPro> selectPageByParam(UserProParam param);
}
