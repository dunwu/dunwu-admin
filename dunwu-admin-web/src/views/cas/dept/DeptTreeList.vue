<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 搜索 -->
      <div v-if="crud.props.searchToggle">
        <el-input
          v-model="query.name"
          clearable
          size="small"
          placeholder="输入部门名称搜索"
          style="width: 30%;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-select
          v-model="query.disabled"
          clearable
          size="small"
          placeholder="状态"
          class="filter-item"
          style="width: 30%;"
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
      <TableOperation :permission="permission" />
    </div>

    <!--表格-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      row-key="id"
      lazy
      border
      highlight-current-row
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :load="loadDeptTreeList"
      @current-change="handleCurrentChange"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="id" />
      <el-table-column label="部门名称" prop="name" />
      <el-table-column label="状态" prop="disabled">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.disabled"
            :disabled="scope.row.id === 1"
            active-color="#409EFF"
            inactive-color="#F56C6C"
            :active-value="false"
            :inactive-value="true"
            @change="changeStatus(scope.row, scope.row.disabled)"
          />
        </template>
      </el-table-column>
    </el-table>

    <!--表单-->
    <From :permission="permission" />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import From from './DeptForm'
import DeptApi from '@/api/cas/dept'

export default {
  name: 'DeptTreeList',
  components: {
    TableOperation,
    TableQueryOperation,
    From
  },
  cruds() {
    return CRUD({
      title: '部门',
      url: 'cas/dept',
      tableType: 'tree',
      crudMethod: { ...DeptApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  /**
   * 数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      /**
       * 权限表达式
       */
      permission: {
        add: ['admin', 'cas:dept:add'],
        del: ['admin', 'cas:dept:del'],
        edit: ['admin', 'cas:dept:edit'],
        view: ['admin', 'cas:dept:view']
      }
    }
  },
  methods: {
    /**
     * 获取部门树形列表
     */
    loadDeptTreeList(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        DeptApi.treeList(params).then(res => {
          resolve(res)
        })
      }, 100)
    },
    /**
     * 切换禁用状态
     */
    changeStatus(data, val) {
      this.$confirm(
        '此操作将 "' + this.dict.label.disabled_status[val] + '" ' + data.name + '部门, 是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          DeptApi.edit(data)
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
    }
  }
}
</script>

<style lang="scss" scoped></style>
