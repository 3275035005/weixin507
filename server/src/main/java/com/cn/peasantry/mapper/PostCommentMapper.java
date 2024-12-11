package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.PostComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 帖子评论 Mapper 接口
 * </p>
 */
public interface PostCommentMapper extends BaseMapper<PostComment> {

    List<PostComment> getComment(@Param("id") String id);

    PostComment getCommentById(@Param("id")String id);
}
