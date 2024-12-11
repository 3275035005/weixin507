package com.cn.peasantry.service;

import com.cn.peasantry.entity.PostComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 帖子评论 服务类
 * </p>
 */
public interface PostCommentService extends IService<PostComment> {

    /**
     * 通过帖子id查询帖子详情评论内容
     * @param id
     * @return
     */
    List<PostComment> getComment(String id);

    /**
     * 通过评论id查询记录
     * @param id
     * @return
     */
    PostComment getCommentById(String id);

}
