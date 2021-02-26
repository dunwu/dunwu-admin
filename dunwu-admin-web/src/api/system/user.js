import request from '@/utils/request'
import { encrypt } from '@/utils/rsaEncrypt'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api/sys/user/add',
    method: 'post',
    data
  })
}

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function del(ids) {
  return request({
    url: 'api/sys/user/del/batch',
    method: 'post',
    data: ids
  })
}

/**
 * 修改一条记录
 * @param data
 * @returns {*}
 */
export function edit(data) {
  return request({
    url: 'api/sys/user/edit',
    method: 'post',
    data
  })
}

/**
 * 修改用户：个人中心
 * @param data
 * @returns {*}
 */
export function editCenter(data) {
  return request({
    url: 'api/sys/user/edit/center',
    method: 'post',
    data
  })
}

/**
 * 修改用户密码
 * @param data
 * @returns {*}
 */
export function editPassword(data) {
  return request({
    url: 'api/sys/user/edit/password',
    method: 'post',
    data: {
      oldPass: encrypt(data.oldPass),
      newPass: encrypt(data.newPass)
    }
  })
}

/**
 * 修改用户邮箱
 * @param data
 * @returns {*}
 */
export function editEmail(data) {
  return request({
    url: 'api/sys/user/edit/email/' + data.code,
    method: 'post',
    data: {
      password: encrypt(data.pass),
      email: data.email
    }
  })
}

export default { add, edit, del, editCenter, editPassword, editEmail }
