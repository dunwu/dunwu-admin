<template>
  <el-dialog
    v-el-drag-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="应用编码">
        <el-input v-model="form.appCode" style="width: 90%" placeholder="请输入应用编码" />
      </el-form-item>
      <el-form-item label="模块编码">
        <el-input v-model="form.moduleCode" style="width: 90%" placeholder="请输入模块编码" />
      </el-form-item>
      <el-form-item label="命名空间">
        <el-input v-model="form.namespace" style="width: 90%" placeholder="请输入命名空间" />
      </el-form-item>
      <el-form-item label="配置项编码" prop="code">
        <el-input v-model="form.code" style="width: 90%" placeholder="请输入配置项编码" />
      </el-form-item>
      <el-form-item label="配置项配置名称" prop="name">
        <el-input v-model="form.name" style="width: 90%" placeholder="请输入配置项配置名称" />
      </el-form-item>
      <el-form-item label="配置项值">
        <el-input v-model="form.value" style="width: 90%" disabled />
      </el-form-item>
      <el-form-item label="配置项默认值">
        <el-input v-model="form.defaultValue" style="width: 90%" placeholder="请输入配置项默认值" />
      </el-form-item>
      <el-form-item label="配置项值类型">
        <el-input v-model="form.valueType" style="width: 90%" placeholder="请输入配置项值类型" />
      </el-form-item>
      <el-form-item label="是否禁用" prop="isDisabled">
        <el-switch
          v-model="form.isDisabled"
          :active-value="false"
          :inactive-value="true"
          active-text="启用"
          inactive-text="禁用"
        />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="form.note"
          style="width: 90%"
          rows="3"
          type="textarea"
          placeholder="请输入备注"
          maxlength="250"
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
  id: null,
  appCode: null,
  moduleCode: null,
  namespace: null,
  code: null,
  name: null,
  defaultValue: null,
  valueType: null,
  isDisabled: 0,
  note: null
}
export default {
  name: 'GlobalConfigForm',
  directives: { ElDragDialog },
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        appCode: [
          { required: true, trigger: 'blur', message: '应用编码不能为空' },
          { type: 'string', trigger: 'blur', message: '应用编码必须为 string 类型' }
        ],
        moduleCode: [
          { required: true, trigger: 'blur', message: '模块编码不能为空' },
          { type: 'string', trigger: 'blur', message: '模块编码必须为 string 类型' }
        ],
        namespace: [
          { required: true, trigger: 'blur', message: '命名空间不能为空' },
          { type: 'string', trigger: 'blur', message: '命名空间必须为 string 类型' }
        ],
        code: [
          { required: true, trigger: 'blur', message: '配置项编码不能为空' },
          { type: 'string', trigger: 'blur', message: '配置项编码必须为 string 类型' }
        ],
        name: [
          { required: true, trigger: 'blur', message: '配置项配置名称不能为空' },
          { type: 'string', trigger: 'blur', message: '配置项配置名称必须为 string 类型' }
        ],
        valueType: [
          { required: true, trigger: 'blur', message: '配置项值类型不能为空' },
          { type: 'string', trigger: 'blur', message: '配置项值类型必须为 string 类型' }
        ]
      }
    }
  }
}
</script>

<style scoped></style>
