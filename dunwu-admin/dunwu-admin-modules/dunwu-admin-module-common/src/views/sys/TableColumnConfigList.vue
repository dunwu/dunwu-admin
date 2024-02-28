<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-row>
          <el-col :span="6">
            <el-input
              v-model="query.id"
              class="filter-item"
              clearable
              placeholder="请输入ID"
              style="width: 90%;"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.schemaName"
              class="filter-item"
              clearable
              placeholder="请输入Schema名称"
              style="width: 90%;"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.tableName"
              class="filter-item"
              clearable
              placeholder="请输入Table名称"
              style="width: 90%;"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.columnName"
                class="filter-item"
                clearable
                placeholder="请输入字段名称"
                style="width: 90%;"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.locked"
                class="filter-item"
                clearable
                placeholder="请输入是否锁定"
                style="width: 90%;"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <el-col :span="6">
            <TableQueryOperation :crud="crud" />
            <el-button v-if="crud.showExtendSearch" type="text" @click="crud.toggleExtendSearch">
              折叠
              <i class="el-icon-arrow-up el-icon--right" />
            </el-button>
            <el-button v-else type="text" @click="crud.toggleExtendSearch">
              展开
              <i class="el-icon-arrow-down el-icon--right" />
            </el-button>
          </el-col>
        </el-row>
      </div>
      <TableOperation :permission="permission" />
    </div>

    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      @sort-change="crud.changeTableSort"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" :sortable="'custom'" label="ID" prop="id" />
      <el-table-column :show-overflow-tooltip="true" label="Schema名称" prop="schemaName" />
      <el-table-column :show-overflow-tooltip="true" label="Table名称" prop="tableName" />
      <el-table-column :show-overflow-tooltip="true" label="字段名称" prop="columnName" />
      <el-table-column :show-overflow-tooltip="true" label="是否锁定" prop="locked" />
      <el-table-column :show-overflow-tooltip="true" label="创建者" prop="createBy" />
      <el-table-column :show-overflow-tooltip="true" label="更新者" prop="updateBy" />
      <el-table-column :show-overflow-tooltip="true" label="操作时间" prop="createTime" />
      <el-table-column :show-overflow-tooltip="true" label="操作时间" prop="updateTime" />
      <el-table-column v-if="checkPer(['admin','sys:column/config:edit','sys:column/config:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <TableColumnConfigForm />
  </div>
</template>

<script>
import CRUD, {crud, header, presenter} from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import TableColumnConfigApi from './TableColumnConfigApi'
import TableColumnConfigForm from './TableColumnConfigForm'

export default {
  name: 'TableColumnConfigList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, TableColumnConfigForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '表字段配置表',
      url: 'sys/column/config',
      sort: ['id,asc',],
      crudMethod: { ...TableColumnConfigApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'sys:column/config:add'],
        edit: ['admin', 'sys:column/config:edit'],
        del: ['admin', 'sys:column/config:del']
      },
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped></style>
