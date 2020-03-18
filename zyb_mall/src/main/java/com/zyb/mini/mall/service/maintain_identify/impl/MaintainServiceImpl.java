package com.zyb.mini.mall.service.maintain_identify.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.exception.ApiException;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.identify.MaintainListParam;
import com.zyb.mini.mall.service.maintain_identify.MaintainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 修复订单 服务实现类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
@Service
public class MaintainServiceImpl extends ServiceImpl<MaintainMapper, Maintain> implements MaintainService {

    @Resource
    private MaintainMapper maintainMapper;

    @Resource
    private MaintainCompanyImgMapper maintainCompanyImgMapper;

    @Resource
    private MaintainMsgMapper maintainMsgMapper;

    @Resource
    private MaintainImgMapper maintainImgMapper;

    @Resource
    private UserAddressMapper userAddressMapper;


    private List<String> objImg2StrList(List<MaintainCompanyImg> imgs) {
        if (imgs != null && imgs.size() > 0) {
            List<String> imgList = imgs.stream().map(MaintainCompanyImg::getImgUrl).collect(Collectors.toList());
            return imgList;

        }
        return null;
    }

    @Override
    public Maintain getInfo(Long id) {
        Maintain entity = maintainMapper.selectMaintainById(id);
        if (entity != null) {
            //修复前图片 用户提交图片
            List<MaintainImg> maintainImgs = maintainImgMapper.selectList(new QueryWrapper<MaintainImg>().lambda().eq(MaintainImg::getMaintainId, entity.getId()));
            if (maintainImgs != null && maintainImgs.size() > 0) {
                List<String> userImgs = maintainImgs.stream().map(MaintainImg::getImgUrl).collect(Collectors.toList());
                entity.setUserMaintainImgs(userImgs);
            }
            //预回复
            entity.setPreMaintainMsg(maintainMsgMapper.selectOne(new QueryWrapper<MaintainMsg>().lambda().eq(MaintainMsg::getMaintainId, entity.getId()).eq(MaintainMsg::getIsFixed, true).orderByDesc(MaintainMsg::getCreatedTime).last(" LIMIT 1")));
            //预回复图片
            if (entity.getPreMaintainMsg() != null) {
                List<MaintainCompanyImg> imgs = maintainCompanyImgMapper.selectList(new QueryWrapper<MaintainCompanyImg>().lambda().eq(MaintainCompanyImg::getMaintainMsgId, entity.getPreMaintainMsg().getId()));
                entity.setPreMaintainMsgImgs(objImg2StrList(imgs));
            }
            //非预回复
            entity.setMaintainMsg(maintainMsgMapper.selectOne(new QueryWrapper<MaintainMsg>().lambda().eq(MaintainMsg::getMaintainId, entity.getId()).eq(MaintainMsg::getIsFixed, false).orderByDesc(MaintainMsg::getCreatedTime).last(" LIMIT 1")));
            //非预回复图片
            if (entity.getMaintainMsg() != null) {
                List<MaintainCompanyImg> imgs = maintainCompanyImgMapper.selectList(new QueryWrapper<MaintainCompanyImg>().lambda().eq(MaintainCompanyImg::getMaintainMsgId, entity.getMaintainMsg().getId()));
                entity.setMaintainMsgImgs(objImg2StrList(imgs));
            }
//            MaintainPro maintainPro = maintainProMapper.selectById(entity.getMaintainProId());
//            if (maintainPro != null) {
//                entity.setMaintainPro(maintainPro.getRealname());
//            }
//            User user = userMapper.selectById(entity.getUserId());
//            if (user != null) {
//                entity.setUserName(user.getNickname());
//            }
//            AntiqueType antiqueType = antiqueTypeMapper.selectById(entity.getAntiqueTypeId());
//            if (antiqueType != null) {
//                entity.setAntiqueName(antiqueType.getTypeName());
//            }
            UserAddress userAddress = userAddressMapper.selectById(entity.getUserAddressId());
            if (userAddress != null) {
                entity.setUserAddress(userAddress.getAddressDetail());
            }

        }
        return entity;
    }

    @Override
    @Transactional
    public Long add(Maintain entity) {
        if (entity == null) {
            return null;
        }
        entity.setId(null);
        maintainMapper.insert(entity);
        if (entity.getId() == null) {
            throw new ApiException(Status.DATA_ADD_FAIL, "添加修复失败");
        }
//        if (entity.getId() != null && entity.getPreMaintainMsg() != null) {
//            for (int i = 0; i < entity.getUserMaintainImgs().size(); i++) {
//                entity.getUserMaintainImgs().get(i).setId(null);
//                entity.getUserMaintainImgs().get(i).setMaintainId(entity.getId());
//                maintainImgMapper.insert(entity.getUserMaintainImgs().get(i));
//            }
//        }
        if (!Strings.isNullOrEmpty(entity.getImgAryStr())) {
            String[] img = entity.getImgAryStr().split(",");
            for (String s : img) {
                MaintainImg maintainImg = new MaintainImg().setMaintainId(entity.getId()).setImgUrl(s.trim());
                maintainImgMapper.insert(maintainImg);
            }
        }
        return entity.getId();
    }

    @Override
    public Long updateByKey(Maintain entity) {
//        if (entity == null || entity.getId() == null) {
//            return null;
//        }
//        maintainMapper.updateById(entity);
//        if (entity.getId() != null && entity.getPreMaintainMsg() != null) {
//            maintainImgMapper.delete(new QueryWrapper<MaintainImg>().lambda().eq(MaintainImg::getMaintainId, entity.getId()));
//            for (int i = 0; i < entity.getUserMaintainImgs().size(); i++) {
//                entity.getUserMaintainImgs().get(i).setId(null);
//                entity.getUserMaintainImgs().get(i).setMaintainId(entity.getId());
//                maintainImgMapper.insert(entity.getUserMaintainImgs().get(i));
//            }
//        }
//        return entity.getId();
        return null;
    }


    @Override
    public Page<Maintain> selectPageByParam(MaintainListParam param) {
        if (param != null) {
            param.setCmMaintainAmount(null);
        }
        Page<Maintain> page = new Page<>(param.getPage(), param.getPageSize());
        page.setDesc(" a.created_time ");
        List<Maintain> maintainList = maintainMapper.queryMaintainOrder(page, param);
        if (maintainList != null) {
            for (Maintain maintain : maintainList) {
                LambdaQueryWrapper<MaintainMsg> qw = new QueryWrapper<MaintainMsg>().lambda()
                        .eq(MaintainMsg::getMaintainId, maintain.getId())
                        .orderByDesc(MaintainMsg::getCreatedTime)
                        .last(" LIMIT 1");
                MaintainMsg maintainMsg = maintainMsgMapper.selectOne(qw);
                if (maintainMsg != null) {
                    maintain.setPreMaintainMsgStr(maintainMsg.getCmMaintainMsg());
                }
            }
        }
        page.setRecords(maintainList);
        return page;
    }
}
