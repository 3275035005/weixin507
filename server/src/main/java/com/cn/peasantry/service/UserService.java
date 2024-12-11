package com.cn.peasantry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.peasantry.entity.User;
import com.cn.peasantry.utils.page.PageResult;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface UserService extends IService<User> {

    PageResult pageQuery(int page, int limit, User data);
}
