package com.cn.peasantry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.peasantry.entity.Order;
import com.cn.peasantry.utils.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息订单 服务类
 * </p>
 */
public interface OrderService extends IService<Order> {

    PageResult pageQuery(int page, int limit, Order data);

    List<Order> getOrderInfoList(String userId, String state);

    /**
     * 统计柱状图
     * @param date
     * @return
     */
    List<Map<String, Object>> getOrderHistogram(String date);

}
