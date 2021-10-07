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
              v-model="query.fileName"
              clearable
              placeholder="请输入实际文件名"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.namespace"
              clearable
              placeholder="请输入命名空间。一般对应业务系统"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.tag"
                clearable
                placeholder="请输入标签。供业务系统使用"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.originName"
                clearable
                placeholder="请输入源文件名"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.storeType"
                clearable
                placeholder="请输入文件存储服务类型"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.storeUrl"
                clearable
                placeholder="请输入文件存储路径"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.content"
                clearable
                placeholder="请输入文件内容"
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
      <el-table-column prop="fileName" label="实际文件名" />
      <el-table-column prop="namespace" label="命名空间。一般对应业务系统" />
      <el-table-column prop="tag" label="标签。供业务系统使用" />
      <el-table-column prop="originName" label="源文件名" />
      <el-table-column prop="storeType" label="文件存储服务类型" />
      <el-table-column prop="storeUrl" label="文件存储路径" />
      <el-table-column prop="content" label="文件内容" />
      <el-table-column v-if="checkPer(['admin','tool:file/content:edit','tool:file/content:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <FileContentForm />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import FileContentApi from './FileContentApi'
import FileContentForm from './FileContentForm'

export default {
  name: 'FileContentList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker, FileContentForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '文件内容表',
      url: 'tool/file/content',
      sort: ['id,asc',],
      crudMethod: { ...FileContentApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tool:file/content:add'],
        edit: ['admin', 'tool:file/content:edit'],
        del: ['admin', 'tool:file/content:del']
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
