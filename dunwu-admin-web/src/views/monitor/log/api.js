import request from '@/utils/request'

export function getErrDetail(id) {
  return request({
    url: 'monitor/log/error/' + id,
    method: 'get'
  })
}

export function delAllError() {
  return request({
    url: 'monitor/log/del/error',
    method: 'delete'
  })
}

export function delAllInfo() {
  return request({
    url: 'monitor/log/del/info',
    method: 'delete'
  })
}
