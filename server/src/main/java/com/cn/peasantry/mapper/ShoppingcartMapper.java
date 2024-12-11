package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.Shoppingcart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车 Mapper 接口
 * </p>
 */
public interface ShoppingcartMapper extends BaseMapper<Shoppingcart> {

    List<Shoppingcart> getShoppingCart(@Param("id") String id);

    List<Shoppingcart> getGoodsByIds(@Param("ids")List<String> ids);
}
