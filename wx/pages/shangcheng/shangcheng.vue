<template>
    <view>
		<!-- 向上的图标-->
		<view class="side" @click="shareBub">
		  <view class="add">+</view>
		</view>
		<view class="side1" @click="shoppingcartBub">
		  <view class="add">
			  <image src="/static/images/index/gwc.svg" style="height: 50rpx;width: 50rpx;"></image>
		  </view>
		</view>
		<view class="home_top flex">
			<view class="home_search flex">
				<image src="/static/images/shoppingcart/icon_search.svg" class="icon"></image>
				<input v-model="name" style="color: #999;" type="text" class="input" />
				<button class="button" @click="search">搜索</button>
			</view>
		</view>
		<!-- 框架 -->
		<view class="goods">
			
			<!-- type -->
			<view class="goods_type">
				<view class="item" :class="cur_type == index?'active':''" v-for="(item,index) in type" @click="choice_type(index)">{{item.title}}</view>
			</view>
			<!-- type -->
			
			<!-- list -->
			<view class="goods_list">
				
				<!-- item -->
				<view class="item" v-for="(item,index) in goods_list" :key="index" @click="goods_link(item.id)">
					<image :src="item.image" class="item_pic" mode="widthFix"></image>
					<view class="item_box">
						<view class="item_title">{{item.name}}</view>
						<view class="item_price">{{item.price}}</view>
					</view>
				</view>
				<!-- item -->
				
			</view>
			<!-- list -->
			
		</view>
		<!-- 框架 -->

    </view>
</template>

<script>
	import {
		getGoodsList,
		getGoodsClassifyAll
	} from "@/api/common.js"
    export default {
        data() {
            return {
				
				cur_type:0,
				
                name: '',
				
				// 商品分类
				type:[],
				
				// 商品列表
				goods_list:[]
				
				
				
            }
        },
		
		created() {
			this.getCategory()
		},
		
			
        methods: {
			
			// 查询分类
            getCategory(){
				getGoodsClassifyAll().then(res => {
					this.type = res.data.list;
					this.getGoodsList(this.type[0].id);
				})
			},
			getGoodsList(id){
				let param = {
					classifyId: id,
					name: this.name
				}
				getGoodsList(param).then(res => {
					this.goods_list = res.data.list
				})
			},
			
			// 搜索功能
			search(){
				if(this.name == ''){
					uni.showToast({
					    title: '请输入搜索商品名称',
					    icon: 'none',
					    duration: 2000
					}) 
				}else{
					this.cur_type = -1
					this.getGoodsList('');
				}
			},
			
			// type
			choice_type(index){
				this.cur_type = index
				this.name = ''
				this.getGoodsList(this.type[index].id);
			},
			
			// link
			goods_link(id){
				uni.navigateTo({
					url:'/pages/shangcheng_xinxi/shangcheng_xinxi?id='+id
				})
			},
			// 点击发布产品
			shareBub(){
				uni.navigateTo({
					url:'/pages/shangcheng_push/index'
				})
			},
			// 点击购物车
			shoppingcartBub(){
				uni.navigateTo({
					url:'/pages/shoppingcart/shoppingcart'
				})
			}
        }
    }
</script>

<style>
@import url(shangcheng.css);
</style>