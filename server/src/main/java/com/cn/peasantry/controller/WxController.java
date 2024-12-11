package com.cn.peasantry.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.peasantry.entity.*;
import com.cn.peasantry.service.*;
import com.cn.peasantry.utils.response.R;
import com.cn.peasantry.utils.utils.AceUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  对小程序提供后端接口
 * </p>
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private UserService sysUserService;


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsClassifyService goodsClassifyService;

    @Autowired
    private OrderService orderService;


    @Autowired
    private PostService postService;

    @Autowired
    private PostImageService postImageService;

    @Autowired
    private PostCollectService postCollectService;

    @Autowired
    private PostClassifyService postClassifyService;

    @Autowired
    private PostCommentService postCommentService;

    @Autowired
    private UserChatService userChatService;

    @Autowired
    private PostPraiseService postPraiseService;


    @Autowired
    private ShoppingcartService shoppingcartService;


    /**
     * 登录功能
     */
    @PostMapping("login")
    public R login(@RequestBody User data){
        QueryWrapper<User> qw = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = qw.lambda();
        lambda.eq(User::getUsername, data.getUsername());
        User pUser = sysUserService.getOne(qw);
        if(pUser == null){
            return R.error("账号不存在");
        }
        String string2MD5Password = AceUtils.string2MD5(data.getPassword());
        // 判断密码
        if(!string2MD5Password.equals(pUser.getPassword())){
            return R.error("密码不正确");
        }
        if(!"2".equals(pUser.getUserType())){
            return R.error("请登录用户账号");
        }
        String status = pUser.getStatus();

        // 判断账号状态
        if(!"1".equals(status)){
            return R.error("账号已被禁用");
        }

        return R.ok().data("userInfo",pUser);
    }


    /**
     * 密码修改
     */
    @PostMapping("updatePassword")
    public R updatePassword(@RequestBody User data){
        User pUser = sysUserService.getById(data.getId());
        // 加密旧密码
        String oldPassword = AceUtils.string2MD5(data.getOldPassword());
        if(!oldPassword.equals(pUser.getPassword())){
            return R.error("旧密码不正确");
        }
        // 更新密码
        pUser.setPassword(AceUtils.string2MD5(data.getPassword()));
        sysUserService.updateById(pUser);
        return R.ok();
    }


    /**
     * 注册功能
     * @param data
     * @return
     */
    @PostMapping("register")
    public R register(@RequestBody User data){
        QueryWrapper<User> qw = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = qw.lambda();
        lambda.eq(User::getUsername, data.getUsername());
        User pUser = sysUserService.getOne(qw);
        if(pUser != null){
            return R.error("账号已存在");
        }
        // MD5单向加密
        data.setPassword(AceUtils.string2MD5(data.getPassword()));
        data.setStatus("1");
        data.setUserType("2");
        sysUserService.save(data);
        return R.ok();
    }

    /**
     * 个人信息查询
     */
    @GetMapping("getUserInfoById/{id}")
    public R getUserInfoById(@PathVariable String id){
        User ttUser = sysUserService.getById(id);
        return R.ok().data("row", ttUser);
    }

    /**
     * 个人信息修改
     */
    @PostMapping("updateUserInfo")
    public R updateUserInfo(@RequestBody User ttUser){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("id", ttUser.getId());
        sysUserService.update(ttUser, qw);
        return R.ok().data("row", ttUser);
    }

    /**
     * 查询商品分类
     */
    @GetMapping("getGoodsClassifyAll")
    public R getGoodsClassifyAll(){
        List<GoodsClassify> list = goodsClassifyService.list(new QueryWrapper<GoodsClassify>()
                .orderByAsc("sort"));
        return R.ok().data("list", list);
    }

    /**
     * 查询商品信息
     * @return
     */
    @GetMapping("getGoodsList")
    public R getGoodsList(Goods goods){
        List<Goods> goodsList = goodsService.getGoodsList(new Goods().setClassifyId(goods.getClassifyId()).setName(goods.getName()));
        return R.ok().data("list", goodsList);
    }

    /**
     * 查询商品详情
     */
    @GetMapping("getGoodsAndUserById/{id}")
    public R getGoodsAndUserById(@PathVariable String id){
        Goods goods = goodsService.getGoodsAndUserById(id);
        return R.ok().data("row",goods);
    }

    /**
     * 商品发布
     */
    @PostMapping("sendGoods")
    public R sendGoods(@RequestBody Goods goods){
        goods.setSales(0);
        goodsService.save(goods);
        return R.ok();
    }

    /**
     * 商品直接购买
     */
    @PostMapping("sendOrder")
    public R sendOrder(@RequestBody Order order){
        Goods goods = goodsService.getById(order.getGoodsId());
        if(goods.getUserId().equals(order.getBuyUserId())){
            return R.error("您不能购买自己商品");
        }
        if(order.getNum() > goods.getNum()){
            return R.error("库存不足");
        }

        order.setState("0");
        order.setOrderNo(System.currentTimeMillis()+"");
        order.setPrice(goods.getPrice());
        goods.setNum(goods.getNum() - order.getNum());
        goods.setSales(goods.getSales() + order.getNum());
        goodsService.updateById(goods);
        orderService.save(order);
        return R.ok();
    }

    /**
     * 商品购物车购物车购买
     */
    @PostMapping("sendOrders")
    @Transactional
    public R sendOrders(@RequestBody Order order){
        List<String> shoppingcartIds = order.getShoppingcartIds();
        for (String shoppingcartId : shoppingcartIds) {
            Shoppingcart shoppingcart = shoppingcartService.getById(shoppingcartId);
            Goods goods = goodsService.getById(shoppingcart.getGoodsId());
            if(goods.getUserId().equals(order.getBuyUserId())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return R.error("您不能购买自己商品");
            }
            if(shoppingcart.getNum() > goods.getNum()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return R.error(goods.getName()+"库存不足");
            }
            Order orderNew = new Order();
            orderNew.setGoodsId(shoppingcart.getGoodsId());
            orderNew.setState("0");
            orderNew.setOrderNo(System.currentTimeMillis()+"");
            orderNew.setPrice(goods.getPrice());
            orderNew.setNum(shoppingcart.getNum());
            orderNew.setBuyUserId(order.getBuyUserId());

            orderNew.setPhone(order.getPhone());
            orderNew.setAddress(order.getAddress());
            orderNew.setName(order.getName());

            goods.setNum(goods.getNum() - shoppingcart.getNum());
            goods.setSales(goods.getSales() + shoppingcart.getNum());
            goodsService.updateById(goods);
            orderService.save(orderNew);
        }

        // 删除购物车
        for (String id : shoppingcartIds) {
            shoppingcartService.removeById(id);
        }
        return R.ok();
    }


    /**
     * 通过购物车id集合查询商品信息
     */
    @PostMapping("getGoodsByIds")
    public R getGoodsByIds(@RequestBody List<String> ids){
        List<Shoppingcart> list = shoppingcartService.getGoodsByIds(ids);
        return R.ok().data("list", list);
    }


    /**
     * 查询帖子分类
     */
    @GetMapping("getPostClassifyAll")
    public R getPostClassifyAll(){
        List<PostClassify> list = postClassifyService.list(new QueryWrapper<PostClassify>()
                .orderByAsc("sort"));
        return R.ok().data("list", list);
    }

    /**
     * 首页 - 查询帖子信息
     * @param classifyId 分类id
     * @param title 标题
     * @return
     */
    @GetMapping("getPostList")
    public R getPostList(String classifyId, String title){
        Post post = new Post();
        if(!"all".equals(classifyId)){
            post.setClassifyId(classifyId);
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(title)){
            post.setTitle(title);
        }
        List<Post> goodsList = postService.getPostList(post);
        return R.ok().data("list", goodsList);
    }

    /**
     * 添加一条浏览记录
     */
    @PostMapping("sendView/{id}")
    public R sendView(@PathVariable String id){

        Post post = postService.getById(id);
        post.setViewNum(post.getViewNum()+1);
        postService.updateById(post);
        return R.ok();
    }

    /**
     * 查询帖子详情
     */
    @GetMapping("getPostAndUserById/{id}/{userId}")
    public R getPostAndUserById(@PathVariable String id, @PathVariable String userId){
        Post post = postService.getPostAndUserById(id, userId);
        return R.ok().data("row",post);
    }

    /**
     * 发布帖子
     */
    @PostMapping("sendPost")
    public R sendPost(@RequestBody Post post){

        post.setViewNum(0);
        postService.save(post);
        // 存储帖子图片
        List<String> imageList = post.getImageList();
        if(imageList != null && imageList.size() > 0){
            for (int i = 0; i < imageList.size(); i++) {
                PostImage postImage = new PostImage();
                postImage.setSort(i+1);
                postImage.setPostId(post.getId());

                postImage.setImage(imageList.get(i));
                postImageService.save(postImage);
            }
        }
        return R.ok();
    }

    /**
     * 通过帖子id查询帖子详情评论内容
     */
    @GetMapping("getPostComment/{id}")
    public R getConfessionComment(@PathVariable String id){
        List<PostComment> list = postCommentService.getComment(id);
        return R.ok().data("list", list);
    }

    /**
     * 发布帖子评论
     */
    @PostMapping("sendPostComment")
    public R sendPostComment(@RequestBody PostComment postComment) {

        postComment.setContent(EmojiParser.parseToAliases(postComment.getContent()));
        postCommentService.save(postComment);
        PostComment postComment1 = postCommentService.getCommentById(postComment.getId());
        postComment1.setContent(EmojiParser.parseToUnicode(postComment1.getContent()));
        return R.ok().data("row", postComment1);
    }
    /**
     * 帖子点赞和取消点赞
     */
    @PostMapping("postPraise")
    public R postPraise(@RequestBody PostPraise postPraise) {
        PostPraise postPraiseNew = postPraiseService.getOne(new QueryWrapper<PostPraise>()
                .eq("user_id", postPraise.getUserId()).eq("post_id", postPraise.getPostId()));
        if(postPraiseNew != null){
            postPraiseService.removeById(postPraiseNew.getId());
            return R.ok("取消点赞成功");
        }else{
            postPraiseService.save(postPraise);
            return R.ok("点赞成功").data("data", postPraise.getId());
        }
    }

    /**
     * 帖子收藏 和 帖子收藏
     */
    @PostMapping("postCollect")
    public R postCollect(@RequestBody PostCollect postCollect){
        PostCollect activityCollectNew = postCollectService.getOne(new QueryWrapper<PostCollect>()
                .eq("user_id", postCollect.getUserId()).eq("post_id", postCollect.getPostId()));
        if(activityCollectNew != null){
            postCollectService.removeById(activityCollectNew.getId());
            return R.ok("取消收藏成功");
        }else{
            postCollectService.save(postCollect);
            return R.ok("收藏成功").data("data", postCollect.getId());
        }
    }


    /**
     * 消息页面-  通过用户id查询聊天记录列表
     */
    @GetMapping("getUserChatList/{userId}")
    public R getUserChatList(@PathVariable String userId){
        List<UserChat> list =  userChatService.getUserChatList(userId);
        return R.ok().data("list", list);
    }

    /**
     * 消息页面- 查询聊天窗口聊天记录
     * @param sendUserId 发送人id
     * @param receiveUserId 接收人id
     * @return
     */
    @GetMapping("getUserChatBySendUserAndReceiveUser/{sendUserId}/{receiveUserId}")
    public R getUserChatBySendUserAndReceiveUser(@PathVariable String sendUserId, @PathVariable String receiveUserId){
        List<UserChat> list =  userChatService.getUserChatBySendUserAndReceiveUser(sendUserId, receiveUserId);
        return R.ok().data("list", list);
    }

    /**
     * 发送消息
     * @param userChat
     * @return
     */
    @PostMapping("sendUserChat")
    public R sendUserChat(@RequestBody UserChat userChat){

        userChatService.save(userChat);
        return R.ok();
    }

    /**
     * 我的 - 查询我的订单
     */
    @GetMapping("getOrderInfoList/{userId}/{state}")
    public R getOrderInfoList(@PathVariable String userId, @PathVariable String state){
        List<Order> list =  orderService.getOrderInfoList(userId, state);
        return R.ok().data("list", list);
    }

    /**
     * 我的 -订单确认收货
     * @param orderId
     * @return
     */
    @PostMapping("successOrder/{orderId}")
    public R successOrder(@PathVariable String orderId){
        Order order = orderService.getById(orderId);
        order.setState("2");
        orderService.updateById(order);
        return R.ok();
    }

    /**
     * 我的 - 查询我的发布的帖子
     */
    @GetMapping("getPostListByUserId/{userId}")
    public R getPostListByUserId(@PathVariable String userId){
        List<Post> list = postService.getPostList(new Post().setUserId(userId));
        return R.ok().data("list", list);
    }

    /**
     * 我的 - 查询我的收藏的帖子
     */
    @GetMapping("getPostCollectByUserId/{userId}")
    public R getPostCollectByUserId(@PathVariable String userId){
        List<Post> list = postService.getPostCollectByUserId(userId);
        return R.ok().data("list", list);
    }
    /**
     * 我的 - 查询我的点赞的帖子
     */
    @GetMapping("getPostPraiseByUserId/{userId}")
    public R getPostPraiseByUserId(@PathVariable String userId){
        List<Post> list = postService.getPostPraiseByUserId(userId);
        return R.ok().data("list", list);
    }

    /**
     * 我的 - 查询我的发布商品
     */
    @GetMapping("getGoodsListByUserId/{userId}")
    public R getGoodsListByUserId(@PathVariable String userId){
        List<Goods> list = goodsService.getGoodsList(new Goods().setUserId(userId));
        return R.ok().data("list", list);
    }

    /**
     * 购物车页面->查询我的购物车
     */
    @GetMapping("getShoppingCart/{id}")
    public R getShoppingCart(@PathVariable String id){
        List<Shoppingcart> list = shoppingcartService.getShoppingCart(id);
        return R.ok().data("list", list);
    }

    /**
     * 商品详情->加入购物车
     */
    @PostMapping("addShoppingCart")
    public R addShoppingCart(@RequestBody Shoppingcart shoppingcart){
        Shoppingcart shoppingcartNew = shoppingcartService.getOne(new QueryWrapper<Shoppingcart>()
                .eq("user_id", shoppingcart.getUserId())
                .eq("goods_id", shoppingcart.getGoodsId()));
        if(shoppingcartNew == null){
            shoppingcart.setNum(1);
            shoppingcartService.save(shoppingcart);
            return R.ok("加入购物车成功");
        }else{
            shoppingcartNew.setNum(shoppingcartNew.getNum() + shoppingcart.getNum());
            shoppingcartService.updateById(shoppingcartNew);
            return R.ok("购物车商品 +1");
        }
    }

    /**
     * 购物车页面->修改我的购物车 +或-商品
     */
    @PostMapping("updateShoppingCart")
    public R updateShoppingCart(@RequestBody Shoppingcart shoppingcart){
        Shoppingcart shoppingcartNew = shoppingcartService.getById(shoppingcart.getId());
        shoppingcartNew.setNum(shoppingcart.getNum());
        shoppingcartService.updateById(shoppingcartNew);
        return R.ok();
    }

    /**
     * 购物车页面->删除购物车
     */
    @PostMapping("deleteShoppingCart")
    public R deleteShoppingCart(@RequestBody List<String> ids){
        for (String id : ids) {
            shoppingcartService.removeById(id);
        }
        return R.ok();
    }



}
