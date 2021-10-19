<template>
  <el-dialog
    v-el-drag-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="600px"
  >
    <el-form ref="form" inline :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="form.name" style="width: 420px" placeholder="请输入部门名称" />
      </el-form-item>
      <el-form-item label="部门顺序" prop="sequence">
        <el-input-number
          v-model.number="form.sequence"
          style="width: 420px;"
          placeholder="请输入部门顺序"
          :min="0"
          :max="999"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="顶级部门">
        <el-radio-group v-model="form.isTop" size="mini">
          <el-radio-button :label="true">是</el-radio-button>
          <el-radio-button :label="false">否</el-radio-button>
        </el-radio-group>
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
          style="width: 420px;"
          placeholder="请输入备注"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4 }"
        />
      </el-form-item>
      <el-form-item v-if="!form.isTop" style="margin-bottom: 0;" label="上级部门" prop="pid">
        <treeselect
          v-model="form.pid"
          :load-options="loadDeptList"
          :options="deptList"
          style="width: 420px;"
          search-nested
          :show-count="true"
          :default-expand-level="1"
        >
          <label
            slot="option-label"
            slot-scope="{ node, shouldShowCount, count, labelClassName, countClassName }"
            :class="labelClassName"
          >
            {{ node.label }}
            <span v-if="shouldShowCount" :class="countClassName">({{ count }})</span>
          </label>
        </treeselect>
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
import DeptApi from '@/api/cas/dept'
import Treeselect, { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import ElDragDialog from '@/directive/el-drag-dialog'

/**
 * 表单默认值
 */
const defaultForm = {
  id: null,
  pid: 0,
  name: null,
  sequence: 1,
  childrenNum: 0,
  disabled: false,
  note: null,
  isTop: true
}
export default {
  name: 'DeptForm',
  components: { Treeselect },
  directives: { ElDragDialog },
  mixins: [form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '部门',
      url: 'cas/dept',
      tableType: 'tree',
      crudMethod: { ...DeptApi }
    })
  },
  /**
   * 数据字典
   */
  dicts: ['disabled_status'],
  props: {
    permission: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        name: [{ required: true, trigger: 'blur', message: '部门名称不能为空' }],
        sequence: [{ required: true, trigger: 'blur', message: '部门顺序不能为空' }]
      },
      deptList: []
    }
  },
  methods: {
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.deptList = []
      form.isTop = form.pid === 0
      this.getAllTreeList(form.id, form.pid)
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      if (this.form.pid !== 0 && this.form.pid === this.form.id) {
        this.$message({ message: '上级部门不能为空', type: 'warning' })
        return false
      }
      if (this.form.isTop) {
        this.form.pid = 0
      }
      return true
    },
    getAllTreeList(id, pid) {
      DeptApi.treeList().then(res => {
        const children = res.map(function(obj) {
          return obj
        })
        const deptList = [{ id: 0, label: '顶级类目', children: children }]
        this.buildDeptOptions(deptList, id, pid)
        this.deptList = deptList
      })
    },
    // 获取弹窗内部门数据
    loadDeptList({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        DeptApi.treeList({ disabled: false, pid: parentNode.id !== 0 ? parentNode.id : 0 }).then(res => {
          parentNode.children = res.map(function(obj) {
            return obj
          })
          setTimeout(() => {
            callback()
          }, 100)
        })
      }
    },
    buildDeptOptions(deptList, id, pid) {
      deptList.forEach(data => {
        if (pid && pid === data.id) {
          // 上级部门选项默认展开
          data.isDefaultExpanded = true
        }
        if (id && id === data.id) {
          // 上级部门选项中不允许选择自己
          data.isDisabled = true
        }
        if (data.children) {
          this.buildDeptOptions(data.children, id, pid)
        }
      })
    },
    handleDrag() {
      this.$refs.select.blur()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .vue-treeselect__control,
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  height: 30px;
  line-height: 30px;
}
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
