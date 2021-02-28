import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api/generator/table/add',
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
    url: 'api/generator/table/edit',
    method: 'post',
    data
  })
}

export function find(params) {
  return request({
    url: 'api/generator/table/find',
    method: 'get',
    params
  })
}

export default { add, edit, find }
