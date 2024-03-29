import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'cas/dept/add',
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
    url: 'cas/dept/del/batch',
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
    url: 'cas/dept/edit',
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
    url: 'cas/dept/list',
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
    url: 'cas/dept/page',
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
    url: 'cas/dept/' + id,
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
    url: 'cas/dept/export/list',
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
    url: 'cas/dept/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

/**
 * 根据 params 条件，返回树形列表
 * @param params
 * @returns {*}
 */
export function treeList(params) {
  return request({
    url: 'cas/dept/treeList',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，返回同级和上级的树形列表
 * @param idList
 * @returns {*}
 */
export function superiorTreeList(idList) {
  const ids = idList.length || idList.length === 0 ? idList : Array.of(idList)
  return request({
    url: 'cas/dept/superiorTreeList',
    method: 'post',
    data: ids
  })
}

export default {
  add,
  edit,
  delBatch,
  list,
  page,
  getById,
  exportList,
  exportPage,
  treeList,
  superiorTreeList
}
