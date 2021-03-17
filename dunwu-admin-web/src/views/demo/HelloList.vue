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
              v-model="query.name"
              clearable
              placeholder="请输入名字"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.age"
              clearable
              placeholder="请输入年龄"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.avatar"
                clearable
                placeholder="请输入头像"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <date-range-picker v-model="query.createTimeRange" class="date-item" style="width: 90%" />
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
      <el-table-column prop="name" label="名字" :sortable="'custom'" />
      <el-table-column prop="age" label="年龄" :sortable="'custom'" />
      <el-table-column prop="avatar" label="头像">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px" :src="scope.row.avatar" fit="fill"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column
        v-if="checkPer(['admin', 'demo:hello:edit', 'demo:hello:del'])"
        label="操作"
        width="150px"
        align="center"
      >
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <HelloForm />
  </div>
</template>

<script>
import HelloApi from './HelloApi'
import HelloForm from './HelloForm'
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'

export default {
  name: 'HelloList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, HelloForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '测试',
      url: 'demo/hello',
      sort: 'id,asc',
      crudMethod: { ...HelloApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'demo:hello:add'],
        edit: ['admin', 'demo:hello:edit'],
        del: ['admin', 'demo:hello:del']
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
