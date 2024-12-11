package com.cn.peasantry.controller;


import com.cn.peasantry.entity.Post;
import com.cn.peasantry.service.PostService;
import com.cn.peasantry.utils.page.PageResult;
import com.cn.peasantry.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 帖子信息 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/post")
public class PostController {


    @Autowired
    private PostService service;

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
            @RequestBody Post data){
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
}

