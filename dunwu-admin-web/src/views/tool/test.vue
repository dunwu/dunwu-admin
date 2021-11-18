<template>
  <el-form ref="form" :model="form" style="margin-top: 20px" label-width="80px">
    <el-form-item label="消息">
      <el-input v-model="form.content" style="width: 400px" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">发送</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import request from '@/utils/request'

export default {
  data() {
    return {
      form: {
        content: ''
      }
    }
  },
  methods: {
    onSubmit() {
      this.pushMsg()
    },
    pushMsg() {
      return request({
        url: '/sys/test/push',
        method: 'get',
        params: {
          content: this.form.content
        }
      })
    }
  }
}
</script>
