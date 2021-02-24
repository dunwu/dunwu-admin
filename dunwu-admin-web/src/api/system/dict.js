import request from '@/utils/request'

export function getDicts(code) {
  return request({
    url: 'api/sys/dict' + '?code=' + code,
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/sys/dict',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/sys/dict/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sys/dict',
    method: 'put',
    data
  })
}

export default { add, edit, del }
