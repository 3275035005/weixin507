<template>
	<view class="container">
		<view class="main">
			<input placeholder="输入原密码" class="info-input" type="password" v-model="oldPassword" />
			<input placeholder="输入新密码" class="info-input" type="password" v-model="password" />

			<view class="regist">
				<tui-button @click="updatePassword" type="danger" shape="circle">修改密码</tui-button>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		updatePassword
	} from "@/api/common.js"

	export default {
		data() {
			return {

				id: '',
				password: '',
				oldPassword: '',
			}
		},

		created() {
			this.id = uni.getStorageSync("userInfo").id
		},

		methods: {



			updatePassword() {

				if (this.oldPassword == '') {
					uni.showToast({
						title: "请输入原密码"
					})
					return
				}
				if (this.password == '') {
					uni.showToast({
						title: "请输入新密码"
					})
					return
				}

				

				let userObj = {
					id : this.id,
					password : this.password,
					oldPassword : this.oldPassword
				}
				
				updatePassword(userObj).then(res => {

					if (res.success) {
						uni.showToast({
							title: "修改成功"
						})
						setTimeout(() => {
							uni.reLaunch({
								url: "/pages/user/user"
							});
						}, 1000)
					}else{
						uni.showToast({
							title: res.message,
							 icon: 'error',
						})
					}
				})
			}

		}
	}
</script>

<style scoped>
	@import url(./updatePassword.css);
</style>
