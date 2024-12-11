<template>
	<view class="content">
		<tui-navigation-bar backgroundColor="#fff" :isFixed="true" :isOpacity="false">
			<view class="nav">
				<view class="center">
					<view>
						<image class="avatar" :src="imgInfo.avatar" mode="aspectFill" />
					</view>

					<view class="username">{{imgInfo.userName}}</view>
				</view>
				<!-- <tui-icon name="more-fill" size="20"></tui-icon> -->
			</view>
		</tui-navigation-bar>
		<scroll-view scroll-y class="page" @scrolltolower="loadData" @scroll="scroll" :scroll-top="scrollTop">

			<view class="img-list">
				<!-- 重做图片显示模块 -->
				<view class="img-list-one" v-if="count==1">
					<img :src="imgInfo.imageList[0]" width="100%" @click="previewImgae(0)"></img>
				</view>

				<view class="img-list-two" v-if="count==2">

					<image :src="imgInfo.imageList[0]" mode="widthFix" @click="previewImgae(0)"></image>
					<image :src="imgInfo.imageList[1]" mode="widthFix" @click="previewImgae(1)"></image>
				</view>

				<view class="img-list-five" v-if="count==5">
					<view class="img-list-five-top">
						<image :src="imgInfo.imageList[0]" mode="aspectFill" @click="previewImgae(0)"
							style="width: 50%;height: 600rpx;" class="fadeImg"></image>
						<image :src="imgInfo.imageList[1]" mode="aspectFill" @click="previewImgae(1)"
							style="width: 50%;height: 600rpx;" class="fadeImg"></image>
					</view>
					<view class='img-list-five-down'>
						<image :src="imgInfo.imageList[2]" mode="aspectFill" @click="previewImgae(2)"></image>
						<image :src="imgInfo.imageList[3]" mode="aspectFill" @click="previewImgae(3)"></image>
						<image :src="imgInfo.imageList[4]" mode="aspectFill" @click="previewImgae(4)"></image>
					</view>
				</view>


				<tui-grid :unlined="true" v-if="count==3||count==4||count==6||count==8">
					<block v-for="(item, index) in imgInfo.imageList" :key="index">
						<tui-grid-item :cell="2" :border="false" :bottomLine="false">
							<view class="card">
								<image :src="item" mode="aspectFill" @click="previewImgae(index)"
									style="width: 100%;height: 600rpx;" class="fadeImg"></image>
							</view>
						</tui-grid-item>
					</block>
				</tui-grid>


				<view v-if="count==7">
					<view class="img-list-five-top">
						<image :src="imgInfo.imageList[0]" mode="aspectFill" @click="previewImgae(0)"
							style="height: 500rpx;"></image>
						<image :src="imgInfo.imageList[1]" mode="aspectFill" @click="previewImgae(1)"
							style="height: 500rpx;"></image>
					</view>
					<view class="img-list-five-top">
						<image :src="imgInfo.imageList[2]" mode="aspectFill" @click="previewImgae(2)"
							style="height: 500rpx;"></image>
						<image :src="imgInfo.imageList[3]" mode="aspectFill" @click="previewImgae(3)"
							style="height: 500rpx;"></image>
					</view>
					<view class='img-list-five-down'>
						<image :src="imgInfo.imageList[4]" mode="aspectFill" @click="previewImgae(4)"></image>
						<image :src="imgInfo.imageList[5]" mode="aspectFill" @click="previewImgae(5)"></image>
						<image :src="imgInfo.imageList[6]" mode="aspectFill" @click="previewImgae(6)"></image>
					</view>
				</view>


				<tui-grid :unlined="true" v-if="count==9">
					<block v-for="(item, index) in imgInfo.imageList" :key="index">
						<tui-grid-item :cell="3" :border="false" :bottomLine="false">
							<view class="card">
								<image :src="item" @click="previewImgae(index)" mode="aspectFill"
									style="width: 300rpx;height: 300rpx;" class="fadeImg" />
							</view>
						</tui-grid-item>
					</block>
				</tui-grid>



			</view>
			<view class="cont">
				<view class="img-content">{{imgInfo.content}}</view>
				<view class="time">{{imgInfo.createTime}}|{{imgInfo.viewNum}}浏览</view>
			</view>
			<view class="comment">
				<view style="font-weight: bold;margin-left: 40rpx;">所有评论</view>
				<!--  scrollTop，comArr实现评论跳转，-->
				<comment :mid='imgInfo.id' :zuid='imgInfo.userId' @getComment="getComment" @scrollTop="getScrollTop"
					@cancelAgreeComment="cancelAgreeComment" :val="val" :parentId="parentId" :comArr='comArr'
					:commentInfo='commentInfo' :currentUid='imgInfo.userId' @delComment='delComment'>
				</comment>
				<!-- 评论功能 -->
			</view>


		</scroll-view>

		<!-- 底部输入框 -->
		<view :class="fn?'fotter fotter-none':'fotter'">

			<view v-if="!useful_flag" class="fotter-info">
				<view class="fotter-content" @click="active">{{ placeholder }}</view>
				<tui-icon name="star-fill" size="20" v-if="isCollection" @click="cancelCollect()"></tui-icon>
				<tui-icon name="star" size="20" v-else @click="cancelCollect()"></tui-icon>
				<view class="fotter-c-a">{{ imgInfo.collectNum }}</view>

				<tui-icon name="agree-fill" size="20" v-if="userIsAgree" @click="cancelAgreeImg()"></tui-icon>
				<tui-icon name="agree" size="20" v-else @click="cancelAgreeImg()"></tui-icon>
				<view class="fotter-c-a">{{ imgInfo.praiseNum }}</view>
			</view>

			<!-- 新增表情发送 -->
			<input-emoji v-else :useful_flag="useful_flag" :cursor="cursor" @addCommentWithEmoji="addCommentWithEmoji"
				:placeholder="placeholder">
			</input-emoji>
		</view>
		<!-- 底部输入框完成 -->
		<!-- 蒙板 -->
		<view class="shade" v-show="show" @click="close"></view>

	</view>
