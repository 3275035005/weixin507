<template>
	<view class="login_back">

		<!-- logo -->
		<view class="login_logo flex" style="font-size: 100rpx;color: #ffffff;">
			农友圈注册
		</view>
		<!-- logo -->

		<!-- form -->
		<view class="login_form">
			<view class="item flex">
				<image src="/static/images/new/icon_name.svg" class="icon" mode="heightFix"></image>
				<input type="text" placeholder="请输入账号" v-model="form.username">
			</view>
			<view class="item flex">
				<image src="/static/images/new/icon_password.svg" class="icon" mode="heightFix"></image>
				<input type="password" placeholder="请输入密码" v-model="form.password">
			</view>
			<view class="item flex">
				<image src="/static/images/new/icon_password.svg" class="icon" mode="heightFix"></image>
				<input placeholder="请输入昵称" type="text" v-model="form.name" />
			</view>
			<view class="item flex">
				<image src="/static/images/new/icon_password.svg" class="icon" mode="heightFix"></image>
				<input placeholder="请输入手机号" type="number" v-model="form.phone" />
			</view>
			<view class="item flex">
				<image src="/static/images/new/icon_password.svg" class="icon" mode="heightFix"></image>
				<radio-group @change="chang">
					<label v-for="item in radioGroup" :key="item.value">
						<radio :value="item.value" :checked="item.value==form.sex" />{{item.name}}
					</label>
				</radio-group>
			</view>
			<view class="button">
				<button @click="submit">注册</button>
			</view>
			<view class="button1">
				<button @click="login">返回登录</button>
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
		register
	} from "@/api/common.js"
	export default {
		data() {
			return {

				form: {
					username: '',
					password: '',
					name: '',
					phone: '',
					sex: '0',
				},
				radioGroup: [{
						value: "1",
						name: "男",
					},
					{
						value: "0",
						name: "女",
					},
				]


			}
		},
		methods: {

		// 返回登录
		login() {
			uni.navigateTo({
				url: "/pages/denglu/denglu"
			});
		},
		chang(e) {
			this.form.sex = e.detail.value;  //选中按钮的value值
		},
		
		// 提交
		submit() {
			let that = this
			if (that.form.username === '') {
				uni.showToast({
					title: '请输入账号',
					icon: "none"
				})
				return
			}
			if (that.form.name === '') {
				uni.showToast({
					title: '请输入昵称',
					icon: "none"
				})
				return
			}
			if (that.form.phone === '') {
				uni.showToast({
					title: '请输入手机号',
					icon: "none"
				})
				return
			}
			if (that.form.password === '') {
				uni.showToast({
					title: '请输入密码',
					icon: "none"
				})
				return
			}
			register(that.form).then((res) => {
					if (res.code == 20000) {
						uni.showToast({
							title: '注册成功',
							duration: 1000,
							icon: "none",
						})
						setTimeout(() => {
							uni.navigateTo({
								url: '/pages/denglu/denglu'
							})
						}, 1000)
		
					} else {
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
		



		}
	}
</script>

<style>
	@import url(zhuce.css);
</style>
