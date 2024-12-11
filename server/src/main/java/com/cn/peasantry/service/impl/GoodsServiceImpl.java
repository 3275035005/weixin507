package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.Goods;
import com.cn.peasantry.mapper.GoodsMapper;
import com.cn.peasantry.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.peasantry.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public PageResult pageQuery(int page, int limit, Goods data) {
        PageHelper.startPage(page, limit);
        List<Goods> queryList = baseMapper.pageQuery(data);
        PageInfo<Goods> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Goods> getGoodsList(Goods data) {
        return baseMapper.getGoodsList(data);
    }

    @Override
    public Goods getGoodsAndUserById(String id) {
        return baseMapper.getGoodsAndUserById(id);
    }

}
