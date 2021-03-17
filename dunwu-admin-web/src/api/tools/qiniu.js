import request from '@/utils/request'

export function get() {
  return request({
    url: 'qiNiuContent/config',
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'qiNiuContent/config',
    data,
    method: 'put'
  })
}

export function download(id) {
  return request({
    url: 'qiNiuContent/download/' + id,
    method: 'get'
  })
}

export function sync() {
  return request({
    url: 'qiNiuContent/synchronize',
    method: 'post'
  })
}

export function del(ids) {
  return request({
    url: 'qiNiuContent',
    method: 'delete',
    data: ids
  })
}

export default { del, download, sync }
