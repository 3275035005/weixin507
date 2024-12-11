package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.PostImage;
import com.cn.peasantry.mapper.PostImageMapper;
import com.cn.peasantry.service.PostImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子封面图 服务实现类
 * </p>
 */
@Service
public class PostImageServiceImpl extends ServiceImpl<PostImageMapper, PostImage> implements PostImageService {

}
