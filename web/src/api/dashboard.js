import request from "@/utils/request";


export function getHistogram(date) {
  return request({
    url: `/dashboard/getHistogram?date=` + date,
    method: 'get'
  })
}

export function getInfo() {
  return request({
    url: `/dashboard/getInfo`,
    method: 'get'
  })
}

