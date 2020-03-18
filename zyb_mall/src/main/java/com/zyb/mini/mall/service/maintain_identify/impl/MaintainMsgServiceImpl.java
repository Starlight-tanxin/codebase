package com.zyb.mini.mall.service.maintain_identify.impl;

import com.zyb.mini.mall.dao.MaintainCompanyImgMapper;
import com.zyb.mini.mall.pojo.entity.MaintainCompanyImg;
import com.zyb.mini.mall.pojo.entity.MaintainMsg;
import com.zyb.mini.mall.dao.MaintainMsgMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.service.maintain_identify.MaintainMsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tanxin
 * @since 2019-11-03
 */
@Service
public class MaintainMsgServiceImpl extends ServiceImpl<MaintainMsgMapper, MaintainMsg> implements MaintainMsgService {

    @Resource
     private MaintainMsgMapper maintainMsgMapper;

    @Resource
    private MaintainCompanyImgMapper maintainCompanyImgMapper;

    @Override
    public Integer add(MaintainMsg maintainMsg) {
        if (maintainMsg == null ||maintainMsg.getIsFixed() == null) {
            return null;
        }
        maintainMsg.setCreatedTime(LocalDateTime.now());
        maintainMsgMapper.insert(maintainMsg);
        if(maintainMsg.getMaintainCompanyImgs()!=null){
            for (int i = 0; i <maintainMsg.getMaintainCompanyImgs().size() ; i++) {
                maintainMsg.getMaintainCompanyImgs().get(i).setId(null);
                maintainMsg.getMaintainCompanyImgs().get(i).setMaintainMsgId(maintainMsg.getId());
                maintainCompanyImgMapper.insert( maintainMsg.getMaintainCompanyImgs().get(i));
            }
        }
        return maintainMsg.getId();
    }
}
