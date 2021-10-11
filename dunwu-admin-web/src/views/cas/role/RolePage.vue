<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.blurry"
          size="small"
          clearable
          placeholder="输入名称或者描述搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <TableQueryOperation />
      </div>
      <TableOperation :permission="permission" />
    </div>

    <!--表格-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      row-key="id"
      highlight-current-row
      style="width: 100%;"
      border
      @selection-change="crud.selectionChangeHandler"
      @current-change="handleCurrentChange"
    >
      <el-table-column :selectable="checkboxT" type="selection" width="55" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="dataScope" label="数据权限" />
      <el-table-column prop="level" label="角色级别" />
      <el-table-column :show-overflow-tooltip="true" prop="note" label="描述" />
      <el-table-column :show-overflow-tooltip="true" width="135px" prop="createTime" label="创建日期" />
      <el-table-column
        v-if="checkPer(['admin', 'role:edit', 'role:del'])"
        label="操作"
        width="130px"
        align="center"
        fixed="right"
      >
        <template slot-scope="scope">
          <TableColumnOperation v-if="scope.row.level >= level" :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <Pagination />

    <!-- 表单 -->
    <Form :permission="permission" />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import Pagination from '@crud/Pagination'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Form from './RoleForm'
import RoleApi from '@/api/cas/role'

export default {
  name: 'RolePage',
  components: {
    Pagination,
    TableOperation,
    TableQueryOperation,
    TableColumnOperation,
    Form
  },
  cruds() {
    return CRUD({
      title: '角色',
      url: 'cas/role',
      sort: 'level,asc',
      crudMethod: { ...RoleApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  data() {
    return {
      defaultProps: { children: 'children', label: 'label', isLeaf: 'leaf' },
      level: 3,
      currentId: 0,
      menuLoading: false,
      showButton: false,
      menus: [],
      menuIds: [],
      permission: {
        add: ['admin', 'cas:role:add'],
        del: ['admin', 'cas:role:del'],
        edit: ['admin', 'cas:role:edit'],
        view: ['admin', 'cas:role:view']
      }
    }
  },
  created() {
    RoleApi.getLevel().then(data => {
      this.level = data.level
    })
  },
  methods: {
    // 触发单选
    handleCurrentChange(val) {
      if (val) {
        const _this = this
        // // 清空菜单的选中
        // this.$refs.menu.setCheckedKeys([])
        // 保存当前的角色id
        this.currentId = val.id
        // 初始化默认选中的key
        this.menuIds = []
        val.menus.forEach(function(data) {
          _this.menuIds.push(data.id)
        })
        this.showButton = true
      }
      this.$emit('selectCurrent', val)
    },
    refreshTable() {
      this.crud.toQuery()
    },
    checkboxT(row) {
      return row.level >= this.level
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.role-span {
  font-weight: bold;
  color: #303133;
  font-size: 15px;
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
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
