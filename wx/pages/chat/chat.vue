<template>
	<!-- 已使用第三方工具，这里的代码不用了 -->
	<view>
		<view class="container" :style="{ 'padding-bottom': `${52 + inputBottom}px` }">
			<scroll-view class="content" :scroll-y="true" :scroll-top="scrollTop" refresher-enabled="true"
				:refresher-triggered="triggered" @refresherrefresh="onRefresh">
				<view v-for="(item, index) in dataList" id="chatBox">
					<view class="tui-chat-item" v-if="item.sendUserId == receiveUser.id">
						<image class="tui-user-avatar tui-mr" :src="receiveUser.avatar" mode="aspectFill" />
						<view class="tui-chat-box">
							<tui-bubble-popup direction="left" position="relative" :mask="false" :show="true"
								triangleLeft="-22rpx" triangleTop="30rpx" width="100%" backgroundColor="#ff7900">
								<view class="tui-chat-text">{{ item.content }}</view>
							</tui-bubble-popup>
						</view>
					</view>
					<view class="tui-chat-item tui-flex-end" v-else>
						<view class="tui-chat-box">
							<tui-bubble-popup direction="right" position="relative" :mask="false" :show="true"
								triangleRight="-22rpx" triangleTop="30rpx" width="100%" :flexEnd="true"
								backgroundColor="#19be6b">
								<view class="tui-chat-text">{{ item.content }}</view>
							</tui-bubble-popup>

						</view>

						<image class="tui-user-avatar tui-ml" :src="userInfo.avatar" mode="aspectFill" />
					</view>

				</view>
			</scroll-view>
		</view>
		<view class="bottom-textarea" :style="{ bottom: inputBottom + 'px' }">
			<view class="textarea-container">
				<textarea v-model="recordInput" :maxlength="-1" :auto-height="true" :show-confirm-bar="false"
					:cursor-spacing="10" :fixed="true" :adjust-position="false" @focus="focusTextarea"
					@blur="blurTextarea" />
			</view>
			<tui-button type="danger" height="80rpx" width="100rpx" @click="send()">发送</tui-button>
		</view>
	</view>
</template>

<script>
	import {
	  getUserChatBySendUserAndReceiveUser, sendUserChat, getUserInfo, getUserInfoById
	} from "@/api/common.js"

	export default {
		data() {
			return {
				
				itemAverageHeight: 500, //每条数据平均高度，为确保能滚到底部，可以设置大一些
				scrollTop: 1000,
				inputBottom: 0,
				
				
				triggered: false,
				
				recordInput: "", // 发送内容
				dataList: [], // 聊天记录
				receiveUser: {}, // 接收方信息
				rid:'', // 接收方id
				userInfo: {}, // 发送方信息
			};
		},
		onLoad(options) {
			this.rid = options.id;
			this.getReceiveUser();
			
			this.userInfo = uni.getStorageSync("userInfo")
		},
		
		methods: {
			// 查询聊天记录
			getMessage(){
				getUserChatBySendUserAndReceiveUser(this.userInfo.id, this.receiveUser.id).then(res=>{
					this.dataList = res.data.list
					this.scrollTop = this.scrollTop + this.dataList.length * this.itemAverageHeight;
				})
			},
			
			// 发送
			send() {
				if(this.recordInput == '' || this.recordInput == undefined || this.recordInput == null){
					uni.showToast({
						title: "请填写发送内容"
					})
					return;
				}
				
				uni.showLoading({
					title:"发送中",
					mask: true
				})
							
				let parem = {
					content: this.recordInput,
					sendUserId: this.userInfo.id, 
					receiveUserId: this.receiveUser.id
				}
				sendUserChat(parem).then(res=>{
					uni.hideLoading()
					this.dataList.push(parem);
					this.recordInput = null //置空输入框
					this.scrollTop = this.scrollTop + this.itemAverageHeight;
				})
			},

			focusTextarea(e) {
				this.inputBottom = e.detail.height;
				this.scrollTop += 1; //滚到底部
			},
			blurTextarea(e) {
				this.inputBottom = 0;
				this.scrollTop += 1; //滚到底部
			},

			// 查询接收人信息
			getReceiveUser(){
				getUserInfoById(this.rid).then(res=>{
					this.receiveUser = res.data.row;
					uni.setNavigationBarTitle({
						title: this.receiveUser.name,
					})
					this.getMessage() // 查询聊天记录
				})
				 
			}
		},
	};
</script>

<style lang="less">
	@left-right-margin: 40rpx;

	.container {
		width: 100vw;
		height: 100vh;
		box-sizing: border-box;
		padding-bottom: 52px;
		display: flex;
		flex-direction: column;

		.header {
			flex-shrink: 0;
			padding: 0px @left-right-margin 32rpx @left-right-margin;

			.nav {
				display: flex;
				justify-content: space-between;
				margin-right: 10px;
			}
		}

		.content {
			flex-grow: 1;
			overflow: auto;
			height: 90vh;

			.message {
				margin: 0px @left-right-margin 32rpx;
				display: flex;
				flex-direction: column;
				align-items: flex-end;

				.message-content {
					width: auto;
					max-width: calc(100vw - 80rpx);
					word-wrap: break-word;
					box-sizing: border-box;
					padding: 32rpx;
					line-height: 48rpx;
					background: pink;
					border-radius: 16rpx 0px 16rpx 16rpx;
				}

				.message-time {
					line-height: 32rpx;
					margin-top: 16rpx;
				}
			}

			.tui-flex-end {
				justify-content: flex-end;
			}

			.tui-chat-item {
				width: 100%;
				padding: 30rpx 30rpx 0 30rpx;
				box-sizing: border-box;
				display: flex;
				position: relative;

				.tui-chat-box {
					max-width: 70%;

					.tui-user-name {
						font-size: 24rpx;
						color: #999;
					}

					.tui-chat-text {
						width: 100%;
						min-width: 40rpx;
						padding: 18rpx 24rpx;
						display: flex;
						align-items: center;
						flex-wrap: wrap;
						word-break: break-all;
						box-sizing: border-box;
					}
				}

				.tui-user-avatar {
					width: 84rpx;
					height: 84rpx;
					border-radius: 50%;
					flex-shrink: 0;
				}

				.tui-mr {
					margin-right: 24rpx;
				}

				.tui-ml {
					margin-left: 24rpx;
				}

			}
		}
	}

	.bottom-textarea {
		position: fixed;
		bottom: 0px;
		left: 0px;
		right: 0px;
		background-color: #fff;
		border-top: 1rpx solid #f4f4f4;
		padding: 6px 20px;
		display: flex;
		align-items: flex-end;

		.textarea-container {
			flex: 1;
			min-height: 60rpx;
			background: #f2f2f2;
			border-radius: 8rpx;
			box-sizing: border-box;
			padding: 20rpx 16rpx;

			>textarea {
				max-height: 250rpx;
				font-size: 36rpx;
				color: rgba(8, 8, 8, 1);
				width: auto;
			}
		}
	}
</style>