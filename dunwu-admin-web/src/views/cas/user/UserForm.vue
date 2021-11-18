<template>
  <el-dialog
    v-el-drag-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model.number="form.phone" />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <treeselect
          v-model="form.deptId"
          :options="depts"
          :load-options="loadDeptList"
          style="width: 184px"
          search-nested
          :show-count="true"
          :default-expand-level="1"
        />
        <label
          slot="option-label"
          slot-scope="{ node, shouldShowCount, count, labelClassName, countClassName }"
          :class="labelClassName"
        >
          {{ node.label }}
          <span v-if="shouldShowCount" :class="countClassName">({{ count }})</span>
        </label>
      </el-form-item>
      <el-form-item label="岗位" prop="jobId">
        <el-select v-model="form.jobId" style="width: 184px" placeholder="请选择">
          <el-option v-for="item in jobs" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="角色" prop="roles">
        <el-select
          v-model="roleDatas"
          style="width: 462px"
          multiple
          placeholder="请选择"
          @remove-tag="deleteTag"
          @change="changeRole"
        >
          <el-option
            v-for="item in roles"
            :key="item.name"
            :disabled="level !== 1 && item.level <= level"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.gender" size="mini" style="width: 184px">
          <el-radio-button label="男">男</el-radio-button>
          <el-radio-button label="女">女</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否禁用" prop="disabled">
        <el-radio-group v-model="form.disabled" size="mini" style="width: 184px">
          <el-radio-button :label="true">是</el-radio-button>
          <el-radio-button :label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import CRUD, { crud, form } from '@crud/crud'
import ElDragDialog from '@/directive/el-drag-dialog'
import Treeselect, { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import UserApi from '@/api/cas/user'
import DeptApi from '@/api/cas/dept'
import JobApi from '@/api/cas/job'
import RoleApi from '@/api/cas/role'
import { isvalidPhone } from '@/utils/validate'

let userRoles = []
const defaultForm = {
  id: null,
  username: null,
  nickname: null,
  gender: '男',
  email: null,
  disabled: 'false',
  roles: [],
  jobId: null,
  deptId: null,
  dept: { id: null },
  job: { id: null },
  phone: null
}
export default {
  name: 'UserForm',
  components: { Treeselect },
  directives: { ElDragDialog },
  cruds() {
    return CRUD({
      title: '用户',
      url: 'cas/user',
      sort: ['id,asc'],
      tableType: 'page',
      crudMethod: { ...UserApi }
    })
  },
  mixins: [form(defaultForm), crud()],
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
    // 自定义验证
    const validPhone = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入电话号码'))
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    return {
      depts: [],
      jobs: [],
      level: 3,
      roles: [],
      roleDatas: [], // 多选时使用
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入用户昵称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        phone: [{ required: true, trigger: 'blur', validator: validPhone }]
      }
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  mounted() {
    this.crud.msg.add = '添加成功，默认密码：123456'
  },
  methods: {
    changeRole(value) {
      userRoles = []
      value.forEach(function(data, index) {
        const role = { id: data }
        userRoles.push(role)
      })
    },
    deleteTag(value) {
      userRoles.forEach(function(data, index) {
        if (data.id === value) {
          userRoles.splice(index, value)
        }
      })
    },
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.getRoles()
      this.getDeptTreeList()
      this.getRoleLevel()
      console.info('form', form)
      this.getJobs({ disabled: false, deptId: form.deptId })
      // form.disabled = form.disabled.toString()
    },
    // 添加前将多选的值设置为空
    [CRUD.HOOK.beforeToAdd]() {
      this.roleDatas = []
      this.getJobs({ deptId: this.form.dept.id })
    },
    // 初始化编辑时候的角色与岗位
    [CRUD.HOOK.beforeToEdit](crud, form) {
      this.getJobs({ deptId: this.form.dept.id })
      this.roleDatas = []
      userRoles = []
      const _this = this
      form.roles.forEach(function(role, index) {
        _this.roleDatas.push(role.id)
        const rol = { id: role.id }
        userRoles.push(rol)
      })
    },
    // 提交前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      if (!crud.form.dept.id) {
        this.$message({
          message: '部门不能为空',
          type: 'warning'
        })
        return false
      } else if (!crud.form.jobId) {
        this.$message({
          message: '岗位不能为空',
          type: 'warning'
        })
        return false
      } else if (this.roleDatas.length === 0) {
        this.$message({
          message: '角色不能为空',
          type: 'warning'
        })
        return false
      }
      crud.form.roles = userRoles
      return true
    },
    getDeptTreeList() {
      DeptApi.treeList().then(res => {
        const children = res.map(function(obj) {
          return obj
        })
        this.depts = [{ id: 0, label: '顶级类目', children: children }]
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
    // 获取弹窗内角色数据
    getRoles() {
      RoleApi.list()
        .then(res => {
          this.roles = res
        })
        .catch(() => {})
    },
    // 获取弹窗内岗位数据
    getJobs(params) {
      JobApi.list(params)
        .then(res => {
          console.info('jobs', res)
          this.jobs = res
        })
        .catch(() => {})
    },
    // 获取权限级别
    getRoleLevel() {
      RoleApi.getLevel()
        .then(res => {
          this.level = res.level
        })
        .catch(() => {})
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
</style>
