import {
	request
} from '../utils/request.js'
import {
	appConfig
} from '../config/config.js'


export function getPostClassifyAll() {
	return request.get(appConfig.WEB_API + '/getPostClassifyAll', null)
}

export function getPostList(classifyId, value) {
	return request.get(appConfig.WEB_API + '/getPostList?classifyId='+classifyId+'&title='+value, null)
}

export function getPostAndUserById(id, userId) {
	return request.get(appConfig.WEB_API + '/getPostAndUserById/'+id +"/"+userId, null)
}

export function sendView(id) {
	return request.post(appConfig.WEB_API + '/sendView/'+id, {})
}

export function sendPost(data) {
	return request.post(appConfig.WEB_API + '/sendPost', data)
}

export function login(data) {
	return request.post(appConfig.WEB_API + '/login', data)
}



export function getGoodsClassifyAll() {
	return request.get(appConfig.WEB_API + '/getGoodsClassifyAll', null)
}

export function getGoodsList(data) {
	return request.get(appConfig.WEB_API + '/getGoodsList', data)
}
export function getGoodsAndUserById(id) {
	return request.get(appConfig.WEB_API + '/getGoodsAndUserById/'+id, null)
}

export function getUserChatList(id) {
	return request.get(appConfig.WEB_API + '/getUserChatList/'+id, null)
}

export function getUserChatBySendUserAndReceiveUser(sendUserId, receiveUserId) {
	return request.get(appConfig.WEB_API + '/getUserChatBySendUserAndReceiveUser/'+sendUserId+'/'+receiveUserId, null)
}

export function sendUserChat(data) {
	return request.post(appConfig.WEB_API + '/sendUserChat', data)
}

export function getPostComment(id) {
	return request.get(appConfig.WEB_API + '/getPostComment/'+id, null)
}

export function sendPostComment(data) {
	return request.post(appConfig.WEB_API + '/sendPostComment', data)
}

export function postPraise(data) {
	return request.post(appConfig.WEB_API + '/postPraise', data)
}


export function postCollect(data) {
	return request.post(appConfig.WEB_API + '/postCollect', data)
}

export function getOrderInfoList(userId, state) {
	return request.get(appConfig.WEB_API + '/getOrderInfoList/'+userId+'/'+state, null)
}

export function sendOrder(data) {
	return request.post(appConfig.WEB_API + '/sendOrder', data)
}

export function sendOrders(data) {
	return request.post(appConfig.WEB_API + '/sendOrders', data)
}
export function successOrder(orderId) {
	return request.post(appConfig.WEB_API + '/successOrder/'+orderId, null)
}
export function getPostListByUserId(userId) {
	return request.get(appConfig.WEB_API + '/getPostListByUserId/'+userId, null)
}

export function getPostCollectByUserId(userId) {
	return request.get(appConfig.WEB_API + '/getPostCollectByUserId/'+userId, null)
}

export function getGoodsListByUserId(userId) {
	return request.get(appConfig.WEB_API + '/getGoodsListByUserId/'+userId, null)
}

export function getUserInfoById(userId) {
	return request.get(appConfig.WEB_API + '/getUserInfoById/'+userId)
}

export function updatePassword(data) {
	return request.post(appConfig.WEB_API + '/updatePassword', data)
}

export function updateUserInfo(data) {
	return request.post(appConfig.WEB_API + '/updateUserInfo', data)
}

export function sendGoods(data) {
	return request.post(appConfig.WEB_API + '/sendGoods', data)
}
export function register(data) {
	return request.post(appConfig.WEB_API + '/register', data)
}

// 查询我的购物车
export function getShoppingCart(id) {
	return request.get(appConfig.WEB_API + '/getShoppingCart/'+id,"")
}

// 加入购物车
export function addShoppingCart(data) {
	return request.post(appConfig.WEB_API + '/addShoppingCart', data)
}

// 修改我的购物车 +或-商品
export function updateShoppingCart(data) {
	return request.post(appConfig.WEB_API + '/updateShoppingCart', data)
}

// 删除购物车
export function deleteShoppingCart(data) {
	return request.post(appConfig.WEB_API + '/deleteShoppingCart', data)
}
// 通过商品id集合查询商品信息
export function getGoodsByIds(data) {
	return request.post(appConfig.WEB_API + '/getGoodsByIds', data)
}

// 查询我的点赞帖子
export function getPostPraiseByUserId(userId) {
	return request.get(appConfig.WEB_API + '/getPostPraiseByUserId/'+userId, null)
}

