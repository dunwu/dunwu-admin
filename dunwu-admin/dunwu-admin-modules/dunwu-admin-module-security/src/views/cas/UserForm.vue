<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="部门ID">
        <el-input v-model="form.deptId" style="width: 90%" placeholder="请输入部门ID" />
      </el-form-item>
      <el-form-item label="岗位ID">
        <el-input v-model="form.jobId" style="width: 90%" placeholder="请输入岗位ID" />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="form.username" style="width: 90%" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" style="width: 90%" placeholder="请输入昵称" />
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.gender" style="width: 90%" placeholder="请输入性别" />
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="form.phone" style="width: 90%" placeholder="请输入手机号码" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" style="width: 90%" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="头像地址">
        <el-input v-model="form.avatar" style="width: 90%" placeholder="请输入头像地址" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" style="width: 90%" placeholder="请输入密码" />
      </el-form-item>
      <el-form-item label="是否为admin账号">
        <el-input v-model="form.admin" style="width: 90%" placeholder="请输入是否为admin账号" />
      </el-form-item>
      <el-form-item label="状态：1启用、0禁用">
        <el-input v-model="form.enabled" style="width: 90%" placeholder="请输入状态：1启用、0禁用" />
      </el-form-item>
      <el-form-item label="创建者">
        <el-input v-model="form.createBy" style="width: 90%" placeholder="请输入创建者" />
      </el-form-item>
      <el-form-item label="更新着">
        <el-input v-model="form.updateBy" style="width: 90%" placeholder="请输入更新着" />
      </el-form-item>
      <el-form-item label="修改密码的时间">
        <el-input v-model="form.pwdResetTime" style="width: 90%" placeholder="请输入修改密码的时间" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="form.createTime" style="width: 90%" placeholder="请输入创建时间" />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-input v-model="form.updateTime" style="width: 90%" placeholder="请输入更新时间" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { form } from '@crud/crud'

/**
 * 表单实体默认值
 */
const defaultForm = { deptId: null, jobId: null, username: null, nickname: null, gender: null, phone: null, email: null, avatar: null, password: null, admin: null, enabled: null, createBy: null, updateBy: null, pwdResetTime: null, createTime: null, updateTime: null, }
export default {
  name: 'UserForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '用户名不能为空' },
          { type: 'string', trigger: 'blur', message: '用户名必须为 string 类型' }
        ],
        nickname: [
          { required: true, trigger: 'blur', message: '昵称不能为空' },
          { type: 'string', trigger: 'blur', message: '昵称必须为 string 类型' }
        ],
      }
    }
  }
}
</script>

<style scoped></style>
