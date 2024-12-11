package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.PostComment;
import com.cn.peasantry.mapper.PostCommentMapper;
import com.cn.peasantry.service.PostCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 帖子评论 服务实现类
 * </p>
 */
@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment> implements PostCommentService {

    @Override
    public List<PostComment> getComment(String id) {
        List<PostComment> comment = baseMapper.getComment(id);
        for (PostComment postComment : comment) {
            postComment.setContent(EmojiParser.parseToUnicode(postComment.getContent()));
        }

        return comment;
    }

    @Override
    public PostComment getCommentById(String id) {
        return baseMapper.getCommentById(id);
    }
}
