package com.cn.peasantry.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cn.peasantry.entity.Order;
import com.cn.peasantry.service.OrderService;
import com.cn.peasantry.utils.page.PageResult;
import com.cn.peasantry.utils.response.R;
import com.cn.peasantry.utils.utils.AceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息订单 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService service;

    /**
     * 分页条件查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @param data 封装查询条件数据
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody Order data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        service.removeById(id);
        return R.ok();
    }

    /**
     * 发货操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Order data){

        UpdateWrapper<Order> uw = new UpdateWrapper<>();
        uw.eq("id", data.getId());
        service.update(data, uw);
        return R.ok();
    }



}

