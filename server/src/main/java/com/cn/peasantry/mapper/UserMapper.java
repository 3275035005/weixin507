package com.cn.peasantry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.peasantry.entity.User;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> pageQuery(User data);
}
