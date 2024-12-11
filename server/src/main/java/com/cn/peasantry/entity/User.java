package com.cn.peasantry.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`user`")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(MD5加密)
     */
    private String password;

    /**
     * 账号状态（1正常，2禁用）
     */
    private String status;

    /**
     * 昵称
     */
    private String name;

    /**
     * 简介
     */
    private String content;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 权限（1代表管理员，2 代表农民）
     */
    private String userType;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别 (0女·， 1男)
     */
    private String sex;

    /**
     * 所在地区
     */
    private String address;

    /**
     * 是否为商家 (0否 1是)
     */
    private String flag;

    /**
     * 背景图
     */
    private String image;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 接收原密码
     */
    @TableField(exist = false)
    private String oldPassword;

    @TableField(exist = false)
    private String[] roles;


}
