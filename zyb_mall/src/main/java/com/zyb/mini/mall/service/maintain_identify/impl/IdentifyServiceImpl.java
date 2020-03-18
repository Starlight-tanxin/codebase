package com.zyb.mini.mall.service.maintain_identify.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.exception.ApiException;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.identify.IdentifyParam;
import com.zyb.mini.mall.service.maintain_identify.IdentifyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * tzw 鉴赏记录
 */
@Service
public class IdentifyServiceImpl extends ServiceImpl<IdentifyMapper, Identify> implements IdentifyService {

    @Resource
    private IdentifyMapper identifyMapper;

    @Resource
    private IdentifyImgMapper identifyImgMapper;

    @Resource
    private MaintainEvaluateMapper maintainEvaluateMapper;

    @Resource
    private UserProMapper userProMapper;

    @Resource
    private UserMapper userMapper;


    @Resource
    private YearMapper yearMapper;


    @Override
    public Identify getInfo(Long id) {
        Identify identify = identifyMapper.selectById(id);
        if (identify != null) {
            identify.setIdentifyImgs(identifyImgMapper.selectList(new QueryWrapper<IdentifyImg>().lambda()
                    .eq(IdentifyImg::getIdentifyId, identify.getId())));
            identify.setMaintainEvaluate(maintainEvaluateMapper.selectOne(new QueryWrapper<MaintainEvaluate>().lambda().eq(MaintainEvaluate::getMaintainId, identify.getId()).eq(MaintainEvaluate::getBusinessType, Boolean.TRUE)));
            UserPro userPro = userProMapper.selectById(identify.getUserProId());
            if (userPro != null) {
                identify.setProName(userPro != null ? userPro.getRealname() : null);
            }
            User user = userMapper.selectById(identify.getUserId());
            if (user != null) {
                identify.setUserName(user.getNickname());
            }
            Year year = yearMapper.selectById(identify.getProYearId());
            if (year != null) {
                identify.setProYear(year.getYearName());
            }
        }
        return identify;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long add(Identify entity) {
        if (entity == null) {
            return null;
        }
        Long userProId = entity.getUserProId();
        UserPro userPro = userProMapper.selectById(userProId);
        if (userPro == null) {
            throw new ApiException(Status.PRO_NOT_EXIST);
        }
        entity.setAmount(userPro.getPrice());
        identifyMapper.insert(entity);
        if (entity.getId() == null) {
            throw new ApiException(Status.DATA_ADD_FAIL, "添加鉴赏失败");
        }
        if (!Strings.isNullOrEmpty(entity.getImgAryStr())) {
            String[] img = entity.getImgAryStr().split(",");
            for (String s : img) {
                if (!Strings.isNullOrEmpty(s)) {
                    IdentifyImg identifyImg = new IdentifyImg().setImgUrl(s.trim()).setIdentifyId(entity.getId());
                    identifyImgMapper.insert(identifyImg);
                }
            }
        }
//        if (entity.getIdentifyImgs() != null) {
//            for (int i = 0; i < entity.getIdentifyImgs().size(); i++) {
//                entity.getIdentifyImgs().get(i).setId(null);
//                entity.getIdentifyImgs().get(i).setIdentifyId(entity.getId());
//                identifyImgMapper.insert(entity.getIdentifyImgs().get(i));
//            }
//        }
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateByKey(Identify entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        }
        identifyMapper.updateById(entity);
        if (entity.getIdentifyImgs() != null) {
            identifyImgMapper.delete(new QueryWrapper<IdentifyImg>().lambda().eq(IdentifyImg::getIdentifyId, entity.getId()));
            for (int i = 0; i < entity.getIdentifyImgs().size(); i++) {
                entity.getIdentifyImgs().get(i).setId(null);
                entity.getIdentifyImgs().get(i).setIdentifyId(entity.getId());
                identifyImgMapper.insert(entity.getIdentifyImgs().get(i));
            }
        }
        return entity.getId();
    }

    @Override
    public Page<Identify> selectPageByParam(IdentifyParam param) {
        Page<Identify> page = new Page<>(param.getPage(), param.getPageSize());
        page.setDesc(" a.created_time ");
        page.setRecords(identifyMapper.selectPageByParam(page, param));
        return page;
    }
}
