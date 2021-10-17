import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'cas/menu/add',
    method: 'post',
    data
  })
}

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function delBatch(ids) {
  return request({
    url: 'cas/menu/del/batch',
    method: 'post',
    data: ids
  })
}

/**
 * 修改一条记录
 * @param data
 * @returns {*}
 */
export function edit(data) {
  return request({
    url: 'cas/menu/edit',
    method: 'post',
    data
  })
}

/**
 * 根据 params 条件，查询匹配条件的列表
 * @param params
 * @returns {*}
 */
export function list(params) {
  return request({
    url: 'cas/menu/list',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，查询匹配条件的分页列表
 * @param params
 * @returns {*}
 */
export function page(params) {
  return request({
    url: 'cas/menu/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 查询记录
 * @param id
 * @returns {*}
 */
export function getById(id) {
  return request({
    url: 'cas/menu/' + id,
    method: 'get'
  })
}

/**
 * 根据指定 id 列表，导出相应数据
 * @param ids
 * @returns {*}
 */
export function exportList(ids) {
  return request({
    url: 'cas/menu/export/list',
    method: 'post',
    responseType: 'blob',
    data: ids
  })
}

/**
 * 根据 params 条件，导出符合查询条件的分页数据
 * @param params
 * @returns {*}
 */
export function exportPage(params) {
  return request({
    url: 'cas/menu/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

/**
 * 根据 params 条件，返回树形列表
 * @param params
 */
export function treeList(params) {
  return request({
    url: 'cas/menu/treeList',
    method: 'get',
    params
  })
}

export function treeListByPid(id) {
  return request({
    url: 'cas/menu/treeListByPid',
    method: 'get',
    params: { id }
  })
}

/**
 * 根据 params 条件，返回同级和上级的树形列表
 * @param pid
 */
export function superiorTreeList(pid) {
  return request({
    url: 'cas/menu/superiorTreeList',
    method: 'get',
    params: { pid }
  })
}

export function childrenIds(id) {
  return request({
    url: 'cas/menu/childrenIds?id=' + id,
    method: 'get'
  })
}

export function buildMenus() {
  return request({
    url: 'cas/menu/mine',
    method: 'get'
  })
}

export default {
  add,
  edit,
  delBatch,
  list,
  page,
  exportList,
  exportPage,
  treeList,
  treeListByPid,
  superiorTreeList,
  childrenIds
}
