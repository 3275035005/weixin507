package com.cn.peasantry.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购物车
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Shoppingcart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 加入数量
     */
    private Integer num;


    /**
     * 选中状态：默认1
     */
    @TableField(exist = false)
    private Integer checked = 1;


    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 商品单价
     */
    @TableField(exist = false)
    private String price;


    /**
     * 商品封面
     */
    @TableField(exist = false)
    private String image;

}
