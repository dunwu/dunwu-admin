import request from '@/utils/request'

export function getAllTable() {
  return request({
    url: 'api/generator/table/all',
    method: 'get'
  })
}

export function generatorApi(tableName, type) {
  return request({
    url: 'api/generator/' + tableName + '/' + type,
    method: 'post',
    responseType: type === 2 ? 'blob' : ''
  })
}

export function saveBatch(data) {
  return request({
    url: 'api/generator/column/saveBatch',
    method: 'post',
    data
  })
}

export function sync(tables) {
  return request({
    url: 'api/generator/column/sync',
    method: 'post',
    data: { tables }
  })
}

export function findGlobalConfig() {
  return request({
    url: 'api/generator/global/find',
    method: 'get'
  })
}

export function saveGlobalConfig(data) {
  return request({
    url: 'api/generator/global/save',
    method: 'post',
    data
  })
}

export function findTableConfig(params) {
  return request({
    url: 'api/generator/table/find/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function saveTableConfig(data) {
  return request({
    url: 'api/generator/table/save',
    method: 'post',
    data
  })
}

export function previewGenerateCode(params) {
  return request({
    url: 'api/generator/preview/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export function downloadGenerateCode(params) {
  return request({
    url: 'api/generator/download/' + params.schemaName + '/' + params.tableName,
    method: 'get'
  })
}

export default {
  generator: generatorApi,
  saveBatch,
  sync,
  previewGenerateCode,
  downloadGenerateCode,
  findGlobalConfig,
  saveGlobalConfig,
  findTableConfig,
  saveTableConfig
}
