<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-row>
          <el-col :span="6">
            <el-input
              v-model="query.id"
              clearable
              placeholder="请输入ID"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.appId"
              clearable
              placeholder="请输入应用编号"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.note"
              clearable
              placeholder="请输入备注"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.createBy"
                clearable
                placeholder="请输入创建者"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.updateBy"
                clearable
                placeholder="请输入更新者"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.createTime"
                clearable
                placeholder="请输入创建时间"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.updateTime"
                clearable
                placeholder="请输入更新时间"
                style="width: 90%;"
                class="filter-item"
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
      <el-table-column prop="id" label="ID" :sortable="'custom'" />
      <el-table-column prop="appId" label="应用编号" />
      <el-table-column prop="note" label="备注" />
      <el-table-column prop="createBy" label="创建者" />
      <el-table-column prop="updateBy" label="更新者" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column prop="updateTime" label="更新时间" />
      <el-table-column v-if="checkPer(['admin','mnt:deploy:edit','mnt:deploy:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <DeployForm />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import DeployApi from './DeployApi'
import DeployForm from './DeployForm'

export default {
  name: 'DeployList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, DeployForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '部署管理',
      url: 'mnt/deploy',
      sort: ['id,asc',],
      crudMethod: { ...DeployApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'mnt:deploy:add'],
        edit: ['admin', 'mnt:deploy:edit'],
        del: ['admin', 'mnt:deploy:del']
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
