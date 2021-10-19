<template>
  <el-dialog
    v-el-drag-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="520px"
  >
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="角色名称" prop="code">
        <el-input v-model="form.code" style="width: 380px;" />
      </el-form-item>
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="form.name" style="width: 380px;" />
      </el-form-item>
      <el-form-item label="角色级别" prop="level">
        <el-input-number v-model.number="form.level" :min="1" controls-position="right" style="width: 145px;" />
      </el-form-item>
      <el-form-item label="数据范围" prop="dataScope">
        <el-select v-model="form.dataScope" style="width: 140px" placeholder="请选择数据范围" @change="changeScope">
          <el-option v-for="item in dateScopes" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item v-if="form.dataScope === '自定义'" label="数据权限" prop="depts">
        <treeselect
          v-model="deptDatas"
          :load-options="loadDepts"
          :options="depts"
          multiple
          style="width: 380px"
          placeholder="请选择"
        />
      </el-form-item>
      <el-form-item label="是否禁用" prop="disabled">
        <el-radio-group v-model="form.disabled" size="mini">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="描述信息" prop="note">
        <el-input v-model="form.note" style="width: 380px;" rows="5" type="textarea" />
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
import Treeselect, { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import RoleApi from '@/api/cas/role'
import DeptApi from '@/api/cas/dept'
import MenuApi from '@/api/cas/menu'

/**
 * 表单默认值
 */
const defaultForm = {
  id: null,
  code: null,
  name: null,
  note: null,
  disabled: false,
  dataScope: '全部',
  level: 3,
  depts: []
}
export default {
  name: 'RoleForm',
  components: { Treeselect },
  directives: { ElDragDialog },
  mixins: [form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '角色',
      url: 'cas/role',
      sort: 'level,asc',
      crudMethod: { ...RoleApi }
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
      defaultProps: { children: 'children', label: 'label', isLeaf: 'leaf' },
      dateScopes: ['全部', '本级', '自定义'],
      level: 3,
      currentId: 0,
      menuLoading: false,
      showButton: false,
      menus: [],
      menuIds: [],
      depts: [],
      deptDatas: [], // 多选时使用
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        permission: [{ required: true, message: '请输入权限', trigger: 'blur' }]
      }
    }
  },
  created() {
    RoleApi.getLevel().then(data => {
      this.level = data.level
    })
  },
  methods: {
    getMenuDatas(node, resolve) {
      setTimeout(() => {
        MenuApi.treeList({ pid: node.data.id }).then(res => {
          resolve(res)
        })
      }, 100)
    },
    // 添加前初始化部门信息
    [CRUD.HOOK.beforeToAdd]() {
      this.deptDatas = []
    },
    // 编辑前初始化自定义数据权限的部门信息
    [CRUD.HOOK.beforeToEdit](crud, form) {
      this.deptDatas = []
      if (form.dataScope === '自定义') {
        this.getSupDepts(form.depts)
      }
      const _this = this
      if (form.depts) {
        form.depts.forEach(function(dept) {
          _this.deptDatas.push(dept.id)
        })
      }
    },
    // 提交前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      if (crud.form.dataScope === '自定义' && this.deptDatas.length === 0) {
        this.$message({
          message: '自定义数据权限不能为空',
          type: 'warning'
        })
        return false
      } else if (crud.form.dataScope === '自定义') {
        const depts = []
        this.deptDatas.forEach(function(data) {
          const dept = { id: data }
          depts.push(dept)
        })
        crud.form.depts = depts
      } else {
        crud.form.depts = []
      }
      return true
    },
    // 获取部门数据
    getDepts() {
      DeptApi.treeList({ disabled: false }).then(res => {
        this.depts = res.content.map(function(obj) {
          if (obj.hasChildren) {
            obj.children = null
          }
          return obj
        })
      })
    },
    getSupDepts(depts) {
      const ids = []
      depts.forEach(dept => {
        ids.push(dept.id)
      })
      DeptApi.superiorTreeList(ids).then(res => {
        const data = res
        this.buildDepts(data)
        this.depts = data
      })
    },
    buildDepts(depts) {
      depts.forEach(data => {
        if (data.children) {
          this.buildDepts(data.children)
        }
        if (data.hasChildren && !data.children) {
          data.children = null
        }
      })
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        DeptApi.treeList({ disabled: true, pid: parentNode.id }).then(res => {
          parentNode.children = res.content.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 200)
        })
      }
    },
    // 如果数据权限为自定义则获取部门数据
    changeScope() {
      if (this.form.dataScope === '自定义') {
        this.getDepts()
      }
    },
    checkboxT(row) {
      return row.level >= this.level
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.role-span {
  font-weight: bold;
  color: #303133;
  font-size: 15px;
}
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep .vue-treeselect__multi-value {
  margin-bottom: 0;
}
::v-deep .vue-treeselect__multi-value-item {
  border: 0;
  padding: 0;
}
</style>
