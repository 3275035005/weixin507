<template>
	
	<view class="content">
	
		<scroll-view scroll-y class="page" refresher-enabled="true" :refresher-triggered="triggered"
			@refresherrefresh="onRefresh">
			<view>
				<view class="container">
					<tui-list-view>
						<tui-list-cell :lineLeft="false" v-for="(item, index) in dataList" :key="index">

							<tui-swipe-action :actions="actions" @click="handleAction(item,index)">
								<template v-slot:content>

									<view class="tui-item-box" @click="chat(item)">
										<view class="tui-msg-box">
											<image :src="item.receiveAvatar" class="tui-msg-pic" mode="aspectFill"
												:lazy-load='true' />
											<view class="tui-msg-item">
												<view class="tui-msg-name">{{ item.receiveUserName }}</view>
												<view class="tui-msg-content">{{ item.content }}</view>
											</view>
										</view>
										<view class="tui-msg-right">
											<view class="tui-msg-time">{{ item.createTime.substring(10, 16) }}</view>
										</view>
									</view>
								</template>
							</tui-swipe-action>

						</tui-list-cell>
					</tui-list-view>
				</view>
			</view>
		</scroll-view>

	</view>
</template>

<script>
	import {
		getUserChatList
	} from '@/api/common.js'
	
	import {
		appConfig
	} from '@/config/config.js'
	export default {
		data() {
			return {
				dataList: [],
				actions: [{
					name: '删除',
					color: '#fff',
					fontsize: 30, //单位rpx
					width: 70, //单位px
					background: '#FD3B31',
				}],


				triggered: false,
				userInfo:{}
			}
		},
		onLoad(){
			this.userInfo = uni.getStorageSync("userInfo")
		},
	
		onShow() {
			this.getMessage();
		},
		
		methods: {
			getMessage(){
				getUserChatList(this.userInfo.id).then(res => {
					this.dataList = res.data.list
				})
			},
			
			// 跳转聊天页面
			chat(item) {
				uni.navigateTo({
					url: "/pages/chat/chat?id="+item.receiveUserId
				})
			},
		},

	}
</script>

<style scoped>
	@import url(./message.css);
</style>