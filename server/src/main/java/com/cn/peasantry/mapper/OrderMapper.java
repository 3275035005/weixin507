package com.cn.peasantry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.peasantry.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息订单 Mapper 接口
 * </p>
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> pageQuery(Order data);


    List<Order> getOrderInfoList(@Param("userId") String userId,@Param("state") String state);

    List<Map<String, Object>> getOrderHistogram(@Param("date") String date);

}
