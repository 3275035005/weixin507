<template>
	<view>
		<view class="tui-content-box">
			<view class="tui-avatar-box">
				<view>
					<image src="/static/images/toast/img_nodata.png" class="tui-avatar" />
				</view>
			</view>
			<view class="tui-search-box" >
				<tui-icon name="search-2" :size="18" color="#bfbfbf" @click="getPostList"></tui-icon>
				<input class="tui-search-text" v-model="value"></input>
			</view>
		</view>
		<view :class="isFixed?'tui-mtop-fixed':'tui-mtop' " id='tui-mtop'>
			<view class="nav">
				<ul>
					<li v-for="(item, index) in categoryList" :key="index">
						<a @click="getImgListByCategory(item.id, index)">
							<view :class="index==C?'c-activated':''">{{item.title}}</view>
						</a>
					</li>
				</ul>
			</view>

			<a>
				<tui-icon name="arrowdown" size="20" color="#666666" @click="show" v-if="T"></tui-icon>
				<tui-icon name="arrowup" size="20" color="#666666" @click="show" v-else></tui-icon>
			</a>

			<view class="nav-show" :class="T ? 'box-show' : ''">
				<ul>
					<li v-for="(item, index) in categoryList" :key="index" v-show="index>=7">
						<a @click="getImgListByCategory(item.id,index)">
							<view :class="index==C?'c-activated':''">{{item.title}}</view>
						</a>
					</li>
				</ul>
			</view>
		</view>

		<!-- 使用瀑布流 -->
		<view class="main">
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
	</view>
</template>

<script>
	import {
		getPostList,
		getPostClassifyAll,
		sendView
	} from "@/api/common.js"

	export default {
		
		props: {
			seed: Number
		},
		data() {
			return {

				currentTab: 1,

				T: true,
				C: -1,
				visiable: false,
				triggered: false,
				userInfo: {},
				page: 1,
				limit: 5,
				size: 50,
				total: 2,
				isEnd: false, //是否到底底部了
				loading: false, //是否正在加载
				keyword: '',
				categoryList: [],
				isFirst: false,

				//滚动
				scrollTop: 0,
				old: {
					scrollTop: 0
				},

				isFixed: false,

				startData: {},

				list: [], //瀑布流总数据
				list1: [], // 瀑布流第一列数据
				list2: [], // 瀑布流第二列数据

				needRefresh: false,

				stickyScrollTop: 0,
				classifyId:'all',
				value:''
			}
		},


		created() {
			this.userInfo = uni.getStorageSync("userInfo")
			if(this.userInfo == ''){
				uni.reLaunch({
					url: "/pages/denglu/denglu"
				})
			}
			
		},

		onShow() {
			this.getCategory()
			this.getPostList();
		},


		methods: {
			
			show() {
				this.T = !this.T
			},
			closeDrawer() {
				this.visiable = false
			},

			getCategory() {
				getPostClassifyAll().then(res => {

					this.categoryList = res.data.list
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

			getImgListByCategory(id, index) {
				this.C = index
				//数据要先清空
				
				this.classifyId = id;
				this.getPostList();
				this.T = true 
			},

			getPostList() {
				
				uni.showLoading({
					title:'加载中'
				})
				this.list = [];
				this.list1 = [];
				this.list2 = [];
				getPostList(this.classifyId, this.value).then(res => {
					res.data.list.forEach(item => {
						this.list.push(this.getItem(item))
					})
					uni.hideLoading()
				})
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
	@import url(./index.css);
</style>
