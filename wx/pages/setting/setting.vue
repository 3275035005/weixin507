<template>
	<view class="content">
		

		<view class="update_pwd">
			<view class="info-item-zhuti" @click="updatePassword">

				<view class="title">修改密码</view>
				<view class="icon"><tui-icon name="arrowright" size="25"></tui-icon></view>
			</view>
		</view>

		

		<view class="fotter">
			<view class="f-btn">
				<button @click="loginOut">退出登录</button>
			</view>

		</view>
	</view>
</template>

<script>

	import {
		tokenUtil
	} from "@/utils/token.js"
	export default {
		data() {
			return {}
		},
		
		methods: {
			
			loginOut() {
				uni.removeStorageSync("userInfo")
				tokenUtil.clear()
				
				setTimeout(() => {
					uni.navigateTo({
						url: "/pages/denglu/denglu"
					})
				}, 500)

			},

			updatePassword() {
				uni.navigateTo({
					url: "/pages/setting/updatePossword/updatePossword"
				})
			}
		}
	}
</script>

<style scoped>
	@import url(./setting.css);
</style>