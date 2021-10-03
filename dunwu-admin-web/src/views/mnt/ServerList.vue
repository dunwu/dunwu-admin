<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-row>
          <el-col :span="8">
            <el-input
              v-model="query.id"
              clearable
              placeholder="请输入ID"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="8">
            <el-input
              v-model="query.name"
              clearable
              placeholder="请输入名称"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <date-range-picker v-model="query.createTimeRange" type="datetimerange" class="filter-item" />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <el-input
                v-model="query.ip"
                clearable
                placeholder="请输入IP地址"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <el-input
                v-model="query.port"
                clearable
                placeholder="请输入端口"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <el-col :span="8">
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
      <el-table-column prop="id" label="ID" :sortable="'custom'" />
      <el-table-column prop="account" label="账号" />
      <el-table-column prop="ip" label="IP地址" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="port" label="端口" />
      <el-table-column prop="createBy" label="创建者" />
      <el-table-column prop="updateBy" label="更新者" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column prop="updateTime" label="更新时间" />
      <el-table-column v-if="checkPer(['admin', 'mnt:server:edit', 'mnt:server:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <ServerForm />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import ServerApi from './ServerApi'
import ServerForm from './ServerForm'

export default {
  name: 'ServerList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, ServerForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '服务器配置表',
      url: 'mnt/server',
      sort: ['id,asc'],
      crudMethod: { ...ServerApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'mnt:server:add'],
        edit: ['admin', 'mnt:server:edit'],
        del: ['admin', 'mnt:server:del']
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

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
