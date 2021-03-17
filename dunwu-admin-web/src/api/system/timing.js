import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'jobs',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'jobs',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'jobs',
    method: 'put',
    data
  })
}

export function updateIsPause(id) {
  return request({
    url: 'jobs/' + id,
    method: 'put'
  })
}

export function execution(id) {
  return request({
    url: 'jobs/exec/' + id,
    method: 'put'
  })
}

export default { del, updateIsPause, execution, add, edit }
