import request from '@/utils/request'

// 获取所有的Role
export function getAll() {
  return request({
    url: 'api/sys/role/list',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/sys/role',
    method: 'post',
    data
  })
}

export function get(id) {
  return request({
    url: 'api/sys/role/' + id,
    method: 'get'
  })
}

export function getLevel() {
  return request({
    url: 'api/sys/role/level',
    method: 'get'
  })
}

export function del(ids) {
  return request({
    url: 'api/sys/role',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sys/role',
    method: 'put',
    data
  })
}

export function editMenu(data) {
  return request({
    url: 'api/sys/role/menu',
    method: 'put',
    data
  })
}

export default { add, edit, del, get, editMenu, getLevel }
