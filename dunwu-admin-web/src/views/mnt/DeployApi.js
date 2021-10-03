import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'mnt/deploy/add',
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
    url: 'mnt/deploy/del/batch',
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
    url: 'mnt/deploy/edit',
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
    url: 'mnt/deploy/list',
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
    url: 'mnt/deploy/page',
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
    url: 'mnt/deploy/' + id,
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
    url: 'mnt/deploy/export/list',
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
    url: 'mnt/deploy/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

export function upload(data) {
  return request({
    url: 'mnt/deploy/upload',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 获取服务运行状态
 * @param data
 */
export function getServerStatus(data) {
  return request({
    url: 'mnt/deploy/getServerStatus',
    method: 'post',
    data
  })
}

/**
 * 回滚服务
 * @param data
 */
export function rollbackServer(data) {
  return request({
    url: 'mnt/deploy/rollbackServer',
    method: 'post',
    data
  })
}

/**
 * 回滚服务
 * @param data
 */
export function startServer(data) {
  return request({
    url: 'mnt/deploy/startServer',
    method: 'post',
    data
  })
}

/**
 * 回滚服务
 * @param data
 */
export function stopServer(data) {
  return request({
    url: 'mnt/deploy/stopServer',
    method: 'post',
    data
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
  upload,
  getServerStatus,
  rollbackServer,
  startServer,
  stopServer
}
