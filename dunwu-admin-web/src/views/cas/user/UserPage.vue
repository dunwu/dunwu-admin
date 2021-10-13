<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 搜索 -->
      <div v-if="crud.props.searchToggle">
        <el-input
          v-model="query.blurry"
          clearable
          size="small"
          placeholder="输入用户ID或用户名搜索"
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
          <el-option
            v-for="item in dict['disabled_status'].options"
            :key="item.code"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
        <TableQueryOperation />
      </div>
      <TableOperation v-if="allowOperation" :permission="permission" />
    </div>

    <!--表格-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      row-key="id"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column v-if="allowOperation" type="selection" width="55" />
      <el-table-column prop="id" label="ID" />
      <el-table-column :show-overflow-tooltip="true" prop="username" label="用户名" />
      <el-table-column :show-overflow-tooltip="true" prop="nickname" label="昵称" />
      <el-table-column :show-overflow-tooltip="true" prop="dept" label="部门">
        <template slot-scope="scope">
          <div>{{ scope.row.dept.name }}</div>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="job" label="职务">
        <template slot-scope="scope">
          <div>{{ scope.row.job.name }}</div>
        </template>
      </el-table-column>
      <el-table-column v-if="allowOperation" label="状态" prop="disabled">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.disabled"
            :disabled="user.id === scope.row.id"
            active-color="#409EFF"
            inactive-color="#F56C6C"
            :active-value="false"
            :inactive-value="true"
            @change="changeStatus(scope.row, scope.row.disabled)"
          />
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <Pagination />

    <!--表单-->
    <From :permission="permission" />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import { mapGetters } from 'vuex'
import From from './UserForm'
import UserApi from '@/api/cas/user'

export default {
  name: 'UserPage',
  components: { TableOperation, TableQueryOperation, Pagination, From },
  cruds() {
    return CRUD({
      title: '用户',
      url: 'cas/user',
      sort: ['id,asc'],
      tableType: 'page',
      crudMethod: { ...UserApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  /**
   * 数据字典
   */
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
    return {
      /**
       * 权限表达式
       */
      permission: {
        add: ['admin', 'cas:user:add'],
        del: ['admin', 'cas:user:del'],
        edit: ['admin', 'cas:user:edit'],
        view: ['admin', 'cas:user:view']
      },
      allowOperation: this.deptId == null
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  watch: {
    deptId(value) {
      this.deptId = value
      this.allowOperation = this.deptId == null
      if (!this.allowOperation) {
        this.crud.optShow.add = false
        this.crud.optShow.edit = false
        this.crud.optShow.del = false
        this.crud.optShow.export = false
      }
      this.crud.toQuery()
    }
  },
  methods: {
    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.crud.query.deptId = this.deptId
    },
    /**
     * 切换禁用状态
     */
    changeStatus(data, val) {
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
          UserApi.edit(data)
            .then(res => {
              this.crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, this.dict.label.disabled_status[val] + '成功')
            })
            .catch(err => {
              data.disabled = !data.disabled
              console.error(err.response.data.msg)
            })
        })
        .catch(() => {
          data.disabled = !data.disabled
        })
    },
    /**
     * 选中表中一行记录
     */
    handleCurrentChange(val) {
      this.$emit('selectCurrent', val)
    },
    /**
     * 刷新表数据
     */
    refreshTable() {
      this.crud.toQuery()
    }
  }
}
</script>

<style lang="scss" scoped></style>
