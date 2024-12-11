<template>
	<view class="content" :style="'margin-top:'+vHeight+'rpx'">
		<tui-navigation-bar backgroundColor="#fff" :isFixed="false" :isOpacity="false">
			<view class="top">
				<view class="right">
					<view class="item">
						<navigator url="/pages/setting/setting">
							<tui-icon name="setup" class="icon-item-setup" color="#fff" size="30"></tui-icon>
						</navigator>
					</view>
				</view>
			</view>
		</tui-navigation-bar>


		<!-- 头 -->

		<!-- 显示图片 -->
		<view class="image"> 
			<image :src="userInfo.image" mode="aspectFill" @click="previewImgae(userInfo.image)" />
		</view>

		<!-- 主体 -->
		<view class="main">
			<view class="top">
				<view class="user">
					<view class="user-left">
						<image :src="userInfo.avatar" class="avatar" mode="aspectFill"
							@click="previewImgae(userInfo.avatar)" />
						<view class="user-content">
							<h3>{{ userInfo.name }}</h3>
							<view class="descrpition f">{{ userInfo.content }} </view>
						</view>
					</view>
					<view class="user-right">
						<tui-button type="danger" shape="circle" @click="editUserInfo" height="60rpx" width="140rpx"
							:size="28">编辑</tui-button>
					</view>
				</view>
				<view class="">
					<tui-tabs :tabs="tabs" :currentTab="currentTab" @change="change" sliderBgColor="red"
						selectedColor="red" itemWidth="50%"></tui-tabs>
				</view>
			</view>

			<view class="zhuti">
				<view>
					<Trend ref="trendRef" v-if="currentTab == 0"> </Trend>
					<Album ref="albumRef" v-if="currentTab == 1"></Album>
					<Praise ref="praiseRef" v-if="currentTab == 2"></Praise>
					<Order ref="orderRef" v-if="currentTab == 3"></Order>
					<Goods ref="goodsRef" v-if="currentTab == 4"></Goods>
				</view>
				
			</view>
		</view>

	</view>
</template>

<script>
	import Trend from "@/pages/user/trends/trends"
	import Album from "@/pages/user/albums/albums"
	import Praise from "@/pages/user/praise/praise"
	import Order from "@/pages/user/order/index"
	import Goods from "@/pages/user/goods/index"
	
	export default {
		components: {
			Trend,
			Album,
			Praise,
			Order, 
			Goods
		},
		data() {
			return {
				current: 0,
				currentTab: 0,
				tabs: [{
					name: "动态"
				}, {
					name: "收藏"
				}, {
					name: "点赞"
				},{
					name: "订单"
				}, {
					name: "商品"
				}],
				userInfo: {},
				seed: 0,
				//页面初始化高度
				screenHeight: 0,

				top_show: false,

				vHeight: 0,
			}
		},
		onLoad(options) {
			this.userInfo = uni.getStorageSync("userInfo");
		},

		
		onShow() {
			if(this.currentTab == 0){
				this.$refs.trendRef.getInfo();
			}else if(this.currentTab == 1){
				this.$refs.albumRef.getInit();
			}else if(this.currentTab == 2){
				this.$refs.praiseRef.getInit();	  
			}else if(this.currentTab == 3){
				this.$refs.orderRef.getOrderList();	  
			}else if(this.currentTab == 4){
				this.$refs.goodsRef.getInit();	  
			}
			
			
			
		
			
			this.userInfo = uni.getStorageSync("userInfo");
		},
		methods: {
			change(e) {
				this.currentTab = e.index
			},
		
			
			
			// 跳转设置
			setting() {
				uni.navigateTo({
					url: "/pages/setting/setting"
				})
			},


			// 跳转编辑资料
			editUserInfo() {
				uni.navigateTo({
					url: "/pages/user/info/info?uid=" + this.userInfo.id
				})
			},
		
			


			//-----------------------------------预览图片----------------
			previewImgae(url) {

				let that = this
				let path = []
				path.push(url)

				uni.previewImage({
					current: 0, // 当前显示图片的索引值
					urls: path, // 需要预览的图片列表，photoList要求必须是数组
					longPressActions: {
						itemList: ['保存'],
						success: function(data) {

							//进行保存
							if (data.tapIndex == 0) {
								let p = that.downLoadImg(url)
								p.then((data) => {
									uni.showToast({
										title: data,
										icon: "none"
									});
								})
							}
						},
						fail: function(err) {
							return
						}
					}
				})
			},

			// 保存图片至本地
			downLoadImg(path) {
				//下载图片资源至本地，返回文件的本地临时路径
				return new Promise((resolve, reject) => {
					uni.downloadFile({
						url: path,
						success: (res) => {
							if (res.statusCode === 200) {
								//保存图片至相册
								uni.saveImageToPhotosAlbum({
									filePath: res.tempFilePath,
									success: function() {
										resolve("保存成功")
									},
									fail: function() {
										reject("保存失败")
									}
								});
							}
						}
					})
				})
			},
		}
	}
</script>

<style scoped>
	@import url(./user.css);
</style>