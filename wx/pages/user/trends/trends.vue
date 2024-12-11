<template>
	<view class="container">
			<ul>
				<uni-list :data='dataList' :total='total'>
					<li v-for="(item, index) in dataList" :key="index">
						<view class="item">
							<view class="top">
								<view class="avatar-user">
									<view class="avatar">
										<image :src="userInfo.avatar" mode="aspectFill" :lazy-load='true' />
									</view>
									<view class="user">
										<view class="name">{{ userInfo.name }}</view>
										<view class="time">{{ item.createTime }}</view>
									</view>
								</view>
							</view>
							<view class="content">
								{{ item.content }}
							</view>
							<view class="main">

								<view class="img-list">
									<view v-for="(img, index) in item.imageList" :key="index">
										<image :src="img" mode="aspectFill" :lazy-load='true' class="fadeImg" />
									</view>
								</view>
							</view>
							<view class="fotter"></view>
						</view>
					</li>
				</uni-list>
			</ul>
	</view>
</template>

<script>
	
	import {getPostListByUserId} from '@/api/common.js'
	import {
		appConfig
	} from '@/config/config'
	export default {
		props: {
			uid: String,
		},
		data() {
			return {
				page: 1,
				limit: 3,
				userInfo: {},
				dataList: [], 
				isEnd: false, //是否到底底部了
				loading: false, //是否正在加载
				total: 1,
				type: 0,
			
			}
		},
		created(){
			this.userInfo = uni.getStorageSync("userInfo");
			this.getInfo();
		},
		
		methods: {
			getInfo() {
				getPostListByUserId(this.userInfo.id).then(res=>{
					this.dataList = res.data.list
				})
			}
		}
	}
</script>

<style scoped>
	@import url(./trends.css);
</style>