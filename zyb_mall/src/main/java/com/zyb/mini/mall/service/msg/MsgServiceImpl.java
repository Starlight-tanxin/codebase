package com.zyb.mini.mall.service.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.pojo.dto.MsgSessionDTO;
import com.zyb.mini.mall.pojo.dto.UserMsgDTO;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.redis.RedisUtils;
import com.zyb.mini.mall.utils.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 消息
 *
 * @author: Tx
 * @date: 2019/11/13
 */
@Service
public class MsgServiceImpl {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 获取用户的消息列表
     *
     * @param userId 用户id
     * @return
     */
    public List<Object> getMsgSessionList(Long userId) {
        List<Object> sessionList = RedisUtils.lrange(redisTemplate, RedisKeyNameConstant.MSG_SESSION_ID, 0, -1);
        if (sessionList == null) {
            return new ArrayList<>();
        }
        List<Object> list = new ArrayList<>();
        for (Object o : sessionList) {
            MsgSessionDTO dto = (MsgSessionDTO) o;
            if (dto.getUserId().equals(userId)) {
                list.add(o);
            }
        }
        return list;
    }

    /**
     * 获取一个会话列表里面的消息详情 （前后端通用）
     *
     * @param userId    用户id
     * @param sessionId 会话消息id
     * @return
     */
    public List<UserMsgDTO> getMsgList(Long userId, String sessionId) {
        String key = RedisKeyNameConstant.MSG_LIST_BY_SESSION + sessionId;
        String msgListStr = RedisUtils.getString(redisTemplate, key);
        if (Strings.isNullOrEmpty(msgListStr)) {
            return new ArrayList<>();
        }
        List<UserMsgDTO> list = JSONObject.parseArray(msgListStr, UserMsgDTO.class);
        return list;
    }

    /**
     * 用户发起一个会话
     *
     * @param user 用户
     * @param msg  消息内容
     * @return
     */
    public List<UserMsgDTO> addMsgSession(User user, String msg) {
        String sessionId = UUID.absolutelyUniqueId();
        Long time = System.currentTimeMillis();
        MsgSessionDTO msgSessionDTO = new MsgSessionDTO()
                .setSessionId(sessionId)
                .setNickname(user.getNickname())
                .setHeadImg(user.getHeadImg())
                .setUserId(user.getId());
        RedisUtils.rpush(redisTemplate, RedisKeyNameConstant.MSG_SESSION_ID, msgSessionDTO, CommonConstant.MSG_TIME_OUT);
        String key = RedisKeyNameConstant.MSG_LIST_BY_SESSION + sessionId;
        UserMsgDTO userMsgDTO = new UserMsgDTO()
                .setUserId(user.getId())
                .setHeadImg(user.getHeadImg())
                .setNickname(user.getNickname())
                .setIsCompany(false)
                .setSessionId(sessionId)
                .setTime(time)
                .setMsg(msg);
        List<UserMsgDTO> msgList = Arrays.asList(userMsgDTO);
        RedisUtils.set(redisTemplate, key, JSON.toJSONString(msgList), CommonConstant.MSG_TIME_OUT);
        return msgList;
    }


    /**
     * 用户发送一条新的消息
     *
     * @param user      用户
     * @param sessionId 消息会话的id
     * @param msg       消息内容
     * @return
     */
    public List<UserMsgDTO> sendMsgSession(User user, String sessionId, String msg) {
        //User user = userService.getById(userId);
        Long time = System.currentTimeMillis();
        UserMsgDTO userMsgDTO = new UserMsgDTO()
                .setUserId(user.getId())
                .setHeadImg(user.getHeadImg())
                .setNickname(user.getNickname())
                .setIsCompany(false)
                .setSessionId(sessionId)
                .setTime(time)
                .setMsg(msg);
        List<UserMsgDTO> msgList = getMsgList(user.getId(), sessionId);
        msgList.add(userMsgDTO);
        String key = RedisKeyNameConstant.MSG_LIST_BY_SESSION + sessionId;
        RedisUtils.set(redisTemplate, key, JSON.toJSONString(msgList), CommonConstant.MSG_TIME_OUT);
        return msgList;
    }

    /**
     * 获取用户的消息列表 (后台)
     *
     * @return
     */
    public List<MsgSessionDTO> getMsgSessionListBack() {
        List<Object> sessionList = RedisUtils.lrange(redisTemplate, RedisKeyNameConstant.MSG_SESSION_ID, 0, -1);
        if (sessionList == null) {
            return new ArrayList<>();
        }
        List<MsgSessionDTO> sessionDTOList = new ArrayList<>();
        sessionList.forEach(o -> {
            MsgSessionDTO dto = (MsgSessionDTO) o;
            sessionDTOList.add(dto);
        });
        return sessionDTOList;
    }


    /**
     * 用户发送一条新的消息  （后台）
     *
     * @param sessionId 消息会话的id
     * @param msg       消息内容
     * @return
     */
    public List<UserMsgDTO> sendMsgSessionBack(String sessionId, String msg) {
        Long time = System.currentTimeMillis();
        UserMsgDTO userMsgDTO = new UserMsgDTO()
                .setUserId(0L)
                .setHeadImg(CommonConstant.COMPANY_IMG)
                .setNickname("客服人员")
                .setIsCompany(true)
                .setSessionId(sessionId)
                .setTime(time)
                .setMsg(msg);
        List<UserMsgDTO> msgList = getMsgList(0L, sessionId);
        msgList.add(userMsgDTO);
        String key = RedisKeyNameConstant.MSG_LIST_BY_SESSION + sessionId;
        RedisUtils.set(redisTemplate, key, JSON.toJSONString(msgList), CommonConstant.MSG_TIME_OUT);
        return msgList;
    }

}
