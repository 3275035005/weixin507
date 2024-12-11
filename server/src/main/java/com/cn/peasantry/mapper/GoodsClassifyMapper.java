package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.GoodsClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品分类 Mapper 接口
 * </p>
 */
public interface GoodsClassifyMapper extends BaseMapper<GoodsClassify> {

    List<GoodsClassify> pageQuery(GoodsClassify data);

}
