<template>
  <el-dialog
    v-el-drag-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible="crud.status.cu > 0"
    :title="crud.status.title"
    width="500px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="字典编码" prop="code">
        <el-input v-model="form.code" style="width: 370px" />
      </el-form-item>
      <el-form-item label="字典名称" prop="name">
        <el-input v-model="form.name" style="width: 370px" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input
          v-model="form.note"
          type="textarea"
          style="width: 370px"
          :autosize="{ minRows: 2, maxRows: 4 }"
          placeholder="请输入内容"
        />
      </el-form-item>
      <el-form-item :label="dict['disabled_status'].name" prop="disabled">
        <el-select v-model="form.disabled" filterable placeholder="请选择">
          <el-option
            v-for="item in dict['disabled_status'].options"
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
const defaultForm = { id: null, code: null, name: null, note: null, disabled: false, dictOptions: [] }
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
        code: [{ required: true, message: '请输入编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        disabled: [{ required: true, message: '请选择是否启用', trigger: 'blur' }]
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
