<template>
  <div style="margin: 20px">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>添加成员</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-select
            v-model="ids"
            multiple
            filterable
            remote
            reserve-keyword
            size="mini"
            style="width: 90%"
            class="filter-item"
            placeholder="输入用户ID或用户名搜索"
            :remote-method="getExistsList"
            :loading="listOptionsLoading"
          >
            <el-option v-for="item in list" :key="item.id" :label="item.username" :value="item.id" />
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-input
            v-model="query.name"
            clearable
            size="mini"
            style="width: 90%"
            class="filter-item"
            placeholder="输入岗位名称搜索"
            @keyup.enter.native="crud.toQuery"
          />
        </el-col>
        <el-col :span="8">
          <el-button :loading="addLoading" type="primary" plain size="mini" @click="bindUserToDept">
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
                  :value="item.code"
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
            :loading="crud.delAllLoading"
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
      listOptionsLoading: false,
      addLoading: false,
      list: [],
      ids: []
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
    this.ids = []
    this.getExistsList()
  },
  methods: {
    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.crud.query.deptId = this.dept.id
    },
    /**
     * 根据查询条件搜索可以添加的选项
     */
    getExistsList(val) {
      let params = {}
      if (val) {
        params = { blurry: val }
      }
      UserApi.list(params).then(data => {
        this.list = data
      })
    },
    /**
     * 添加部门成员
     */
    bindUserToDept() {
      this.addLoading = true
      UserApi.bindDept(this.dept.id, this.ids)
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
          this.crud.delAllLoading = true
          const ids = val.map((item, index, arr) => {
            return item.id
          })
          UserApi.unbindDept(this.dept.id, ids)
            .then(data => {
              this.crud.delAllLoading = false
              if (data) {
                this.$message({ message: '删除成功', type: 'success' })
              }
              this.crud.toQuery()
            })
            .catch(() => {
              this.crud.delAllLoading = false
            })
        })
        .catch(() => {})
    },
    clear() {
      this.ids = []
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped></style>