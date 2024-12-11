package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 帖子信息 Mapper 接口
 * </p>
 */
public interface PostMapper extends BaseMapper<Post> {

    List<Post> pageQuery(Post data);

    List<Post> getPostList(Post post);

    Post getPostAndUserById(@Param("id") String id,@Param("userId")  String userId);

    List<Post> getPostCollectByUserId(@Param("userId")String userId);

    List<Post> getPostPraiseByUserId(@Param("userId")String userId);

}
