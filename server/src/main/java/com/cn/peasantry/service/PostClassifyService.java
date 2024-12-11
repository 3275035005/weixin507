package com.cn.peasantry.service;

import com.cn.peasantry.entity.PostClassify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.peasantry.utils.page.PageResult;

/**
 * <p>
 * 帖子分类 服务类
 * </p>
 */
public interface PostClassifyService extends IService<PostClassify> {

    PageResult pageQuery(int page, int limit, PostClassify data);

}
