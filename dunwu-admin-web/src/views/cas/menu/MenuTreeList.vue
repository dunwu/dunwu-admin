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
          placeholder="模糊搜索"
          class="filter-item"
          style="width: 30%"
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
      lazy
      border
      highlight-current-row
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :load="getMenus"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" label="菜单标题" width="125px" prop="name" />
      <el-table-column prop="icon" label="图标" width="60px">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon ? scope.row.icon : ''" />
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" prop="expression" label="权限表达式" />
      <el-table-column :show-overflow-tooltip="true" prop="component" label="组件路径" />
      <el-table-column prop="iframe" label="外链" width="75px">
        <template slot-scope="scope">
          <span v-if="scope.row.iframe">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="cache" label="缓存" width="75px">
        <template slot-scope="scope">
          <span v-if="scope.row.cache">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="hidden" label="可见" width="75px">
        <template slot-scope="scope">
          <span v-if="scope.row.hidden">否</span>
          <span v-else>是</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建日期" width="135px" />
      <el-table-column
        v-if="checkPer(['admin', 'menu:edit', 'menu:del'])"
        label="操作"
        width="130px"
        align="center"
        fixed="right"
      >
        <template slot-scope="scope">
          <TableColumnOperation
            :data="scope.row"
            :permission="permission"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column>
    </el-table>

    <!--表单-->
    <From :permission="permission" />
  </div>
</template>

<script>
import menuApi from '@/api/cas/menu'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import From from './MenuForm'

export default {
  name: 'Menu',
  components: { TableOperation, TableQueryOperation, TableColumnOperation, From },
  cruds() {
    return CRUD({
      title: '菜单',
      url: 'cas/menu',
      tableType: 'tree',
      crudMethod: { ...menuApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  data() {
    return {
      /**
       * 权限表达式
       */
      permission: {
        add: ['admin', 'cas:menu:add'],
        del: ['admin', 'cas:menu:del'],
        edit: ['admin', 'cas:menu:edit'],
        view: ['admin', 'cas:menu:view']
      }
    }
  },
  methods: {
    getMenus(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        menuApi.treeList(params).then(res => {
          resolve(res)
        })
      }, 100)
    },
    // 选中图标
    selected(name) {
      this.form.icon = name
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep .vue-treeselect__control,
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  height: 30px;
  line-height: 30px;
}
</style>
