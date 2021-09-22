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
        <el-input v-model="form.name" style="width: 90%" placeholder="请输入名字" />
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input v-model="form.age" style="width: 90%" placeholder="请输入年龄" />
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-input v-model="form.avatar" style="width: 90%" placeholder="请输入头像" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="form.createTime"
          type="datetime"
          style="width: 90%"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="请选择创建时间"
        />
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
const defaultForm = { name: null, age: null, avatar: null, createTime: null }
export default {
  name: 'HelloForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        name: [
          { required: true, trigger: 'blur', message: '名字不能为空' },
          { type: 'string', trigger: 'blur', message: '名字必须为 string 类型' }
        ],
        age: [
          { required: true, trigger: 'blur', message: '年龄不能为空' },
          { type: 'string', trigger: 'blur', message: '年龄必须为 string 类型' }
        ],
        avatar: [
          { required: true, trigger: 'blur', message: '头像不能为空' },
          { type: 'string', trigger: 'blur', message: '头像必须为 string 类型' }
        ],
        createTime: [
          { required: true, trigger: 'blur', message: '创建时间不能为空' },
          { type: 'string', trigger: 'blur', message: '创建时间必须为 string 类型' }
        ]
      }
    }
  }
}
</script>

<style scoped></style>
