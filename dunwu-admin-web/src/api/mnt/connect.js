import request from '@/utils/request'

export function testServerConnect(data) {
  return request({
    url: 'serverDeploy/testConnect',
    method: 'post',
    data
  })
}
