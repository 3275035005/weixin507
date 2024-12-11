<template>
   <!-- 框架 -->
   <view class="goods">
   	<!-- list -->
   	<view class="goods_list">
   		
   		<!-- item -->
   		<view class="item" v-for="(item,index) in goods_list" :key="index" @click="goods_link(item.id)">
   			<image :src="item.image" class="item_pic" mode="widthFix"></image>
   			<view class="item_box">
   				<view class="item_title">{{item.name}}</view>
   				<view class="item_price">{{item.price}}</view>
   			</view>
			
   		</view>
		<view class="fotter"></view>
   		<!-- item -->
   		
   	</view>
   	<!-- list -->
   	
   </view>
   <!-- 框架 -->
</template>

<script>
	import {
		getGoodsListByUserId
	} from "@/api/common.js"
    export default {
        data() {
            return {
				goods_list:[]
            }
        },
		
		created(){
			this.getInit();
		},
		methods: {
			getInit(){
				getGoodsListByUserId(uni.getStorageSync("userInfo").id).then(res => {
					this.goods_list = res.data.list
				})
			},
			
			// link
			goods_link(id){
				uni.navigateTo({
					url:'/pages/shangcheng_xinxi/shangcheng_xinxi?id='+id
				})
			}
				
        }
    }
</script>

<style>
@import url(index.css);
</style>