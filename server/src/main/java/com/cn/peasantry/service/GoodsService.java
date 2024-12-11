package com.cn.peasantry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.peasantry.entity.Goods;
import com.cn.peasantry.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 */
public interface GoodsService extends IService<Goods> {

    PageResult pageQuery(int page, int limit, Goods data);

    List<Goods> getGoodsList(Goods data);

    Goods getGoodsAndUserById(String id);
}
