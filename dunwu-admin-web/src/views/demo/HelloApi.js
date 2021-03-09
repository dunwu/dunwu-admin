import qs from 'qs'
import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api/demo/hello/add',
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
    url: 'api/demo/hello/del/batch',
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
    url: 'api/demo/hello/edit',
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
    url: 'api/demo/hello/list',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，查询匹配条件的列表
 * @param data
 * @returns {*}
 */
export function exportList(data) {
  return request({
    url: 'api/demo/hello/export/list',
    method: 'post',
    responseType: 'blob',
    data
  })
}

/**
 * 根据 params 条件，导出符合查询条件的分页数据
 * @param params
 * @returns {*}
 */
export function exportPage(params) {
  return request({
    url: 'api/demo/hello/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

export default { add, edit, delBatch, list, exportList, exportPage }
