import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/add',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/del/batch',
    method: 'post',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/${table.entityPath}/edit',
    method: 'post',
    data
  })
}

export default { add, edit, del }
