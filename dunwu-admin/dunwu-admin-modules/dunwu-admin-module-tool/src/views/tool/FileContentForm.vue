<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="实际文件名" prop="fileName">
        <el-input v-model="form.fileName" style="width: 90%" placeholder="请输入实际文件名" />
      </el-form-item>
      <el-form-item label="命名空间。一般对应业务系统">
        <el-input v-model="form.namespace" style="width: 90%" placeholder="请输入命名空间。一般对应业务系统" />
      </el-form-item>
      <el-form-item label="标签。供业务系统使用">
        <el-input v-model="form.tag" style="width: 90%" placeholder="请输入标签。供业务系统使用" />
      </el-form-item>
      <el-form-item label="源文件名" prop="originName">
        <el-input v-model="form.originName" style="width: 90%" placeholder="请输入源文件名" />
      </el-form-item>
      <el-form-item label="文件存储服务类型">
        <el-input v-model="form.storeType" style="width: 90%" placeholder="请输入文件存储服务类型" />
      </el-form-item>
      <el-form-item label="文件存储路径">
        <el-input v-model="form.storeUrl" style="width: 90%" placeholder="请输入文件存储路径" />
      </el-form-item>
      <el-form-item label="文件内容" prop="content">
        <el-input v-model="form.content" style="width: 90%" placeholder="请输入文件内容" />
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
const defaultForm = { fileName: null, namespace: null, tag: null, originName: null, storeType: null, storeUrl: null, content: null, }
export default {
  name: 'FileContentForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        fileName: [
          { required: true, trigger: 'blur', message: '实际文件名不能为空' },
          { type: 'string', trigger: 'blur', message: '实际文件名必须为 string 类型' }
        ],
        namespace: [
          { required: true, trigger: 'blur', message: '命名空间。一般对应业务系统不能为空' },
          { type: 'string', trigger: 'blur', message: '命名空间。一般对应业务系统必须为 string 类型' }
        ],
        tag: [
          { required: true, trigger: 'blur', message: '标签。供业务系统使用不能为空' },
          { type: 'string', trigger: 'blur', message: '标签。供业务系统使用必须为 string 类型' }
        ],
      }
    }
  }
}
</script>

<style scoped></style>
