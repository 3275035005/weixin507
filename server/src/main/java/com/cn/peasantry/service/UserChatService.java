package com.cn.peasantry.service;

import com.cn.peasantry.entity.UserChat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 聊天记录表 服务类
 * </p>
 */
public interface UserChatService extends IService<UserChat> {

    /**
     * 通过用户id查询聊天记录列表
     * @param userId
     * @return
     */
    List<UserChat> getUserChatList(String userId);

    /**
     * 通过发送方用户id和接收方用户id，查询聊天窗口聊天记录
     * @param sendUserId
     * @param receiveUserId
     * @return
     */
    List<UserChat> getUserChatBySendUserAndReceiveUser(String sendUserId, String receiveUserId);
}
