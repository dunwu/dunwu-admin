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
              v-model="query.deployId"
              clearable
              placeholder="请输入部署编号"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <date-range-picker v-model="query.deployDateRange" type="datetimerange" class="filter-item" />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <el-input
                v-model="query.appName"
                clearable
                placeholder="请输入应用名称"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <el-input
                v-model="query.deployUser"
                clearable
                placeholder="请输入部署用户"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="8">
              <el-input
                v-model="query.ip"
                clearable
                placeholder="请输入服务器IP"
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
      <el-table-column prop="deployId" label="部署编号" />
      <el-table-column prop="appName" label="应用名称" />
      <el-table-column prop="deployDate" label="部署日期" />
      <el-table-column prop="deployUser" label="部署用户" />
      <el-table-column prop="ip" label="服务器IP" />
      <el-table-column
        v-if="checkPer(['admin', 'mnt:deployHistory:edit', 'mnt:deployHistory:del'])"
        label="操作"
        width="150px"
      >
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import DeployHistoryApi from './DeployHistoryApi'

export default {
  name: 'DeployHistoryList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '部署历史管理',
      url: 'mnt/deploy/history',
      sort: ['id,asc'],
      crudMethod: { ...DeployHistoryApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'mnt:deployHistory:add'],
        edit: ['admin', 'mnt:deployHistory:edit'],
        del: ['admin', 'mnt:deployHistory:del']
      }
    }
  },
  created() {
    this.crud.optShow.add = false
    this.crud.optShow.edit = false
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
