package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.Shoppingcart;
import com.cn.peasantry.mapper.ShoppingcartMapper;
import com.cn.peasantry.service.ShoppingcartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车 服务实现类
 * </p>
 */
@Service
public class ShoppingcartServiceImpl extends ServiceImpl<ShoppingcartMapper, Shoppingcart> implements ShoppingcartService {

    @Override
    public List<Shoppingcart> getShoppingCart(String id) {
        return baseMapper.getShoppingCart(id);
    }

    @Override
    public List<Shoppingcart> getGoodsByIds(List<String> ids) {
        return baseMapper.getGoodsByIds(ids);
    }
}
