import request from '@/utils/request'

export function del(keys) {
  return request({
    url: 'auth/online/del/batch',
    method: 'post',
    data: keys
  })
}
