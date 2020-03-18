package com.zyb.mini.mall.service.maintain_identify.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.entity.Maintain;
import com.zyb.mini.mall.pojo.entity.MaintainEvaluate;
import com.zyb.mini.mall.service.maintain_identify.MaintainEvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * tzw 评价
 */
@Service
public class MaintainEvaluateServiceImpl extends ServiceImpl<MaintainEvaluateMapper, MaintainEvaluate> implements MaintainEvaluateService {

    @Resource
    private MaintainEvaluateMapper maintainEvaluateMapper;
    @Resource
    private MaintainProMapper maintainProMapper;
    @Resource
    private UserProMapper userProMapper;
    @Resource
    private MaintainMapper maintainMapper;
    @Resource
    private IdentifyMapper identifyMapper;


    @Override
    public Long add(MaintainEvaluate param) {
        //判断 专家是否存在与 该专家与订单里的专家是否一致防止恶意评价
        if (param.getBusinessType()) {
            Identify identify = identifyMapper.selectById(param.getMaintainId());
            if (userProMapper.selectById(param.getProId()) == null || identify == null || identify.getUserProId().longValue() != param.getProId()) {
                R.error(Status.PRO_NOT_EXIST);
            }
        } else {
            Maintain maintain = maintainMapper.selectById(param.getMaintainId());

            if (maintainProMapper.selectById(param.getProId()) == null || maintain == null || maintain.getMaintainProId() != param.getProId()) {
                R.error(Status.PRO_NOT_EXIST);
            }
        }
        maintainEvaluateMapper.insert(param);
        return param.getId();
    }
}
