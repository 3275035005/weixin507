<template>
	<view class="waterfall">
		<uv-waterfall ref="waterfall" v-model="list" column-count="2" @changeList="changeList" column-gap="8">
				<template v-slot:list1>
					<!-- 为了磨平部分平台的BUG，必须套一层view -->
					<view>
						<uni-list :data="list1" :total='list1.length' style="background-color: #f4f4f4;">
							<view v-for="(item, index) in list1" :key="index" class="waterfall-item">
		
								<view class="waterfall-item__image">
									<image :src="item.image" :lazy-load="true" mode="widthFix" :width="item.width+'px'"
										@click="getImgInfo(item.id)">
		
									</image>
								</view>
								<view class="card">
		
									<view class="cont">
										<view class="content">{{item.title}}
										</view>
										<view class="userInfo">
											<view class="avatar-usrname">
												<image :src="item.avatar" :lazy-load="true" mode="aspectFill"
													class="avatar"></image>
												<view class="username">{{ item.userName }}</view>
											</view>
											<view class="agreeCount">
												<tui-icon name="star" size="10"></tui-icon>{{item.collectNum}}
											</view>
										</view>
										<view class="card-nums">
											{{item.count}}
										</view>
									</view>
								</view>
							</view>
						</uni-list>
		
		
					</view>
				</template>
				<!-- 第二列数据 -->
				<template v-slot:list2>
					<!-- 为了磨平部分平台的BUG，必须套一层view -->
					<view>
						<uni-list :data='list2' :total='list2.length' style="background-color: #f4f4f4;">
							<view v-for="(item, index) in list2" :key="index" class="waterfall-item">
								<view class="waterfall-item__image">
		
									<image :src="item.image" :lazy-load="true" mode="widthFix" :width="item.width+'px'"
										@click="getImgInfo(item.id)">
									</image>
								</view>
								<view class="card">
		
									<view class="cont">
										<view class="content">{{item.title}}</view>
										<view class="userInfo">
											<view class="avatar-usrname">
												<image :src="item.avatar" :lazy-load="true" mode="aspectFill"
													class="avatar"></image>
												<view class="username">{{ item.userName }}</view>
											</view>
											<view class="agreeCount">
												<tui-icon name="star" size="10"></tui-icon>{{item.collectNum}}
											</view>
										</view>
		
										<view class="card-nums">
											{{item.count}}
										</view>
									</view>
								</view>
							</view>
		
						</uni-list>
		
					</view>
				</template>
			</uv-waterfall>
		
    </view>
</template>
<script>
	import {
		getPostPraiseByUserId, sendView
	} from "@/api/common.js"
	export default {
		data() { 
			return {
				list1: [], // 瀑布流第一列数据
				list2: [], // 瀑布流第二列数据
				list:[],
			}
		},
		created(){
			this.getInit();
		},
		methods: {
			getInit(){
				getPostPraiseByUserId(uni.getStorageSync("userInfo").id).then(res=>{
					this.list = [];
					this.list1 = [];
					this.list2 = [];
					res.data.list.forEach(item => {
						this.list.push(this.getItem(item))
					})
				})
			},
			getItem(item) {
				return {
					id: item.id,
					image: item.imageList[0],
					title: item.title,
					count: item.imageList.length,
					avatar: item.avatar,
					userName: item.userName,
					collectNum: item.collectNum
				}
			},
			changeList(e) {
				this[e.name].push(e.value);
			},

			// 跳转详情
			getImgInfo(id) {
				console.log(id)
				sendView(id).then(res => {
					// 添加浏览记录
					uni.navigateTo({
						url: "/pages/main/main?id=" + id
					})
				})
			
			}
		}
	}
</script>
<style scoped>
	image {
		width: 100%;

		border-top-left-radius: 8px;
		border-top-right-radius: 8px;
	}

	.waterfall {
		background-color: #f4f4f4;
	}

	.card {
		margin: 0rpx 2rpx;
		box-sizing: border-box;
		break-inside: avoid;
		background-color: #fff;
		position: relative;
	}

	.card .cont {
		margin-left: 10px;
	}

	.card .cont p {
		margin-top: 5px;
		font-size: 12px;
	}



	.card .card-nums {
		position: absolute;
		bottom: 130rpx;
		left: 300rpx;
		background-color: #7a7a7a;
		width: 30rpx;
		height: 45rpx;
		text-align: center;
		color: #fff;
		opacity: 0.5;
	}

	.userInfo {
		margin-top: 5px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-right: 5px;
	}

	.avatar-usrname image {
		width: 50rpx;
		height: 50rpx;
		border-radius: 50%;
	}

	.avatar-usrname {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding-bottom: 5px;
	}

	.username {
		color: #000000;
		font-size: 24rpx;
	}

	.agreeCount {
		color: #000000;
		font-size: 24rpx;
	}

	.waterfall-item {
		background-color: #fff;
		overflow: hidden;
		margin-bottom: 10rpx;
		border-radius: 6px;
	}
</style>