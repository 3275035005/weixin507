package com.cn.peasantry.service;

import com.cn.peasantry.entity.Goods;
import com.cn.peasantry.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.peasantry.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 帖子信息 服务类
 * </p>
 */
public interface PostService extends IService<Post> {

    PageResult pageQuery(int page, int limit, Post data);

    List<Post> getPostList(Post post);

    Post getPostAndUserById(String id, String userId);

    /**
     * 通过用户id查询我的收藏的帖子
     * @param userId
     * @return
     */
    List<Post> getPostCollectByUserId(String userId);

    /**
     *  通过用户id查询我的点赞的帖子
     * @param userId
     * @return
     */
    List<Post> getPostPraiseByUserId(String userId);
}
