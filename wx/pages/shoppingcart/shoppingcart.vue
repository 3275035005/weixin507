<template>
	<view class="shop-car">
		<view class="common-car">
			<view class="empty-shop-car" v-if="goodsProducts==''">
				<image src="/static/images/shoppingcart/empty_shop_car.png" class="empty-shop-car-image" mode=""></image>
				<text>当前您的购物车是空的</text>
				<view class="empty-shop-car-btn" @click="subNext">
					<text>去逛逛</text>
				</view>
			</view>
			<view class="shop-car" v-else>
				<view class="shop-car-header">
					<text @tap="cut">{{isCut?'编辑':'完成'}}</text>
				</view>
				<view class="store-box">
					<view class="goodsInfo" v-for="(itemw,indexw) in goodsProducts" :key="indexw">
						<image src="/static/images/shoppingcart/select.png" v-if="itemw.checked == 2" class="checked-image"
							mode="" @tap="goodsCheck(indexw,itemw.checked)"></image>
						<image src="/static/images/shoppingcart/not_select.png" v-else class="checked-image" mode=""
							@tap="goodsCheck(indexw,itemw.checked)"></image>
						<view class="goodsInfo-right">
							<image :src="itemw.image" class="goods-image" mode=""></image>
							<view class="goodsInfo-box">
								<text class="goods-name">{{itemw.name}}</text>
								<view class="goods-box">
									<text class="goods-price">¥{{itemw.price}}</text>
									<view class="goods-num-box">
										<view class="goods-image" @tap="sub(indexw,itemw.num)">
											<text>-</text>
										</view>
										<view class="goods-num">
											<text>{{itemw.num}}</text>
										</view>
										<view class="goods-image" @tap="add(indexw,itemw.num)">
											<text>+</text>
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
				<view class="statistics-box">
					<view class="statistics">
						<view class="statistics-left" v-if="statisticsIndex" @tap="allCheck">
							<image src="/static/images/shoppingcart/select.png"  class="checked-image" mode="" >
							</image>
							<text>全选</text>
						</view>
						<view class="statistics-left" v-else @tap="allCheck">
							<image src="/static/images/shoppingcart/not_select.png"  class="checked-image" mode="" >
							</image>
							<text>全选</text>
						</view>
						<view class="statistics-right" v-if="isCut" @tap="accounts">
							<text>总计：</text><text class="text-color">¥{{total}}</text>
							<view class="btn">
								<text>结算</text>
							</view>
						</view>
						<view class="statistics-right" v-else @tap="delect">
							<view class="btn">
								<text>删除</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<slot></slot>
		</view>
	</view>
</template>

<script>
	import {
		getShoppingCart, deleteShoppingCart, updateShoppingCart
	} from "@/api/common.js"
	export default {
		data() {
			return {
				goodsProducts: [],
				userId:'',
				isEmpty: false,
				statisticsIndex:false,
				total:0,
				isCut:true
			}
		},
		
		onShow() {
			this.userId = uni.getStorageSync("userInfo").id;
			this.getInit()
		},
		methods: {
			getInit(){
				getShoppingCart(this.userId).then(res => {
					this.goodsProducts = res.data.list
				})
			},
			
			
			
			//商品选择
			goodsCheck(storeIndex, goodsChecked) {
					console.log(goodsChecked)
				if (goodsChecked == 1) {
					this.goodsProducts[storeIndex].checked = 2
				} else {
					this.goodsProducts[storeIndex].checked = 1
				}
			
				//判断是否全选
				let statisticsIndex = true
				this.goodsProducts.find((item,index)=>{
					if(item.checked == 1){
						statisticsIndex = false
					}
				})
				if(statisticsIndex == false){
					this.statisticsIndex = false
				}else{
					this.statisticsIndex = true
				}
				
				this.statistics()
			},
			
			//减少
			sub(storeIndex, goodsnum){
				if(goodsnum == 1){
					return
				}else{
					this.goodsProducts[storeIndex].num--
					this.updateShoppingCart(storeIndex);
				}
				this.statistics()
			},
			//增加
			add(storeIndex, goodsnum){
				this.goodsProducts[storeIndex].num++
				this.updateShoppingCart(storeIndex);
				this.statistics()
			},
			updateShoppingCart(goodsIndex){
				updateShoppingCart({
					num: this.goodsProducts[goodsIndex].num,
					id: this.goodsProducts[goodsIndex].id
				}).then(res => {
					this.statistics()
				})
			},
			
			//全选
			allCheck(){
				if(this.statisticsIndex){
					this.goodsProducts.find((item,index)=>{
						item.checked = 1
					})
					this.statisticsIndex = false
				}else{
					this.goodsProducts.find((item,index)=>{
						item.checked = 2
					})
					this.statisticsIndex = true
				}
				this.statistics()
			},
			//统计
			statistics(){
				let total = 0
				this.goodsProducts.find((item,index)=>{
					if(item.checked == 2){
						total = total + item.price*item.num
					}
				})
				this.total = total.toFixed(2)
			},
			
			cut(){
				this.isCut = !this.isCut
				this.statisticsIndex = true
				this.allCheck()
			},
			// 结算
			accounts(){
				
				let judge = this.judgeSelect()
				if(judge.length != 0){
					uni.navigateTo({
						url:'/pages/shoppingcart_xiadan/shoppingcart_xiadan?data='+JSON.stringify(judge)
					})
				}else{
					uni.showToast({
						title:'您当前未选择任何商品,结算失败',
						icon:'none'
					})
				}
			},
			// 删除
			delect(){
				let judge = this.judgeSelect()
				if(judge.length != 0){
					deleteShoppingCart(judge).then(res => {
						uni.showToast({
						    title: '删除成功',
						    icon: 'success',
						    duration: 2000
						}) 
						this.getInit()
					})
				}else{
					uni.showToast({
						title:'您当前未选择任何商品,删除失败',
						icon:'none'
					})
				}
				
			},
			judgeSelect(){
				let judge = []
				this.goodsProducts.find((item,index)=>{
					if(item.checked == 2){
						judge.push(item.id)
					}
				})
				return judge
			},
			// 返回上一层
			subNext(){
				 uni.navigateBack({
				    delta: 1, 
				  });
			}
		}
	}
</script>

<style>
	@import url(shoppingcart.css);
</style>
