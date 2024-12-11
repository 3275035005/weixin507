import request from '@/utils/request'

export function pageQuery(page, limit, data) {
  return request({
    url: `/post-classify/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}
export function deleteById(id) {
  return request({
    url: `/post-classify/deleteById/${id}`,
    method: 'delete'
  })
}
export function insert(data){
  return request({
    url: `/post-classify/insert`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `/post-classify/update`,
    method: 'put',
    data: data
  })
}

export function getPostClassifyAll() {
  return request({
    url: `/post-classify/getPostClassifyAll`,
    method: 'get'
  })
}
