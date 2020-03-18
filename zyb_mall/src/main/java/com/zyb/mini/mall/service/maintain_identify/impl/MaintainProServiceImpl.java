package com.zyb.mini.mall.service.maintain_identify.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.user.UserProParam;
import com.zyb.mini.mall.service.maintain_identify.MaintainProService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 修复得专家表 服务实现类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
@Service
public class MaintainProServiceImpl extends ServiceImpl<MaintainProMapper, MaintainPro> implements MaintainProService {

    @Resource
    private MaintainProMapper maintainProMapper;

    @Resource
    private MaintainEvaluateMapper maintainEvaluateMapper;

    @Resource
    private MaintainCompanyImgMapper maintainCompanyImgMapper;

    @Resource
    private MaintainImgMapper maintainImgMapper;

    @Resource
    private MaintainMapper maintainMapper;

    @Resource
    private MaintainMsgMapper maintainMsgMapper;

    @Override
    public MaintainPro getInfo(Long id) {
        MaintainPro maintainPro = maintainProMapper.selectOneById(id);
        // 好评数
        if (maintainPro != null) {
            maintainPro.setEvaTypeCount(maintainEvaluateMapper.selectCount(new QueryWrapper<MaintainEvaluate>().lambda().eq(MaintainEvaluate::getBusinessType, false).eq(MaintainEvaluate::getEvaType, 1).eq(MaintainEvaluate::getProId, maintainPro.getId())));
        }
        //取最新的修复订单 取订单里 修复前 修复后的图片
        Maintain maintain = maintainMapper.selectOne(new QueryWrapper<Maintain>().lambda().eq(Maintain::getMaintainProId, maintainPro.getId()).orderByDesc(Maintain::getCreatedTime).last(" LIMIT 1"));
        if (maintain != null) {
            // 查 非预回复与 修复前用户提交的图片
            //先查msg 再取图片 非预回复
            LambdaQueryWrapper<MaintainMsg> qw = new QueryWrapper<MaintainMsg>().lambda().eq(MaintainMsg::getIsFixed, 0).eq(MaintainMsg::getMaintainId, maintain.getId()).last(" LIMIT 1");
            MaintainMsg maintainMsg = maintainMsgMapper.selectOne(qw);
            if (maintainMsg == null) {
                // 预回复
                qw.eq(MaintainMsg::getIsFixed, 1);
                maintainMsg = maintainMsgMapper.selectOne(qw);
            }
            if (maintainMsg != null) {
                List<MaintainCompanyImg> imgObjList = maintainCompanyImgMapper.selectList(new QueryWrapper<MaintainCompanyImg>().lambda().eq(MaintainCompanyImg::getMaintainMsgId, maintainMsg.getId()));
                if (imgObjList != null && imgObjList.size() > 0) {
                    List<String> imgList = imgObjList.stream().map(MaintainCompanyImg::getImgUrl).collect(Collectors.toList());
                    maintainPro.setMaintainCompanyStrImgAry(imgList);
                }
            }
            //用户提交的修复前照片
            List<MaintainImg> maintainImgList = maintainImgMapper.selectList(new QueryWrapper<MaintainImg>().lambda().eq(MaintainImg::getMaintainId, maintain.getId()));
            if (maintainImgList != null && maintainImgList.size() > 0) {
                List<String> imgList = maintainImgList.stream().map(MaintainImg::getImgUrl).collect(Collectors.toList());
                maintainPro.setMaintainStrImgAry(imgList);
            }
        }
        return maintainPro;
    }

    @Override
    public Page<MaintainPro> selectPageByParam(UserProParam param) {
        Page<MaintainPro> page = new Page<>(param.getPage(), param.getPageSize());
        List<MaintainPro> list = maintainProMapper.selectPageByParam(page, param);
        page.setRecords(list);
        return page;
    }
}
