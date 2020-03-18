package com.zyb.mini.mall.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.dao.IdentifyMapper;
import com.zyb.mini.mall.dao.MaintainEvaluateMapper;
import com.zyb.mini.mall.dao.UserProMapper;
import com.zyb.mini.mall.pojo.entity.Identify;
import com.zyb.mini.mall.pojo.entity.MaintainEvaluate;
import com.zyb.mini.mall.pojo.entity.UserPro;
import com.zyb.mini.mall.pojo.param.user.UserProParam;
import com.zyb.mini.mall.service.user.UserProService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * tzw 鉴赏专家
 */
@Service
public class UserProServiceImpl extends ServiceImpl<UserProMapper, UserPro> implements UserProService {

    @Resource
    private UserProMapper userProMapper;

    @Resource
    private MaintainEvaluateMapper maintainEvaluateMapper;

    @Resource
    private IdentifyMapper identifyMapper;

    @Override
    public UserPro getInfo(Long id) {
        UserPro userPro = userProMapper.selectOneByKey(id);
        if (userPro != null) {
            userPro.setMaintainEvaluates(maintainEvaluateMapper.selectList(new QueryWrapper<MaintainEvaluate>().lambda().eq(MaintainEvaluate::getProId, userPro.getId()).eq(MaintainEvaluate::getBusinessType, Boolean.TRUE)));
            userPro.setIdentifyNum(identifyMapper.selectCount(new QueryWrapper<Identify>().lambda().eq(Identify::getUserProId, id)));
        }
        return userPro;
    }

    @Override
    public Page<UserPro> selectPageByParam(UserProParam param) {
        Page<UserPro> page = new Page<UserPro>(param.getPage(), param.getPageSize());
        List<UserPro> userProList = userProMapper.selectPageByParam(page, param);
        page.setRecords(userProList);
        return page;
    }
}
