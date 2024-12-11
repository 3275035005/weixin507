package com.cn.peasantry.service.impl;

import com.cn.peasantry.entity.Order;
import com.cn.peasantry.mapper.OrderMapper;
import com.cn.peasantry.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.peasantry.utils.page.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息订单 服务实现类
 * </p>
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public PageResult pageQuery(int page, int limit, Order data) {
        PageHelper.startPage(page, limit);
        List<Order> queryList = baseMapper.pageQuery(data);
        PageInfo<Order> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<Order> getOrderInfoList(String userId, String state) {
        return baseMapper.getOrderInfoList(userId, state);
    }

    @Override
    public List<Map<String, Object>> getOrderHistogram(String date) {
        return baseMapper.getOrderHistogram(date);
    }
}
