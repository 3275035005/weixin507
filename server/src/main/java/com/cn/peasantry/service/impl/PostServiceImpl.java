package com.cn.peasantry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.peasantry.entity.Post;
import com.cn.peasantry.entity.Post;
import com.cn.peasantry.entity.PostCollect;
import com.cn.peasantry.entity.PostPraise;
import com.cn.peasantry.mapper.PostCollectMapper;
import com.cn.peasantry.mapper.PostMapper;
import com.cn.peasantry.mapper.PostPraiseMapper;
import com.cn.peasantry.service.PostCollectService;
import com.cn.peasantry.service.PostPraiseService;
import com.cn.peasantry.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.peasantry.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 帖子信息 服务实现类
 * </p>
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Resource
    private PostCollectMapper postCollectMapper;

    @Resource
    private PostPraiseMapper postPraiseMapper;

    @Override
    public PageResult pageQuery(int page, int limit, Post data) {
        PageHelper.startPage(page, limit);
        List<Post> queryList = baseMapper.pageQuery(data);
        PageInfo<Post> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Post> getPostList(Post post) {
        List<Post> postList = baseMapper.getPostList(post);
        for (Post post1 : postList) {
            Integer collectNum = postCollectMapper.selectCount(new QueryWrapper<PostCollect>()
                    .eq("post_id", post1.getId()));
            post1.setCollectNum(collectNum);
        }

        return postList;
    }

    @Override
    public Post getPostAndUserById(String id, String userId) {
        Post post = baseMapper.getPostAndUserById(id, userId);
        Integer collectNum = postCollectMapper.selectCount(new QueryWrapper<PostCollect>()
                .eq("post_id", id));
        post.setCollectNum(collectNum);

        Integer praiseNum = postPraiseMapper.selectCount(new QueryWrapper<PostPraise>()
                .eq("post_id", id));
        post.setPraiseNum(praiseNum);

        return post;
    }

    @Override
    public List<Post> getPostCollectByUserId(String userId) {
        List<Post> postList = baseMapper.getPostCollectByUserId(userId);
        for (Post post1 : postList) {
            Integer collectNum = postCollectMapper.selectCount(new QueryWrapper<PostCollect>()
                    .eq("post_id", post1.getId()));
            post1.setCollectNum(collectNum);
        }
        return postList;
    }

    @Override
    public List<Post> getPostPraiseByUserId(String userId) {
        List<Post> postList = baseMapper.getPostPraiseByUserId(userId);
        for (Post post1 : postList) {
            Integer collectNum = postCollectMapper.selectCount(new QueryWrapper<PostCollect>()
                    .eq("post_id", post1.getId()));
            post1.setCollectNum(collectNum);
        }
        return postList;
    }
}
