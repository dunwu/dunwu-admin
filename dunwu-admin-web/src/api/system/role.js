import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'sys/role/add',
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
    url: 'sys/role/del/batch',
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
    url: 'sys/role/edit',
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
    url: 'sys/role/list',
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
    url: 'sys/role/page',
    method: 'get',
    params
  })
}

/**
 * 根据指定 id 列表，导出相应数据
 * @param ids
 * @returns {*}
 */
export function exportList(ids) {
  return request({
    url: 'sys/role/export/list',
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
    url: 'sys/role/export/page',
    method: 'get',
    responseType: 'blob',
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
    url: 'sys/role/' + id,
    method: 'get'
  })
}

export function getLevel() {
  return request({
    url: 'sys/role/level',
    method: 'get'
  })
}

export function editMenu(data) {
  return request({
    url: 'sys/role/menu',
    method: 'put',
    data
  })
}

export default { add, edit, delBatch, list, page, exportList, exportPage, getById, editMenu, getLevel }
