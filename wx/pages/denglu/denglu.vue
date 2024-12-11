<template>
	<view class="login_back">

		<!-- logo -->
		<view class="login_logo flex" style="font-size: 100rpx;color: #ffffff;">
			农友圈登录
		</view>
		<!-- logo -->

		<!-- form -->
		<view class="login_form">
			<view class="item flex">
				<image src="/static/images/new/icon_name.svg" class="icon" mode="heightFix"></image>
				<input placeholder="请输入账号" type="text" v-model="username">
			</view>
			<view class="item flex">
				<image src="/static/images/new/icon_password.svg" class="icon" mode="heightFix"></image>
				<input placeholder="请输入密码" type="password" v-model="password" />
			</view>
			<view class="button">
				<button @click="submit">登录</button>
			</view>
			<view class="button1">
				<button @click="zhuche">注册</button>
			</view>
		</view>
		<!-- form -->

		<!-- version -->
		<view class="login_version">
			v1.0
		</view>
		<!-- version -->



	</view>
</template>

<script>
	import {
		login
	} from '@/api/common.js'
	export default {
		data() {
			return {
				username: '',
				password: ''
			}
		},
		methods: {




			// 提交
			submit(){
			
				
			    let that = this
				if (this.username === "") {
				  return uni.showToast({
				    title: "账号不能为空",
				    duration: 1000,
				    icon: "none",
				  })
				} else if (this.password === "") {
				  return uni.showToast({
				    title: "密码不能为空",
				    duration: 1000,
				    icon: "none",
				  })
				}else{

					 login({username:this.username, password:this.password}).then((res) => {
						if(res.code == 20000){
							uni.setStorageSync("userInfo", res.data.userInfo)
							uni.switchTab({
								url:'/pages/index/index'
							})
						}else{
							uni.showToast({
							  title: res.message,
							  duration: 1000,
							  icon: "none",
							})
						}
							
						})
						.catch((err) => { 
							console.log(err)
						})
					
				}
			    
			},




			zhuche() {
				uni.navigateTo({
					url: "/pages/zhuce/zhuce"
				});
			}
		}
	}
</script>

<style>
	@import url(denglu.css);
</style>
