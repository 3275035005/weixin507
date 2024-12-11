<template>
    <view>
        
        <!-- swiper -->
        <view :indicator-dots="true" :autoplay="true" class="goods_swiper">
            <view class="swiper-item">
                <image :src="goods.image" class="swiper_img"></image>
            </view>
        </view>
        <!-- swiper -->
        
        <!-- info -->
        <view class="goods_info">
            <view class="info_title">{{goods.name}}</view>
            <view class="info_price">
                {{goods.price}}
            </view>
            <view class="info_attr flex">
                <view class="cell">原价:￥{{goods.old}}</view>
                <view class="cell">库存: {{goods.num}} </view>
                <view class="cell">销量: {{goods.sales}} </view>
            </view>
        </view>
        <!-- info -->
        
        <!-- other -->
        <view class="goods_other">
           
            <view class="item flex" @click="choice_pop" >
                <view class="text">已选择</view>
                <view class="content">
                   数量：{{number}}
                </view>
            </view>
           
        </view>
        <!-- other -->
        
        <!-- content -->
        <view class="goods_content">
           {{goods.content}}
        </view>
        <!-- content -->
        
        <!-- footer -->
        <view class="goods_footer flex">
			<view class="left flex">
			    <view class="item">
					<button @click="chat()">
						<image src="/static/images/news/icon_chat.svg" class="icon"></image>
						<view class="text" >客服</view>
					</button>
			    </view>
			</view>
            <view class="right">
				<button class="btn1" @click="addShoppingcart">
					<image src="/static/images/index/gwc.svg" style="height: 50rpx;width: 50rpx;margin-top: 15rpx;"></image>
					
				</button>
                <button class="btn2" @click="goods_buy">立即下单</button>
            </view>
        </view>
        <!-- footer -->
        
        <!-- choice -->
        <view class="pop" v-if="choice_show">
            <view class="pop_choice">
                
                <!-- goods -->
                <view class="choice_goods flex">
                    <image :src="goods.image" class="goods_pic" mode="widthFix"></image>
                    <view class="goods_box">
                        <view class="goods_title">{{goods.name}}</view>
                        <view class="goods_attr">库存: {{goods.num}} </view>
                    </view>
                </view>
                <!-- goods -->
                
                <!-- number -->
                <view class="choice_number">
                    <view class="choice_title">数量</view>
                    <view class="number_box flex">
                        <view class="btn" @click="number_cut">-</view>
                        <input class="input" v-model="number" />
                        <view class="btn" @click="number_add">+</view>
                    </view>
                </view>
                <!-- number -->
                
                <!-- close -->
                <image @click="choice_pop" src="/static/images/new/icon_close.svg" class="choice_close"></image>
                <!-- close -->
                
            </view>
        </view>
        <!-- choice -->
        
    </view>
</template>
`
<script>
	import {
		getGoodsAndUserById,
		addShoppingCart
	} from "@/api/common.js"
	
    export default {
        data() {
            return {
                
                // current
                current_rule : 0,
                
                // goods
                goods:{},
                id:'',
                // number
                number:1,
                
                // choice
                choice_show:false
                
                

            }
        },
		onLoad(option) {
			this.id = option.id;
			this.getInit()
		},
		
        methods: {
            getInit(){
				getGoodsAndUserById(this.id).then(res => {
					this.goods = res.data.row
				})
			},
            // choice
            choice_rule(index){
                this.current_rule = index
            },
            choice_pop(){
                this.choice_show =! this.choice_show
            },
            
            // number
            number_cut(){
                let that = this
                if(that.number == 1){
                    uni.showToast({
                        title:'不能再减了',
                        icon:"none"
                    })
                    return false
                }else{
                    that.number --
                }
            },
            number_add(){
                let that = this
                that.number ++
            },
			
			// 跳转聊天页面
			chat() {
				console.log("===========")
				console.log(this.goods.userId);
				uni.navigateTo({
					url: "/pages/chat/chat?id="+this.goods.userId
				})
			},
			// buy
			goods_buy(){
				uni.navigateTo({
					url:'/pages/shangcheng_xiadan/shangcheng_xiadan?id='+this.id+'&num='+this.number
				})
			},
			// 加入购物车
			addShoppingcart(){
				uni.showLoading({
					title:"加入中",
					mask: true
				})
				addShoppingCart({
					userId: uni.getStorageSync("userInfo").id,
					goodsId: this.id,
					num: this.number
				}).then(res => {
					uni.hideLoading()
					if(res.success){
						uni.showToast({
						    title: res.message,
						    icon: 'success',
						    duration: 2000
						})
						this.number =1
					}else{
						uni.showToast({
						    title: '失败',
						    icon: 'none',
						    duration: 2000
						})  
					}
				})
			}
			
        }
    }
</script>

<style>
@import url(shangcheng_xinxi.css);
</style>
