import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api/sys/dept/add',
    method: 'post',
    data
  })
}

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function del(ids) {
  return request({
    url: 'api/sys/dept/del',
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
    url: 'api/sys/dept/edit',
    method: 'post',
    data
  })
}

/**
 * 根据 params 条件，返回树形列表
 * @param params
 */
export function treeList(params) {
  return request({
    url: 'api/sys/dept/treeList',
    method: 'get',
    params
  })
}

export function superiorTreeList(idList) {
  const ids = idList.length || idList.length === 0 ? idList : Array.of(idList)
  return request({
    url: 'api/sys/dept/superior',
    method: 'post',
    data: ids
  })
}

export default { add, edit, del, treeList, superiorTreeList }
