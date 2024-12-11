package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.User;
import com.cn.peasantry.mapper.UserMapper;
import com.cn.peasantry.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.peasantry.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageResult pageQuery(int page, int limit, User data) {
        PageHelper.startPage(page, limit);
        List<User> queryList = baseMapper.pageQuery(data);
        PageInfo<User> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }
}
