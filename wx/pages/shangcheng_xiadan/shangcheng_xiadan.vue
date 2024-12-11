<template>
	<view>
		
		<!-- 商品 -->
		<view class="order_goods page_box">
			<view class="goods_number">共 1 件商品</view>
			<view class="goods_list">
				
				<!-- item -->
				<view class="item flex">
					<image class="item_photo" :src="goods.image" mode="aspectFill"></image>
					<view class="item_box">
						<view class="item_top flex">
							<view class="item_title">{{goods.name}}</view>
							<view class="item_num">x{{dataVo.num}}</view>
						</view>
						<view class="item_price">{{goods.price}}</view>
					</view>
				</view>
				<!-- item -->
				
			</view>
		</view>
		<!-- 商品 -->
		<view class="order_notes page_box">
			<view class="notes_column">收货人姓名</view>
			<view class="notes_box">
				<input v-model="dataVo.name" placeholder="请填写收货人姓名"></textarea>
			</view>
		</view>
		<view class="order_notes page_box">
			<view class="notes_column">收货人电话</view>
			<view class="notes_box">
				<input v-model="dataVo.phone" placeholder="请填写收货人电话"></textarea>
			</view>
		</view>
		<view class="order_notes page_box">
			<view class="notes_column">收货人地址</view>
			<view class="notes_box">
				<input v-model="dataVo.address" placeholder="请填写收货人地址"></textarea>
			</view>
		</view>
		<view class="order_notes page_box">
			<view class="notes_column">备注信息</view>
			<view class="notes_box">
				<textarea v-model="dataVo.remark" placeholder="请填写备注"></textarea>
			</view>
		</view>
		<!-- 底部 -->
		<view class="order_footer flex">
			<view class="footer_text">合计: <text>￥{{goods.price * dataVo.num}}</text></view>
			<view class="footer_sub" @click="subOrder()">提交订单</view>
		</view>
		<!-- 底部 -->
		
	</view>
</template>

<script>
	import {
		getGoodsAndUserById, sendOrder
	} from "@/api/common.js"
	export default {
		data() {
			return {
				dataVo:{
					name:'',
					phone:'',
					address:'',
					remark:'',
					num: 1,
					goodsId:'',
					userId: ''
				},
				goods:{}
			}
		},
		onLoad(option) {
			this.dataVo.num = option.num;
			this.dataVo.goodsId = option.id;
			this.dataVo.buyUserId = uni.getStorageSync("userInfo").id
			this.getInit()
		},
		methods: {
			getInit(){
				getGoodsAndUserById(this.dataVo.goodsId).then(res => {
					this.goods = res.data.row
				})
			},
			// 提交订单
			subOrder(){
				uni.showLoading({
					title:"购买中",
					mask: true
				})
				sendOrder(this.dataVo).then(res => {
					uni.hideLoading()
					if(res.success){
						uni.showToast({
						    title: '购买成功',
						    icon: 'success',
						    duration: 2000
						}) 
						setTimeout(function() {
							uni.navigateBack({
							     url:'/pages/shangcheng_xinxi/shangcheng_xinxi'
							});
						}, 2000);
						

					}else{
						uni.showToast({
						    title: res.message,
						    icon: 'none',
						    duration: 2000
						})  
					}
				})
			}
		},
		
	}
</script>

<style>
@import url(shangcheng_xiadan.css);
page{padding: 28rpx; background: url(/static/images/new/wode_dingdan.svg) no-repeat #f5f5f5;}
</style>
