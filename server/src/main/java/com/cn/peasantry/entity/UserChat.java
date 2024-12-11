package com.cn.peasantry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天记录表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserChat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 发送方用户id
     */
    private String sendUserId;

    /**
     * 接收方用户id
     */
    private String receiveUserId;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 接收方用户昵称
     */
    @TableField(exist = false)
    private String receiveUserName;

    /**
     * 接收方用户头像
     */
    @TableField(exist = false)
    private String receiveAvatar;

    /**
     * 发送方用户头像
     */
    @TableField(exist = false)
    private String sendAvatar;
}
