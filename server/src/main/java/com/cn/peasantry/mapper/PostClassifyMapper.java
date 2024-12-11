package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.PostClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 帖子分类 Mapper 接口
 * </p>
 */
public interface PostClassifyMapper extends BaseMapper<PostClassify> {

    List<PostClassify> pageQuery(PostClassify data);

}
