<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="文件名" prop="fileName">
        <el-input v-model="form.fileName" style="width: 90%" placeholder="请输入文件名" />
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
      <el-form-item label="文件大小" prop="size">
        <el-input v-model="form.size" style="width: 90%" placeholder="请输入文件大小" />
      </el-form-item>
      <el-form-item label="文件扩展名" prop="extension">
        <el-input v-model="form.extension" style="width: 90%" placeholder="请输入文件扩展名" />
      </el-form-item>
      <el-form-item label="文件实际类型" prop="contentType">
        <el-input v-model="form.contentType" style="width: 90%" placeholder="请输入文件实际类型" />
      </el-form-item>
      <el-form-item label="文件存储服务类型">
        <el-input v-model="form.storeType" style="width: 90%" placeholder="请输入文件存储服务类型" />
      </el-form-item>
      <el-form-item label="文件存储路径">
        <el-input v-model="form.storeUrl" style="width: 90%" placeholder="请输入文件存储路径" />
      </el-form-item>
      <el-form-item label="文件访问路径" prop="accessUrl">
        <el-input v-model="form.accessUrl" style="width: 90%" placeholder="请输入文件访问路径" />
      </el-form-item>
      <el-form-item label="创建者">
        <el-input v-model="form.createBy" style="width: 90%" placeholder="请输入创建者" />
      </el-form-item>
      <el-form-item label="更新者">
        <el-input v-model="form.updateBy" style="width: 90%" placeholder="请输入更新者" />
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
const defaultForm = { fileName: null, namespace: null, tag: null, originName: null, size: null, extension: null, contentType: null, storeType: null, storeUrl: null, accessUrl: null, createBy: null, updateBy: null, createTime: null, updateTime: null, }
export default {
  name: 'FileInfoForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        fileName: [
          { required: true, trigger: 'blur', message: '文件名不能为空' },
          { type: 'string', trigger: 'blur', message: '文件名必须为 string 类型' }
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
