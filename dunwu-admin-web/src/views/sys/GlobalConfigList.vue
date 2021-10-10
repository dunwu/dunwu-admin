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
              v-model="query.appCode"
              clearable
              placeholder="请输入应用编码"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.moduleCode"
              clearable
              placeholder="请输入模块编码"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.namespace"
                clearable
                placeholder="请输入命名空间"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.code"
                clearable
                placeholder="请输入配置项编码"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.name"
                clearable
                placeholder="请输入配置项配置名称"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.isDisabled"
                clearable
                placeholder="请输入是否禁用：1 表示禁用；0 表示启用"
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
      <el-table-column prop="appCode" label="应用编码" />
      <el-table-column prop="moduleCode" label="模块编码" />
      <el-table-column prop="namespace" label="命名空间" />
      <el-table-column prop="code" label="配置项编码" />
      <el-table-column prop="name" label="配置项配置名称" />
      <el-table-column prop="updateBy" label="更新者" />
      <el-table-column prop="updateTime" label="更新时间" />
      <el-table-column v-if="checkPer(['admin', 'sys:config:edit', 'sys:config:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <GlobalConfigForm />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import GlobalConfigApi from './GlobalConfigApi'
import GlobalConfigForm from './GlobalConfigForm'

export default {
  name: 'GlobalConfigList',
  components: {
    Pagination,
    TableOperation,
    TableQueryOperation,
    TableColumnOperation,
    GlobalConfigForm
  },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '系统全局配置',
      url: 'sys/config',
      sort: ['id,asc'],
      crudMethod: { ...GlobalConfigApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'sys:config:add'],
        edit: ['admin', 'sys:config:edit'],
        del: ['admin', 'sys:config:del']
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
