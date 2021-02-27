import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/add',
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
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/del/batch',
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
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/edit',
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
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/list',
    method: 'get',
    params
  })
}

export default { add, edit, delBatch, list }
