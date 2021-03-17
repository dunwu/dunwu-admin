<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="名字" prop="name">
        <el-input v-model="form.name" style="width: 90%" />
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input-number v-model="form.age" style="width: 90%" />
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-input v-model="form.avatar" style="width: 90%" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="form.createTime" type="datetime" style="width: 90%" />
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

const defaultForm = { name: null, age: null, avatar: null, createTime: null, }
export default {
  name: 'HelloForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      rules: {
        name: [
          { required: true, trigger: 'blur', type: 'string' }
        ],
        age: [
          { required: true, trigger: 'blur', type: 'number' }
        ],
        avatar: [
          { required: true, trigger: 'blur', type: 'string' }
        ],
      }
    }
  }
}
</script>

<style scoped></style>
