import request from '@/utils/request'

export function getMenusTree(pid) {
  return request({
    url: 'api/sys/menu/list?pid=' + pid,
    method: 'get'
  })
}

export function getMenus(params) {
  return request({
    url: 'api/sys/menu/list',
    method: 'get',
    params
  })
}

export function getMenuSuperior(ids) {
  const data = ids.length || ids.length === 0 ? ids : Array.of(ids)
  return request({
    url: 'api/sys/menu/superior',
    method: 'post',
    data
  })
}

export function getChild(id) {
  return request({
    url: 'api/sys/menu/child?id=' + id,
    method: 'get'
  })
}

export function buildMenus() {
  return request({
    url: 'api/sys/menu/build',
    method: 'get'
  })
}

export function add(data) {
  return request({
    url: 'api/sys/menu',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/sys/menu',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sys/menu',
    method: 'put',
    data
  })
}

export default { add, edit, del, getMenusTree, getMenuSuperior, getMenus, getChild }
