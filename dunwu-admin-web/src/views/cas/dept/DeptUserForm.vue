<template>
  <div style="margin: 20px">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>添加成员</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-select
            v-model="userIds"
            multiple
            filterable
            remote
            reserve-keyword
            size="mini"
            style="width: 90%"
            class="filter-item"
            placeholder="输入用户ID或用户名搜索"
            :remote-method="getUserOptionsList"
            :loading="userOptionsLoading"
          >
            <el-option v-for="item in users" :key="item.id" :label="item.nickname" :value="item.id">
              <span style="float: left">{{ item.nickname }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-select
            v-model="jobId"
            filterable
            remote
            reserve-keyword
            size="mini"
            style="width: 90%"
            class="filter-item"
            placeholder="输入职务ID或职务名搜索"
            :remote-method="getJobOptionsList"
            :loading="jobOptionsLoading"
          >
            <el-option v-for="item in jobs" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button
            :loading="addLoading"
            type="primary"
            :disabled="userIds.length === 0"
            plain
            size="mini"
            @click="bindUserToDept"
          >
            添加成员
          </el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card shadow="never" style="margin-top: 30px">
      <div slot="header" class="clearfix">
        <span>已有成员</span>
      </div>
      <!--工具栏-->
      <div class="head-container">
        <div v-if="crud.props.searchToggle">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-input
                v-model="query.blurry"
                clearable
                size="mini"
                style="width: 90%"
                class="filter-item"
                placeholder="输入用户ID或用户名搜索"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
            <el-col :span="8">
              <el-select
                v-model="query.disabled"
                clearable
                size="mini"
                style="width: 90%"
                class="filter-item"
                placeholder="状态"
                @change="crud.toQuery"
              >
                <el-option
                  v-for="item in dict['disabled_status'].options"
                  :key="item.code"
                  :label="item.name"
                  :value="item.value"
                />
              </el-select>
            </el-col>
            <el-col :span="8">
              <TableQueryOperation />
            </el-col>
          </el-row>
        </div>
        <TableOperation>
          <el-button
            slot="right"
            type="danger"
            icon="el-icon-delete"
            size="mini"
            style="width: 90%"
            class="filter-item"
            :loading="crud.batchDelLoading"
            :disabled="crud.selections.length === 0"
            @click="unbindUserToDept(crud.selections)"
          >
            删除
          </el-button>
        </TableOperation>
      </div>
      <!--表格渲染-->
      <el-table
        ref="table"
        v-loading="crud.loading"
        :data="crud.data"
        border
        @selection-change="crud.selectionChangeHandler"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" />
        <el-table-column :show-overflow-tooltip="true" prop="username" label="用户名" />
        <el-table-column :show-overflow-tooltip="true" prop="nickname" label="昵称" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column :show-overflow-tooltip="true" prop="phone" label="电话" />
        <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱" />
        <el-table-column :show-overflow-tooltip="true" prop="dept" label="部门">
          <template slot-scope="scope">
            <div>{{ scope.row.dept.name }}</div>
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <Pagination />
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import UserApi from '@/api/cas/user'
import JobApi from '@/api/cas/job'

export default {
  name: 'DeptUserForm',
  components: { TableOperation, TableQueryOperation, Pagination },
  cruds() {
    return CRUD({
      title: '用户',
      url: 'cas/user',
      sort: ['id,asc'],
      crudMethod: { ...UserApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  // 数据字典
  dicts: ['disabled_status'],
  props: {
    dept: {
      type: Object,
      required: false,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      deptId: null,
      addLoading: false,
      jobOptionsLoading: false,
      jobs: [],
      jobId: null,
      userOptionsLoading: false,
      users: [],
      userIds: []
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  mounted() {
    this.crud.optShow.add = false
    this.crud.optShow.edit = false
    this.crud.optShow.del = false
    this.crud.optShow.export = false
    this.jobId = null
    this.userIds = []
    this.getUserOptionsList()
    this.getJobOptionsList()
  },
  methods: {
    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.deptId = this.dept.id
      this.crud.query.deptId = this.dept.id
    },
    /**
     * 根据查询条件搜索可以添加的用户列表
     */
    getUserOptionsList(val) {
      const params = {}
      if (val) {
        params.blurry = val
      }
      UserApi.list(params).then(data => {
        this.users = data
      })
    },
    /**
     * 根据查询条件搜索可以添加的职务列表
     */
    getJobOptionsList(val) {
      const params = {}
      if (val) {
        params.name = val
      }
      params.deptId = this.dept.id
      JobApi.list(params).then(data => {
        this.jobs = data
      })
    },
    /**
     * 添加部门成员
     */
    bindUserToDept() {
      this.addLoading = true
      UserApi.bindDept(this.dept.id, this.jobId, this.userIds)
        .then(data => {
          this.addLoading = false
          if (data) {
            this.$message({ message: '添加成员成功', type: 'success' })
          }
          this.crud.toQuery()
          this.clear()
        })
        .catch(() => {
          this.addLoading = false
        })
    },
    /**
     * 删除部门成员
     */
    unbindUserToDept(val) {
      this.$confirm(`确认删除选中的${val.length}条数据?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.crud.batchDelLoading = true
          const userIds = val.map((item, index, arr) => {
            return item.id
          })
          UserApi.unbindDept(this.dept.id, userIds)
            .then(data => {
              this.crud.batchDelLoading = false
              if (data) {
                this.$message({ message: '删除成功', type: 'success' })
              }
              this.crud.toQuery()
            })
            .catch(() => {
              this.crud.batchDelLoading = false
            })
        })
        .catch(() => {})
    },
    clear() {
      this.jobId = null
      this.userIds = []
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped></style>
