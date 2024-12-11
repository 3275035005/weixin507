import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/post/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/post/deleteById/${id}`,
    method: 'delete'
  })
}
