package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.UserChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 聊天记录表 Mapper 接口
 * </p>
 */
public interface UserChatMapper extends BaseMapper<UserChat> {

    List<UserChat> getUserChatList(@Param("userId") String userId);

    List<UserChat> getUserChatBySendUserAndReceiveUser(@Param("sendUserId") String sendUserId,@Param("receiveUserId") String receiveUserId);

}
