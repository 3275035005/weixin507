package com.cn.peasantry.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 销量
     */
    private Integer sales;
    /**
     * 商品介绍
     */
    private String content;

    /**
     * 发布用户id
     */
    private String userId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal old;

    /**
     * 物品封面
     */
    private String image;

    /**
     * 库存
     */
    private Integer num;

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
    private String classifyName;

    /**
     * 发布用户
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String avatar;


}
