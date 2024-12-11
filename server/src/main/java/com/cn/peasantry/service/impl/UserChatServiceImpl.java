package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.UserChat;
import com.cn.peasantry.mapper.UserChatMapper;
import com.cn.peasantry.service.UserChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 聊天记录表 服务实现类
 * </p>
 */
@Service
public class UserChatServiceImpl extends ServiceImpl<UserChatMapper, UserChat> implements UserChatService {

    @Override
    public List<UserChat> getUserChatList(String userId) {
        return baseMapper.getUserChatList(userId);
    }

    @Override
    public List<UserChat> getUserChatBySendUserAndReceiveUser(String sendUserId, String receiveUserId) {
        return baseMapper.getUserChatBySendUserAndReceiveUser(sendUserId, receiveUserId);
    }
}
