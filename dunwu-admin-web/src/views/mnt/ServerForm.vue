<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="名称">
        <el-input v-model="form.name" style="width: 90%" placeholder="请输入名称" />
      </el-form-item>
      <el-form-item label="IP地址">
        <el-input v-model="form.ip" style="width: 90%" placeholder="请输入IP地址" />
      </el-form-item>
      <el-form-item label="端口">
        <el-input v-model="form.port" style="width: 90%" placeholder="请输入端口" />
      </el-form-item>
      <el-form-item label="账号">
        <el-input v-model="form.account" style="width: 90%" placeholder="请输入账号" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" style="width: 70%" placeholder="请输入密码" show-password />
        <el-button :loading="testLoading" type="primary" @click="testConnectServer">
          测试连接
        </el-button>
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
import ServerApi from './ServerApi'

/**
 * 表单实体默认值
 */
const defaultForm = { account: null, ip: null, name: null, password: null, port: null }
export default {
  name: 'ServerForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      testLoading: false,
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        name: [{ required: true, trigger: 'blur', message: '名称不能为空' }],
        ip: [{ required: true, trigger: 'blur', message: 'IP地址不能为空' }],
        port: [{ required: true, trigger: 'blur', message: '端口不能为空' }],
        account: [{ required: true, trigger: 'blur', message: '账号不能为空' }],
        password: [{ required: true, trigger: 'blur', message: '密码不能为空' }]
      }
    }
  },
  methods: {
    testConnectServer() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.testLoading = true
          ServerApi.test(this.form)
            .then(res => {
              this.testLoading = false
              this.$notify({
                title: res ? '连接测试成功' : '连接测试失败',
                type: res ? 'success' : 'error',
                duration: 2500
              })
            })
            .catch(() => {
              this.testLoading = false
            })
        }
      })
    }
  }
}
</script>

<style scoped></style>
