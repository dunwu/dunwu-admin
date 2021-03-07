import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api/mnt/database/add',
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
    url: 'api/mnt/database/del/batch',
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
    url: 'api/mnt/database/edit',
    method: 'post',
    data
  })
}

export function testDbConnection(data) {
  return request({
    url: 'api/mnt/database/testConnect',
    method: 'post',
    data
  })
}

export default { add, edit, delBatch, testDbConnection }
