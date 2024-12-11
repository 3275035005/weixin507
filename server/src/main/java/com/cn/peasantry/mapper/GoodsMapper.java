package com.cn.peasantry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.peasantry.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<Goods> pageQuery(Goods data);

    List<Goods> getGoodsList(Goods data);

    Goods getGoodsAndUserById(@Param("id") String id);
}
