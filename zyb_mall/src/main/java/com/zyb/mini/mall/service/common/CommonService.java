package com.zyb.mini.mall.service.common;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.dao.*;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.vo.city.ProvinceVO;
import com.zyb.mini.mall.pojo.vo.common.DownBoxVO;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Tx
 * @date: 2019/10/26
 */
@Slf4j
@Component
public class CommonService {

    @Resource
    private RegionMapper regionMapper;

    @Resource
    private AntiqueTypeMapper antiqueTypeMapper;

    @Resource
    private YearMapper yearMapper;

    @Resource
    private PhaseTypeMapper phaseTypeMapper;

    @Resource
    private BookTypeMapper bookTypeMapper;

    @Resource
    private BookOtherTypeMapper bookOtherTypeMapper;


    @Resource
    private ExpressCompanyMapper expressCompanyMapper;

    @Resource
    private UserMapper userMapper;

    //    @Autowired
//    private StringRedisTemplate redis;
    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 更新用户缓存
     *
     * @param userId 用户id
     */
    public void updateUserCache(Long userId) {
        User user = userMapper.selectById(userId);
        // redis.opsForValue().set(RedisKeyNameConstant.REDIS_USER_KEY_PREFIX + user, JSON.toJSONString(user));
        RedisUtils.set(redisTemplate, RedisKeyNameConstant.REDIS_USER_KEY_PREFIX + user, JSON.toJSONString(user));
        log.info("更新用户缓存完成 ---->  更新完成的时间 : {} ", System.currentTimeMillis());
    }

    /**
     * 省市区
     *
     * @return
     */
    @Cacheable(cacheNames = "selectProvinceAndCityAndRegion")
    public List<ProvinceVO> selectProvinceAndCityAndRegion() {
        return regionMapper.selectProvinceAndCityAndRegion();
    }

    /**
     * 搜索古董类别
     *
     * @return
     */
    @Cacheable(cacheNames = "selectAntiqueType")
    public List<DownBoxVO> selectAntiqueTypeBox() {
        return antiqueTypeMapper.selectDownBox();
    }

    /**
     * 搜索古年代类别
     *
     * @return
     */
    @Cacheable(cacheNames = "selectYearTypeBox")
    public List<DownBoxVO> selectYearTypeBox() {
        return yearMapper.selectDownBox();
    }


    /**
     * 品相类别
     *
     * @return
     */
    @Cacheable(cacheNames = "selectPhaseTypeBox")
    public List<DownBoxVO> selectPhaseTypeBox() {
        return phaseTypeMapper.selectDownBox();
    }


    /**
     * 书籍类别
     *
     * @return
     */
    @Cacheable(cacheNames = "selectBookTypeBox")
    public List<DownBoxVO> selectBookTypeBox() {
        return bookTypeMapper.selectDownBox();
    }

    /**
     * 书籍其他类别
     *
     * @return
     */
    @Cacheable(cacheNames = "selectBookOtherTypeBox")
    public List<DownBoxVO> selectBookOtherTypeBox() {
        return bookOtherTypeMapper.selectDownBox();
    }


    @Cacheable(cacheNames = "selectExpressTypeBox")
    public List<DownBoxVO> selectExpressTypeBox() {
        return expressCompanyMapper.selectDownBox();
    }
}
