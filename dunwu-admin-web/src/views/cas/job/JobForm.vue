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
      <el-form-item label="职务名称" prop="name">
        <el-input v-model="form.name" style="width: 370px;" placeholder="请输入职务名称" />
      </el-form-item>
      <el-form-item label="职务类型" prop="type">
        <el-radio v-for="item in dict['job_type'].options" :key="item.id" v-model="form.type" :label="item.code">
          {{ item.name }}
        </el-radio>
      </el-form-item>
      <el-form-item label="职级" prop="level">
        <!--        <el-input v-model="form.level" style="width: 370px;" />-->
        <el-select v-model="form.level" size="small" placeholder="请选择职级" style="width: 370px;">
          <el-option
            v-for="item in dict['job_profession_level'].options"
            :key="item.code"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sequence">
        <el-input-number
          v-model.number="form.sequence"
          :min="1"
          :max="99"
          placeholder="请输入职务顺序"
          controls-position="right"
          style="width: 370px;"
        />
      </el-form-item>
      <el-form-item label="是否禁用" prop="disabled">
        <el-radio-group v-model="form.disabled" size="mini">
          <el-radio-button :label="true">是</el-radio-button>
          <el-radio-button :label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input
          v-model="form.note"
          style="width: 370px;"
          placeholder="请输入备注"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10 }"
          maxlength="1000"
          show-word-limit
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">
        取消
      </el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">
        确认
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import CRUD, { crud, form } from '@crud/crud'
import ElDragDialog from '@/directive/el-drag-dialog'

/**
 * 表单默认值
 */
const defaultForm = {
  id: null,
  name: null,
  type: 1,
  level: 1,
  sequence: 1,
  disabled: false,
  note: null
}
export default {
  name: 'JobForm',
  directives: { ElDragDialog },
  mixins: [form(defaultForm), crud()],
  props: {
    dict: { type: Object, required: true }
  },
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择职务类型', trigger: 'blur' }],
        level: [{ required: true, message: '请选择职级', trigger: 'blur' }],
        sequence: [{ required: true, message: '请输入顺序', trigger: 'blur', type: 'number' }],
        disabled: [{ required: true, message: '请选择是否禁用', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      form.type = `${form.type}`
      form.level = `${form.level}`
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped></style>
