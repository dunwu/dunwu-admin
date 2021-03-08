import request from '@/utils/request'

export function getAllTableInSchema(params) {
  return request({
    url: 'api/code/table/all/page',
    method: 'get',
    params
  })
}

export function syncTables(data) {
  return request({
    url: 'api/code/table/sync',
    method: 'post',
    data
  })
}

export function findGlobalConfig() {
  return request({
    url: 'api/code/global/find',
    method: 'get'
  })
}

export function saveGlobalConfig(data) {
  return request({
    url: 'api/code/global/save',
    method: 'post',
    data
  })
}

export function findTableConfig(params) {
  return request({
    url: 'api/code/table/find/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function saveTableConfig(data) {
  return request({
    url: 'api/code/table/save',
    method: 'post',
    data
  })
}

export function findColumnConfig(params) {
  return request({
    url: 'api/code/column/find/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function saveColumnConfig(data) {
  return request({
    url: 'api/code/column/saveBatch',
    method: 'post',
    data
  })
}

export function generateCode(params) {
  return request({
    url: 'api/code/generate/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function downloadCode(params) {
  return request({
    url: 'api/code/download/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function previewCode(params) {
  return request({
    url: 'api/code/preview/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export default {
  getAllTableInSchema,
  syncTables,
  generateCode,
  downloadCode,
  previewCode,
  findGlobalConfig,
  saveGlobalConfig,
  findTableConfig,
  saveTableConfig,
  findColumnConfig,
  saveColumnConfig
}
