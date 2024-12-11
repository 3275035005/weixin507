<template>
	<view class="container">
		<tui-navigation-bar backgroundColor="#fff" :isFixed="false" :isOpacity="false">
			<view class="nav">
				<view class="n-left">
					<view @click='back'><tui-icon name="arrowleft" size="24"></tui-icon></view>
					<view>退出</view>
				</view>
				<view class="next">
					<view @click='next'>发布</view>
				</view>

			</view>
		</tui-navigation-bar>
		<view class="main">

			<view class="img-list">

				<view class="img-item" v-for="(item, index) in imgList" :key="index">
					<image :src="item" @click="previewImgae(index)" mode="aspectFill"></image>
				</view>
				<view class="img-item">

					<view class="upload">
						<view class="img-icon" @click="chooseImg">
							<tui-icon name="plus"></tui-icon>
						</view>
					</view>

				</view>
			</view>
			<view class="content">
			
				<input placeholder="请输入标题" maxlength="300" class="content-input"
					v-model="imgInfo.title"></input>
			
			</view>
			<view class="content">
			
				<textarea placeholder="请输入内容" maxlength="300" class="content-input"
					v-model="imgInfo.content"></textarea>
			
			</view>
			<view class="tag">
				<view class="icon">
					<tui-icon name="bankcard"></tui-icon>
					<p>选择分类</p>
				</view>
				<view class="tag-item">
					{{categoryStr}}
				</view>
				<view class="right">
					<view @click="show"><tui-icon name="arrowright"></tui-icon></view>
				</view>
			</view>
		</view>
		<tui-toast ref="toast"></tui-toast>

		<!-- 底部弹出 -->
		<tui-bottom-popup :zIndex="1002" :maskZIndex="1001" :show="popupShow" @close="popup">
			<view class="categoryBox">

				<view class="category-right">
					<scroll-view scroll-y style="height: 500rpx;">
						<tui-list-view color="#777">
							<tui-list-cell v-for="(category, index) in categoryList" :key="index"
								@click="getCategoryInfo(category)">
								<view class="title">{{ category.title }}</view>
								<view class="desc">{{ category.description }}</view>
							</tui-list-cell>
						</tui-list-view>
					</scroll-view>
				</view>
			</view>
		</tui-bottom-popup>

		<!-- 弹框 -->
		<tui-modal :show="modal" @click="handleClick" @cancel="hide" content="退出数据将不会保存" color="#333" :size="32"
			shape="circle"></tui-modal>

	</view>
</template>

<script>
	import {
		getPostClassifyAll, sendPost
	} from "@/api/common.js"

	export default {
		data() {
			return {
				categoryList: [
					
				],
				popupShow: false,
				imgList: [],
				categoryStr:'',
				imgInfo: {
					
					classifyId:'',
					title:'',
					content:'',
					userId:'',
					imageList:[]
				},
				
				modal: false,
		
			}
		},
		onLoad(option) {
			
		},

		methods: {
			
			handleClick(e){
			 		let index = e.index;
			 		if (index === 0) {
			 			this.hide();
			 		} else {
						this.hide();
			 			uni.switchTab({
			 				url: "/pages/index/index"
			 			})
			 		}
			 	},
			
			// 退出
			back() {
				this.modal = true
			},
			hide() {
				this.modal = false
			},
			
		

			// 图片上传
			chooseImg() {
				let that = this

				if (that.imgList.length >= 9) {
					uni.showToast({
						title: "图片上限"
					})
					return
				}

				let maxCount = 9 - that.imgList.length
				uni.chooseImage({
					count: maxCount, // 最多可以选择的图片张数，默认9
					sizeType: ['original'], //original 原图，compressed 压缩图，默认二者都有
					sourceType: ['album'], //album 从相册选图，camera 使用相机，默认二者都有。如需直接开相机或直接选相册，请只使用一个选项
					success: function(res) {
						that.imgList.push(...res.tempFilePaths)
					}
				})

			},


			// 图片预览
			previewImgae(index) {
				let that = this
				uni.previewImage({
					current: index, // 当前显示图片的索引值
					urls: that.imgList, // 需要预览的图片列表，photoList要求必须是数组
					longPressActions: {
						itemList: ['删除'],
						success: function(data) {
							that.imgList.splice(data.index, 1)
							uni.closePreviewImage()
						},
						fail: function(err) {
						}
					}
				})
			},

			// 选择分类弹框
			show() {
				this.popupShow = true
				getPostClassifyAll().then(res => {
					this.categoryList = res.data.list
				})
			},
			// 选择分类关闭弹框
			getCategoryInfo(category) {
				this.imgInfo.classifyId = category.id
				this.popupShow = false
				this.categoryStr = category.title
			},
			popup() {
				this.popupShow = false
			},
			
			
			// 发布
			next() {
				
			
				if(this.imgInfo.title == ''){
					uni.showToast({
						title: "请输入标题",
						icon:'none'
					})
					return
				}
				if(this.imgInfo.content == ''){
					uni.showToast({
						title: "请输入内容",
						icon:'none'
					})
					return
				}
				if(this.imgInfo.classifyId == ''){
					uni.showToast({
						title: "请选择分类",
						icon:'none'
					})
					return
				}
				let imageList = []
				if(this.imgList.length == 0){
					uni.showToast({
						title: "请选择图片",
						icon:'none'
					})
					return
				}else{
					for(let i=0; i <this.imgList.length; i++){
						
							let that = this;
						  uni.getFileSystemManager().readFile({
						    filePath: that.imgList[i],
						    encoding: 'base64',
						   success: (res) => {
								const base64Image = 'data:image/jpeg;base64,' + res.data;
								this.imgInfo.imageList.push(base64Image)
						    },
						    fail: (errr) => {
								console.log(errr)
						    }
						})
					}
					
				}
			
				uni.showLoading({
					title:'发布中'
				})
				
				let userId = uni.getStorageSync("userInfo").id;
				this.imgInfo.userId = userId;
				
				setTimeout(() => {
						sendPost(this.imgInfo).then(res=>{
							if(res.success){
								uni.hideLoading()
								uni.showToast({
									title: "发布帖子成功"
								})
								setTimeout(function() {
									uni.reLaunch({
										url: '/pages/index/index'
									})
								}, 2000);
							}
						})
				}, 1000); 
				

			
			},
		}
	}
</script>

<style scoped>
	@import url(./push.css);
</style>