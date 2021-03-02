<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-input
          v-model="query.name"
          clearable
          size="small"
          placeholder="请输入表名"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <queryOperation />
      </div>
      <crudOperation>
        <el-tooltip
          slot="right"
          class="item"
          effect="dark"
          content="数据库中表字段变动时使用该功能"
          placement="top-start"
        >
          <el-button
            class="filter-item"
            size="mini"
            type="success"
            icon="el-icon-refresh"
            :loading="syncLoading"
            :disabled="crud.selections.length === 0"
            @click="sync"
          >
            同步
          </el-button>
        </el-tooltip>
      </crudOperation>
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      style="width: 100%;"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" prop="name" label="Table名称" />
      <el-table-column :show-overflow-tooltip="true" prop="comment" label="Table注释" />
      <el-table-column :show-overflow-tooltip="true" prop="engine" label="数据库引擎" />
      <el-table-column :show-overflow-tooltip="true" prop="coding" label="字符编码集" />
      <el-table-column prop="createTime" label="创建日期" />
      <el-table-column label="操作" width="160px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" style="margin-right: 2px" type="text">
            <router-link :to="'/sys-tools/generator/preview/' + scope.row.name">
              预览
            </router-link>
          </el-button>
          <el-button
            size="mini"
            style="margin-left: -1px;margin-right: 2px"
            type="text"
            @click="toDownload(scope.row.name)"
          >
            下载
          </el-button>
          <el-button size="mini" style="margin-left: -1px;margin-right: 2px" type="text">
            <router-link :to="'/sys-tools/generator/config/' + scope.row.schemaName + '/' + scope.row.name">
              配置
            </router-link>
          </el-button>
          <el-button type="text" style="margin-left: -1px" size="mini" @click="toGen(scope.row.name)">
            生成
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import generatorApi from '@/api/generator/generatorApi'
import { downloadFile } from '@/utils/index'
import CRUD, { presenter, header } from '@crud/crud'
import queryOperation from '@crud/Query.operation'
import crudOperation from '@crud/CRUD.operation'
import pagination from '@crud/Pagination'

export default {
  name: 'GeneratorIndex',
  components: { pagination, crudOperation, queryOperation },
  cruds() {
    return CRUD({ url: 'api/generator/table/all' })
  },
  mixins: [presenter(), header()],
  data() {
    return {
      syncLoading: false
    }
  },
  created() {
    this.crud.optShow = { add: false, edit: false, del: false, exportPage: false }
  },
  methods: {
    toGen(name) {
      // 生成代码
      generatorApi.generator(name, 2).then(data => {
        this.$notify({
          title: '生成成功',
          type: 'success',
          duration: 2500
        })
      })
    },
    toDownload(name) {
      // 打包下载
      generatorApi.generator(name, 3).then(data => {
        downloadFile(data, name, 'zip')
      })
    },
    sync() {
      const tables = []
      this.crud.selections.forEach(val => {
        tables.push(val.name)
      })
      this.syncLoading = true
      generatorApi
        .sync(tables)
        .then(() => {
          this.crud.refresh()
          this.crud.notify('同步成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
          this.syncLoading = false
        })
        .then(() => {
          this.syncLoading = false
        })
    }
  }
}
</script>

<style scoped></style>
