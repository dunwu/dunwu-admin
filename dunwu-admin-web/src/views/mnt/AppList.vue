<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-input
          v-model="query.name"
          clearable
          placeholder="输入名称搜索"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <date-range-picker
          v-model="query.createTimeRange"
          type="datetimerange"
          class="filter-item"
          style="width: 90%"
        />
        <TableQueryOperation />
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
      <el-table-column prop="id" label="ID" :sortable="'custom'" />
      <el-table-column prop="name" label="应用名称" />
      <el-table-column prop="uploadPath" label="上传路径" />
      <el-table-column prop="deployPath" label="部署路径" />
      <el-table-column prop="backupPath" label="备份路径" />
      <el-table-column prop="port" label="应用端口" />
      <el-table-column prop="createBy" label="创建者" />
      <el-table-column prop="updateBy" label="更新者" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column prop="updateTime" label="更新时间" />
      <el-table-column v-if="checkPer(['admin', 'mnt:app:edit', 'mnt:app:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <AppForm />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import AppApi from './AppApi'
import AppForm from './AppForm'

export default {
  name: 'AppList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, AppForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '应用配置',
      url: 'mnt/app',
      sort: ['id,asc'],
      crudMethod: { ...AppApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'mnt:app:add'],
        edit: ['admin', 'mnt:app:edit'],
        del: ['admin', 'mnt:app:del']
      }
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
