<template>
  <el-dialog
    v-el-drag-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="应用名称" prop="name">
        <el-input v-model="form.name" style="width: 90%" placeholder="请输入应用名称" maxlength="100" show-word-limit />
      </el-form-item>
      <el-form-item label="上传路径" prop="uploadPath">
        <el-input
          v-model="form.uploadPath"
          style="width: 90%"
          placeholder="请输入上传路径"
          maxlength="255"
          show-word-limit
        />
      </el-form-item>
      <el-form-item label="部署路径" prop="deployPath">
        <el-input
          v-model="form.deployPath"
          style="width: 90%"
          placeholder="请输入部署路径"
          maxlength="255"
          show-word-limit
        />
      </el-form-item>
      <el-form-item label="备份路径" prop="backupPath">
        <el-input
          v-model="form.backupPath"
          style="width: 90%"
          placeholder="请输入备份路径"
          maxlength="255"
          show-word-limit
        />
      </el-form-item>
      <el-form-item label="应用端口" prop="port">
        <el-input v-model="form.port" style="width: 90%" placeholder="请输入应用端口" />
      </el-form-item>
      <el-form-item label="启动脚本">
        <el-input v-model="form.startScript" style="width: 90%" placeholder="请输入启动脚本" type="textarea" rows="3" />
      </el-form-item>
      <el-form-item label="部署脚本">
        <el-input
          v-model="form.deployScript"
          style="width: 90%"
          placeholder="请输入部署脚本"
          type="textarea"
          rows="3"
        />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="form.note"
          style="width: 90%"
          placeholder="请输入备注"
          type="textarea"
          rows="3"
          maxlength="150"
          show-word-limit
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
import ElDragDialog from '@/directive/el-drag-dialog'

/**
 * 表单实体默认值
 */
const defaultForm = {
  name: null,
  uploadPath: null,
  deployPath: null,
  backupPath: null,
  port: null,
  startScript: null,
  deployScript: null,
  note: null,
  createBy: null,
  updateBy: null,
  createTime: null,
  updateTime: null
}
export default {
  name: 'AppForm',
  directives: { ElDragDialog },
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        name: [{ required: true, trigger: 'blur', message: '应用名称不能为空' }],
        uploadPath: [{ required: true, trigger: 'blur', message: '上传路径不能为空' }],
        deployPath: [{ required: true, trigger: 'blur', message: '部署路径不能为空' }],
        backupPath: [{ required: true, trigger: 'blur', message: '备份路径不能为空' }],
        port: [{ required: true, trigger: 'blur', message: '应用端口不能为空' }]
      }
    }
  }
}
</script>

<style scoped></style>
