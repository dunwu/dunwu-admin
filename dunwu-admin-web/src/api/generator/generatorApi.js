import request from '@/utils/request'

export function getAllTable() {
  return request({
    url: 'api/generator/tables/all',
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

export function saveTableColumns(data) {
  return request({
    url: 'api/generator/column/saveTableColumns',
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

export default { generator: generatorApi, saveTableColumns, sync }
