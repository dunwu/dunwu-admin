import request from '@/utils/request'

export function getErrDetail(id) {
  return request({
    url: 'sys/log/error/' + id,
    method: 'get'
  })
}

export function delAllError() {
  return request({
    url: 'sys/log/del/error',
    method: 'delete'
  })
}

export function delAllInfo() {
  return request({
    url: 'sys/log/del/info',
    method: 'delete'
  })
}
