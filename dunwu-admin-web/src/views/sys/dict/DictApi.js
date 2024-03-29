import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'sys/dict/add',
    method: 'post',
    data
  })
}

/**
 * 修改一条记录
 * @param data
 * @returns {*}
 */
export function edit(data) {
  return request({
    url: 'sys/dict/edit',
    method: 'post',
    data
  })
}

/**
 * 根据 ID 删除一条记录
 * @param id
 * @returns {*}
 */
export function del(id) {
  return request({
    url: 'sys/dict/del/' + id,
    method: 'post'
  })
}

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function delBatch(ids) {
  return request({
    url: 'sys/dict/del/batch',
    method: 'post',
    data: ids
  })
}

/**
 * 根据 params 条件，查询匹配条件的列表
 * @param params
 * @returns {*}
 */
export function list(params) {
  return request({
    url: 'sys/dict/list',
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
    url: 'sys/dict/page',
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
    url: 'sys/dict/' + id,
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
    url: 'sys/dict/export/list',
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
    url: 'sys/dict/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

/**
 * 修改一条记录
 * @param data
 * @returns {*}
 */
export function saveDictWithOptions(data) {
  return request({
    url: 'sys/dict/save/dictWithOptions',
    method: 'post',
    data
  })
}

/**
 * 根据指定 id 列表，导出相应数据
 * @param ids
 * @returns {*}
 */
export function generateEnumById(id) {
  return request({
    url: 'sys/dict/generate/enum/' + id,
    method: 'get',
    responseType: 'blob'
  })
}

export default {
  add,
  edit,
  del,
  delBatch,
  list,
  page,
  getById,
  exportList,
  exportPage,
  saveDictWithOptions,
  generateEnumById
}
