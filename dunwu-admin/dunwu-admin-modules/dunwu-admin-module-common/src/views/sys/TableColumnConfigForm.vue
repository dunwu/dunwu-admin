<template>
  <el-dialog
    v-el-drag-dialog
    :before-close="crud.cancelCU"
    :close-on-click-modal="false"
    :title="crud.status.title"
    :visible="crud.status.cu > 0"
    append-to-body
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px" size="small">
      <el-form-item label="Schema名称" prop="schemaName">
        <el-input v-model="form.schemaName" placeholder="请输入Schema名称" style="width: 90%" />
      </el-form-item>
      <el-form-item label="Table名称" prop="tableName">
        <el-input v-model="form.tableName" placeholder="请输入Table名称" style="width: 90%" />
      </el-form-item>
      <el-form-item label="字段名称" prop="columnName">
        <el-input v-model="form.columnName" placeholder="请输入字段名称" style="width: 90%" />
      </el-form-item>
      <el-form-item label="是否锁定">
        <el-input v-model="form.locked" placeholder="请输入是否锁定" style="width: 90%" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {crud, form} from '@crud/crud'
import ElDragDialog from '@/directive/el-drag-dialog'

/**
 * 表单实体默认值
 */
const defaultForm = {
  schemaName: null,
  tableName: null,
  columnName: null,
  locked: null
}
export default {
  name: 'TableColumnConfigForm',
  directives: { ElDragDialog },
  mixins: [form(defaultForm), crud()],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
      }
    }
  },
}
</script>

<style scoped></style>
