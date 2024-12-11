package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.Goods;
import com.cn.peasantry.entity.GoodsClassify;
import com.cn.peasantry.mapper.GoodsClassifyMapper;
import com.cn.peasantry.service.GoodsClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.peasantry.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 */
@Service
public class GoodsClassifyServiceImpl extends ServiceImpl<GoodsClassifyMapper, GoodsClassify> implements GoodsClassifyService {

    @Override
    public PageResult pageQuery(int page, int limit, GoodsClassify data) {
        PageHelper.startPage(page, limit);
        List<GoodsClassify> queryList = baseMapper.pageQuery(data);
        PageInfo<GoodsClassify> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
