import request from '@/utils/request'

export function get() {
  return request({
    url: 'aliPay',
    method: 'get'
  })
}

export function update(data) {
  return request({
    url: 'aliPay',
    data,
    method: 'put'
  })
}

// 支付
export function toAliPay(url, data) {
  return request({
    url: '' + url,
    data,
    method: 'post'
  })
}
