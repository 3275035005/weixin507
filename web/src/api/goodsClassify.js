import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/goods-classify/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/goods-classify/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/goods-classify/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/goods-classify/update`,
    method: 'put',
    data: data
  })
}

export function getGoodsClassifyAll() {
  return request({
    url: `/goods-classify/getGoodsClassifyAll`,
    method: 'get'
  })
}
