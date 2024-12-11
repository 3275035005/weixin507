package com.cn.peasantry.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.peasantry.entity.GoodsClassify;
import com.cn.peasantry.service.GoodsClassifyService;
import com.cn.peasantry.utils.page.PageResult;
import com.cn.peasantry.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/goods-classify")
public class GoodsClassifyController {

    @Autowired
    private GoodsClassifyService service;

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
            @RequestBody GoodsClassify data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改数据
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody GoodsClassify data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody GoodsClassify data){
        service.save(data);
        return R.ok();
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
     * 查询所有分类
     * @return
     */
    @GetMapping("getGoodsClassifyAll")
    public R getGoodsClassifyAll(){
        List<GoodsClassify> list = service.list(new QueryWrapper<GoodsClassify>()
                .orderByAsc("sort"));

        return R.ok().data("list", list);
    }
}

