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

export default { generator: generatorApi, saveBatch, sync }
