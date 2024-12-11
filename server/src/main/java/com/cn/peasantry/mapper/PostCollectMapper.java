package com.cn.peasantry.mapper;

import com.cn.peasantry.entity.Post;
import com.cn.peasantry.entity.PostCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 帖子收藏 Mapper 接口
 * </p>
 */
public interface PostCollectMapper extends BaseMapper<PostCollect> {

    List<Post> getPostCollectByUserId(@Param("userId") String userId);

}
