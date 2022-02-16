<template>
  <el-dialog
    v-el-drag-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="字典编码" prop="code">
        <el-input v-model="form.code" style="width: 90%" placeholder="请输入字典编码" />
      </el-form-item>
      <el-form-item label="字典名称" prop="name">
        <el-input v-model="form.name" style="width: 90%" placeholder="请输入字典名称" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="form.note"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4 }"
          style="width: 90%"
          placeholder="请输入备注"
        />
      </el-form-item>
      <el-form-item label="是否禁用" prop="disabled">
        <el-select v-model="form.disabled" filterable placeholder="请选择是否禁用">
          <el-option
            v-for="item in dict.disabled_status.options"
            :key="item.code"
            :label="item.name"
            :value="item.code"
            :disabled="item.disabled"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import CRUD, { crud, form } from '@crud/crud'
import ElDragDialog from '@/directive/el-drag-dialog'

/**
 * 表单实体默认值
 */
const defaultForm = { code: null, name: null, note: null, disabled: false }
export default {
  name: 'DictForm',
  directives: { ElDragDialog },
  mixins: [form(defaultForm), crud()],
  /**
   * 数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        code: [
          { required: true, trigger: 'blur', message: '字典编码不能为空' },
          { type: 'string', trigger: 'blur', message: '字典编码必须为 string 类型' }
        ],
        name: [
          { required: true, trigger: 'blur', message: '字典名称不能为空' },
          { type: 'string', trigger: 'blur', message: '字典名称必须为 string 类型' }
        ],
        disabled: [
          { required: true, trigger: 'blur', message: '是否禁用不能为空' },
          { type: 'string', trigger: 'blur', message: '是否禁用必须为 string 类型' }
        ]
      }
    }
  },
  methods: {
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      form.disabled = `${form.disabled}`
    }
  }
}
</script>

<style scoped></style>
