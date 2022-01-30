import request from '@/utils/request'

export function getAllTableInSchema(params) {
  return request({
    url: 'code/table/all/page',
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

export function queryTableConfig(params) {
  return request({
    url: 'code/table/query',
    method: 'get',
    params
  })
}

export function queryColumnConfig(params) {
  return request({
    url: 'code/column/query',
    method: 'get',
    params
  })
}

export function syncQueryColumnConfig(params) {
  return request({
    url: 'code/column/query/sync',
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

export function saveTableConfig(data) {
  return request({
    url: 'code/table/save',
    method: 'post',
    data
  })
}

export function saveColumnConfig(data) {
  return request({
    url: 'code/column/saveBatch',
    method: 'post',
    data
  })
}

export function previewCode(params) {
  return request({
    url: 'code/preview',
    method: 'get',
    params
  })
}

export function generateCode(params) {
  return request({
    url: 'code/generate',
    method: 'get',
    params
  })
}

export function downloadCode(params) {
  return request({
    url: 'code/download',
    method: 'get',
    params,
    responseType: 'blob'
  })
}

export function getCodeConfigInfo(params) {
  return request({
    url: 'code/config/info',
    method: 'get',
    params
  })
}

export default {
  getAllTableInSchema,
  queryGlobalConfig,
  queryTableConfig,
  queryColumnConfig,
  syncQueryColumnConfig,
  saveGlobalConfig,
  saveTableConfig,
  saveColumnConfig,
  previewCode,
  generateCode,
  downloadCode,
  getCodeConfigInfo
}
