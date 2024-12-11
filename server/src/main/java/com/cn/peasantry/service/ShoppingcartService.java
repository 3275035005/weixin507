package com.cn.peasantry.service;

import com.cn.peasantry.entity.Shoppingcart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 购物车 服务类
 * </p>
 */
public interface ShoppingcartService extends IService<Shoppingcart> {

    /**
     * 查询我的购物车
     * @param id 用户id
     * @return
     */
    List<Shoppingcart> getShoppingCart(String id);

    /**
     * 通过购物车id查询商品信息
     * @param ids
     * @return
     */
    List<Shoppingcart> getGoodsByIds(List<String> ids);

}
