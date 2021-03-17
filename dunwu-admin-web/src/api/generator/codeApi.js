import request from '@/utils/request'

export function getAllTableInSchema(params) {
  return request({
    url: 'code/table/all/page',
    method: 'get',
    params
  })
}

export function syncTable(params) {
  return request({
    url: 'code/table/sync',
    method: 'get',
    params
  })
}

export function queryGlobalConfig(params) {
  return request({
    url: 'code/global/query',
    method: 'get',
    params
  })
}

export function saveGlobalConfig(data) {
  return request({
    url: 'code/global/save',
    method: 'post',
    data
  })
}

export function queryTableConfig(params) {
  return request({
    url: 'code/table/query',
    method: 'get',
    params
  })
}

export function saveTableConfig(data) {
  return request({
    url: 'code/table/save',
    method: 'post',
    data
  })
}

export function queryColumnConfig(params) {
  return request({
    url: 'code/column/query',
    method: 'get',
    params
  })
}

export function saveColumnConfig(data) {
  return request({
    url: 'code/column/saveBatch',
    method: 'post',
    data
  })
}

export function generateCode(params) {
  return request({
    url: 'code/generate/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function downloadCode(params) {
  return request({
    url: 'code/download/' + params.schemaName + '/' + params.tableName,
    method: 'get',
    responseType: 'blob'
  })
}

export function previewCode(params) {
  return request({
    url: 'code/preview/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export default {
  getAllTableInSchema,
  syncTable,
  generateCode,
  downloadCode,
  previewCode,
  queryGlobalConfig,
  saveGlobalConfig,
  queryTableConfig,
  saveTableConfig,
  queryColumnConfig,
  saveColumnConfig
}
