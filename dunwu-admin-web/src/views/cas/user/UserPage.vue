<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--工具栏-->
      <div class="head-container">
        <div v-if="crud.props.searchToggle">
          <!-- 搜索 -->
          <el-input
            v-model="query.blurry"
            clearable
            size="small"
            placeholder="输入名称或者邮箱搜索"
            style="width: 200px"
            class="filter-item"
            @keyup.enter.native="crud.toQuery"
          />
          <el-select
            v-model="query.disabled"
            clearable
            size="small"
            placeholder="状态"
            class="filter-item"
            style="width: 90px"
            @change="crud.toQuery"
          >
            <el-option v-for="item in enabledTypeOptions" :key="item.code" :label="item.name" :value="item.code" />
          </el-select>
          <TableQueryOperation />
        </div>
        <TableOperation v-if="canWrite" :permission="permission" />
      </div>
      <!--表单渲染-->
      <el-dialog
        append-to-body
        :close-on-click-modal="false"
        :before-close="crud.cancelCU"
        :visible.sync="crud.status.cu > 0"
        :title="crud.status.title"
        width="570px"
      >
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="66px">
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
              :load-options="loadDepts"
              style="width: 178px"
              placeholder="请选择"
            />
          </el-form-item>
          <el-form-item label="岗位" prop="jobId">
            <el-select v-model="form.jobId" style="width: 178px" placeholder="请选择">
              <el-option v-for="item in jobs" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.gender" style="width: 178px">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="状态">
            <el-radio-group v-model="form.disabled" :disabled="form.id === user.id">
              <el-radio v-for="item in dict['disabled_status'].options" :key="item.id" :label="item.code">
                {{ item.name }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item style="margin-bottom: 0" label="角色" prop="roles">
            <el-select
              v-model="roleDatas"
              style="width: 437px"
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
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table
        ref="table"
        v-loading="crud.loading"
        :data="crud.data"
        border
        style="width: 100%"
        @selection-change="crud.selectionChangeHandler"
      >
        <el-table-column v-if="canWrite" :selectable="checkboxT" type="selection" width="55" />
        <el-table-column prop="id" label="ID" />
        <el-table-column :show-overflow-tooltip="true" prop="username" label="用户名" />
        <el-table-column :show-overflow-tooltip="true" prop="nickname" label="昵称" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column :show-overflow-tooltip="true" prop="phone" width="100" label="电话" />
        <el-table-column :show-overflow-tooltip="true" width="135" prop="email" label="邮箱" />
        <el-table-column :show-overflow-tooltip="true" prop="dept" label="部门">
          <template slot-scope="scope">
            <div>{{ scope.row.dept.name }}</div>
          </template>
        </el-table-column>
        <el-table-column v-if="canWrite" label="状态" prop="disabled">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.disabled"
              :disabled="user.id === scope.row.id"
              active-color="#409EFF"
              inactive-color="#F56C6C"
              :active-value="false"
              :inactive-value="true"
              @change="changeEnabled(scope.row, scope.row.disabled)"
            />
          </template>
        </el-table-column>
        <el-table-column
          v-if="canWrite && checkPer(['admin', 'user:edit', 'user:del'])"
          label="操作"
          width="115"
          align="center"
          fixed="right"
        >
          <template slot-scope="scope">
            <TableColumnOperation :data="scope.row" :permission="permission" :disabled-dle="scope.row.id === user.id" />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <Pagination />
    </el-row>
  </div>
</template>

<script>
import userApi from '@/api/cas/user'
import deptApi from '@/api/cas/dept'
import jobApi from '@/api/cas/job'
import roleApi from '@/api/cas/role'
import { isvalidPhone } from '@/utils/validate'
import CRUD, { crud, form, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import Pagination from '@crud/Pagination'
import Treeselect, { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import { mapGetters } from 'vuex'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

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
  name: 'UserList',
  components: { Treeselect, TableOperation, TableQueryOperation, TableColumnOperation, Pagination },
  cruds() {
    return CRUD({ title: '用户', url: 'cas/user', sort: ['id,desc'], crudMethod: { ...userApi }})
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  // 数据字典
  dicts: ['disabled_status'],
  props: {
    deptId: {
      type: Number,
      required: false,
      default: () => {
        return null
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
      height: document.documentElement.clientHeight - 180 + 'px;',
      depts: [],
      jobs: [],
      level: 3,
      roles: [],
      roleDatas: [], // 多选时使用
      permission: {
        add: ['admin', 'user:add'],
        edit: ['admin', 'user:edit'],
        del: ['admin', 'user:del']
      },
      enabledTypeOptions: [{ code: 'false', name: '启用' }, { code: 'true', name: '禁用' }],
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
      },
      canWrite: this.deptId == null
    }
  },
  watch: {
    deptId(value) {
      this.deptId = value
      this.canWrite = this.deptId == null
      if (!this.canWrite) {
        this.crud.optShow.add = false
        this.crud.optShow.edit = false
        this.crud.optShow.del = false
        this.crud.optShow.export = false
      }
      this.crud.toQuery()
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  mounted: function() {
    this.crud.msg.add = '添加成功，默认密码：123456'
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 180 + 'px;'
    }
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

    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.crud.query.deptId = this.deptId
    },
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.getRoles()
      if (form.id == null) {
        this.getDepts()
      } else {
        this.getSupDepts(form.dept.id)
      }
      this.getRoleLevel()
      this.getJobs({ disabled: true })
      form.disabled = form.disabled.toString()
    },
    // 添加前将多选的值设置为空
    [CRUD.HOOK.beforeToAdd]() {
      this.roleDatas = []
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
    getDepts() {
      deptApi.treeList({ disabled: true }).then(res => {
        this.depts = res.map(function(obj) {
          if (obj.hasChildren) {
            obj.children = null
          }
          return obj
        })
      })
    },
    getSupDepts(deptId) {
      deptApi.superiorTreeList(deptId).then(res => {
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
        deptApi.treeList({ disabled: true, pid: parentNode.id }).then(res => {
          parentNode.children = res.map(function(obj) {
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
    // 改变状态
    changeEnabled(data, val) {
      this.$confirm(
        '此操作将 "' + this.dict.label.disabled_status[val] + '" ' + data.username + ', 是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          userApi
            .edit(data)
            .then(res => {
              this.crud.notify(CRUD.NOTIFICATION_TYPE.SUCCESS, this.dict.label.disabled_status[val] + '成功')
            })
            .catch(() => {
              data.disabled = !data.disabled
            })
        })
        .catch(() => {
          data.disabled = !data.disabled
        })
    },
    // 获取弹窗内角色数据
    getRoles() {
      roleApi
        .list()
        .then(res => {
          this.roles = res
        })
        .catch(() => {})
    },
    // 获取弹窗内岗位数据
    getJobs(params) {
      jobApi
        .list(params)
        .then(res => {
          this.jobs = res
        })
        .catch(() => {})
    },
    // 获取权限级别
    getRoleLevel() {
      roleApi
        .getLevel()
        .then(res => {
          this.level = res.level
        })
        .catch(() => {})
    },
    checkboxT(row, rowIndex) {
      return row.id !== this.user.id
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
