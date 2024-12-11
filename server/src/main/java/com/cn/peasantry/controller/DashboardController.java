package com.cn.peasantry.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.peasantry.entity.Order;
import com.cn.peasantry.service.GoodsService;
import com.cn.peasantry.service.OrderService;
import com.cn.peasantry.service.PostService;
import com.cn.peasantry.service.UserService;
import com.cn.peasantry.utils.response.R;
import com.cn.peasantry.utils.utils.AceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页 -> 统计
 * </p>
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private PostService postService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    /**
     * 统计首页数据
     *      1、用户数量
     *      2、帖子数量
     *      3、商品数量
     *      4、订单数量
     * @return
     */
    @GetMapping("getInfo")
    public R getInfo(){

        int orderNumber = orderService.count(null);
        int postNumber = postService.count(null);
        int goodsNumber = goodsService.count(null);
        int userNumber = userService.count(null);

        return R.ok()
                .data("orderNumber", orderNumber)
                .data("postNumber", postNumber)
                .data("goodsNumber", goodsNumber)
                .data("userNumber",userNumber);
    }


    /**
     * 统计订单柱状图
     * @return
     */
    @GetMapping("getHistogram")
    public R getHistogram(String date){
        String userId = "";
        //1、充电订单柱状图统计
        List<String> day = new ArrayList<>();
        List<Integer> number = new ArrayList<>();
        int days = AceUtils.getDaysInMonth();
        for(int i=1;i<=days;i++){
            day.add(i+"号");
            number.add(0);
        }
        List<Map<String, Object>> histogram = orderService.getOrderHistogram(date);
        for (Map<String, Object> map : histogram) {
            Object startChargingTime = map.get("date");
            int dateOfDay = AceUtils.getDateOfDay((String) startChargingTime);
            number.add(dateOfDay-1,Integer.parseInt(map.get("number").toString()) );
        }
        return R.ok()
                .data("day",day)
                .data("number",number);
    }
}
