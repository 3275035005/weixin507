package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.PostClassify;
import com.cn.peasantry.entity.PostClassify;
import com.cn.peasantry.mapper.PostClassifyMapper;
import com.cn.peasantry.service.PostClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.peasantry.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 帖子分类 服务实现类
 * </p>
 */
@Service
public class PostClassifyServiceImpl extends ServiceImpl<PostClassifyMapper, PostClassify> implements PostClassifyService {

    @Override
    public PageResult pageQuery(int page, int limit, PostClassify data) {
        PageHelper.startPage(page, limit);
        List<PostClassify> queryList = baseMapper.pageQuery(data);
        PageInfo<PostClassify> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
