package com.cn.peasantry.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 帖子信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布用户id
     */
    private String userId;

    /**
     * 浏览量
     */
    private Integer viewNum;



    /**
     * 分类id
     */
    private String classifyId;


    /**
     * 发布时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String collectId;

    @TableField(exist = false)
    private String praiseId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private String classifyName;

    @TableField(exist = false)
    private List<String> imageList;

    /**
     * 收藏数量
     */
    @TableField(exist = false)
    private Integer collectNum;

    /**
     * 点赞数量
     */
    @TableField(exist = false)
    private Integer praiseNum;

}
