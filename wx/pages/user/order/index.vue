<template>
	<view class="container">
		<view class="nav">
			<view class="tui-mtop">
				<tui-tabs :tabs="tabs2" :height="88" :currentTab="currentTab" :sliderWidth="150" :sliderHeight="60"
					bottom="50%" color="#888" selectedColor="#888" :bold="true" sliderBgColor="#f4f4f4" @change="change"
					itemWidth="50%">
				</tui-tabs>
			</view>
		</view>
		<!-- order -->
		<view class="order">
			<!-- item -->
			<view class="order_item page_box" v-for="(item,index) in order" :key="index">
				<view class="top flex">
					<view class="number">订单号 : {{item.orderNo}}</view>
					<view class="state s_01" v-if="item.state == 0">待发货</view>
					<view class="state s_02" v-if="item.state == 1">已发货</view>
					<view class="state s_03" v-if="item.state == 2">订单完成</view>
				</view>
				<view class="box flex">
					<image :src="item.image" class="goods_pic" mode="heightFix"></image>
					<view class="goods_content">
						<view class="goods_title">{{item.goodsName}}</view>
						<view class="goods_price">￥{{item.price}}</view>
						<view class="goods_integral flex"><text>X {{item.num}}</text></view>
					</view>
				</view>
				<view class="bottom flex">
					<view class="total">合计: <text>￥{{item.price * item.num}}</text></view>
					<view class="btn flex">
						<view class="btn_cell" v-if="item.state =='1'" @click="back(item.id)">确认收货</view>
					</view>
				</view>
			</view>
			<!-- item -->

			<!-- 弹框 -->
			<tui-modal :show="modal" @click="handleClick" @cancel="hide" content="确认要收货吗?" color="#333" :size="32"
				shape="circle"></tui-modal>
		</view>
		<!-- order -->
	</view>
</template>

<script>
	import {
		getOrderInfoList,
		successOrder
	} from '@/api/common.js'

	export default {
		data() {
			return {
				modal: false,
				orderId:'',
				order: [],
				currentTab: 0,
				tabs2: [{
					name: "待发货"
				}, {
					name: "已发货"
				}, {
					name: "订单完成"
				}],
			}
		},

		created() {
			this.getOrderList();
		},
		methods: {
			change(e) {
				this.currentTab = e.index
				this.getOrderList();
			},

			// 查询订单
			getOrderList() {
				getOrderInfoList(uni.getStorageSync("userInfo").id, this.currentTab).then(res => {
					this.order = res.data.list
				})
			},
			// 确认收货
			sendOrder() {
				successOrder(this.orderId).then(res => {
					if(res.success){
						uni.showToast({
							title: "确认成功"
						})
						this.getOrderList();
					}
				})
			},
			handleClick(e) {
				let index = e.index;
				if (index === 0) {
					this.hide();
				} else {
					this.hide();
					this.sendOrder();
				}
			},

			// 退出
			back(id) {
				this.orderId = id;
				this.modal = true
			},
			hide() {
				this.modal = false
			},

		}
	}
</script>

<style scoped>
	@import url(./index.css);
</style>
