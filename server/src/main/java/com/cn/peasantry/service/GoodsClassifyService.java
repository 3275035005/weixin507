package com.cn.peasantry.service;

import com.cn.peasantry.entity.GoodsClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.peasantry.utils.page.PageResult;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 */
public interface GoodsClassifyService extends IService<GoodsClassify> {

    PageResult pageQuery(int page, int limit, GoodsClassify data);
}