</template>

<script>
	import Comment from "@/components/comment.vue"
	import InputEmoji from "@/components/inputEmoji.vue"

	import {
		sendPostComment,
		getPostAndUserById,
		postCollect,
		postPraise
	} from "@/api/common.js"



	import {
		loadImageEnd
	} from "@/utils/utils.js"
	export default {
		components: {
			Comment,
			InputEmoji
		},
		data() {
			return {
				imgInfo: {},
				count: 4,
				album: {},
				content: '',
				comment: {},

				placeholder: '请输入内容~',
				val: 0,
				parentId: '',
				comArr: [],
				isCollection: false,
				userIsAgree: false,
				T: false,
				isCurrentUser: false,


				fn: false,

				//
				commentInfo: {},

				//滚动
				scrollTop: 0,
				old: {
					scrollTop: 0
				},

				//批量保存
				isshow: false,
				affirmId: '',
				selectImgs: [],

				//实现编辑操作
				popupShow: false,
				batchSaveShow: false,
				loading: false,


				// 输入框状态
				useful_flag: false,
				cursor: false,
				show: false,
				pid: '', // 帖子id
				uid:'',// 用户id
			}
		},
		onLoad(option) {
			this.pid = option.id
			this.uid = uni.getStorageSync("userInfo").id
			this.getOne()
		},
		methods: {
			// 查询帖子详情
			getOne() {
				getPostAndUserById(this.pid, this.uid).then(res => {
					let data = res.data.row
					this.imgInfo = data;
					this.count = data.imageList.length
					if(data.collectId != null){
						this.isCollection = true; 
					}
					if(data.praiseId != null){
						this.userIsAgree = true; 
					}
				})
			},

			// 收藏和取消收藏
			cancelCollect() {
				let data = {
					userId: this.uid,
					postId: this.imgInfo.id
				}
				postCollect(data).then(res => {
					this.isCollection = !this.isCollection
					uni.showToast({
						title: res.message
					})
					if(this.isCollection){
						this.imgInfo.collectNum = this.imgInfo.collectNum * 1 + 1
					}else{
						this.imgInfo.collectNum = this.imgInfo.collectNum * 1 - 1
					}
					
					
				})
			},
			// 点赞和取消点赞
			cancelAgreeImg() {
			
				let data = {
					userId: this.uid,
					postId: this.imgInfo.id
				}
				postPraise(data).then(res => {
					this.userIsAgree = !this.userIsAgree
					uni.showToast({
						title: res.message
					})
					if(this.userIsAgree){
						this.imgInfo.praiseNum = this.imgInfo.praiseNum * 1 + 1
					}else{
						this.imgInfo.praiseNum = this.imgInfo.praiseNum * 1 - 1
					}
					
					
				})
			
			},
			// 弹出输入框
			active() {

				this.useful_flag = true
				this.cursor = true
				this.show = true
			},
			
			// 点击发送
			addCommentWithEmoji(content) {
				this.useful_flag = false
				this.show = false
				this.content = content 
				
				let data = {
					postId: this.pid,
					userId: this.uid,
					content: this.content
				}
				sendPostComment(data).then(res => {
					    this.content = ''
						this.placeholder = "请输入内容~"
						this.commentInfo = res.data.row
				})
			},
			

			close() {

				this.useful_flag = false
				this.show = false
				this.placeholder = '请输入内容~'
				this.comment = {}

			},
			
			
			scroll(e) {
				this.old.scrollTop = e.detail.scrollTop
			},

			getScrollTop(top) {
				this.scrollTop = this.old.scrollTop
				this.$nextTick(function() {
					this.scrollTop = top
				});
			},

			delComment(flag) {
				this.fn = flag
			},

			back() {

				let pages = getCurrentPages()

				//返回上一页面部分
				if (this.isCollection) {
					uni.navigateBack({
						delta: pages.length
					})
				} else {
					uni.navigateBack({
						delta: 1
					})
				}


			},
	


			

			cancelAgreeComment(comment) {
				let data = {}
				data.uid = uni.getStorageSync("userInfo").id
				data.agreeCollectId = comment.id
				data.agreeCollectUid = comment.uid
				data.type = 0
				cancelAgree(data).then()
			},


			//-----------------------------------预览图片----------------
			previewImgae(index) {
				let that = this
				uni.previewImage({
					current: index, // 当前显示图片的索引值
					urls: that.imgInfo.imageList, // 需要预览的图片列表，photoList要求必须是数组
				})
			},

			loadData() {
				this.val = Math.random()
			},

			getComment(comment) {
				this.comment = comment

				//第二种方案
				this.placeholder = '回复' + this.comment.username + ':'
				this.active()
			}

		}

	}
</script>

<style scoped>
	@import url(./main.css);
</style>
