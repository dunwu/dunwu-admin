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
          placeholder="输入角色编码或角色名称搜索"
          style="width: 200px;"
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
      <el-table-column v-if="allowOperation" :selectable="checkboxT" type="selection" width="55" />
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="code" label="角色编码" />
      <el-table-column prop="name" label="角色名称" />
      <el-table-column prop="dataScope" label="数据权限" />
      <el-table-column prop="level" label="角色级别" />
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
  /**
   * 数据字典
   */
  dicts: ['disabled_status'],
  props: {
    userId: {
      type: Number,
      required: false,
      default: () => {
        return null
      }
    }
  },
  data() {
    return {
      defaultProps: { children: 'children', label: 'label', isLeaf: 'leaf' },
      level: 3,
      currentId: 0,
      menuLoading: false,
      showButton: false,
      menus: [],
      menuIds: [],
      /**
       * 权限表达式
       */
      permission: {
        add: ['admin', 'cas:role:add'],
        del: ['admin', 'cas:role:del'],
        edit: ['admin', 'cas:role:edit'],
        view: ['admin', 'cas:role:view']
      },
      allowOperation: this.userId == null
    }
  },
  watch: {
    userId(value) {
      this.userId = value
      this.allowOperation = this.userId == null
      if (!this.allowOperation) {
        this.crud.optShow.add = false
        this.crud.optShow.edit = false
        this.crud.optShow.del = false
        this.crud.optShow.export = false
      }
      this.crud.toQuery()
    }
  },
  created() {
    RoleApi.getLevel().then(data => {
      this.level = data.level
    })
  },
  methods: {
    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.crud.query.userId = this.userId
    },
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
