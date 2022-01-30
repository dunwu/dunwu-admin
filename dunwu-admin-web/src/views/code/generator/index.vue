<template>
  <div class="app-container">
    <div class="head-container">
      <el-row>
        <el-col :span="6">
          <el-select
            v-model="schemaName"
            clearable
            filterable
            remote
            placeholder="请选择Schema"
            style="width: 90%"
            :remote-method="queryDatabase"
            :loading="schemaLoading"
            @clear="resetQuery"
            @change="selectSchema"
          >
            <el-option v-for="item in schemaOptions" :key="item.id" :label="item.schemaName" :value="item.id">
              <span style="float: left">{{ item.schemaName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-input
            v-if="schemaName !== '' && schemaName !== null"
            v-model="tableName"
            placeholder="请输入表名"
            style="width: 90%"
            @keyup.enter.native="queryTables"
          />
          <el-input
            v-else
            v-model="tableName"
            disabled
            placeholder="请输入表名"
            style="width: 90%"
            @keyup.enter.native="queryTables"
          />
        </el-col>
        <el-col :span="6">
          <span style="width: 90%">
            <el-button type="primary" icon="el-icon-search" :disabled="!dbId" @click="queryTables">
              查询
            </el-button>
            <el-button type="primary" icon="el-icon-refresh-left" @click="resetQuery">
              重置
            </el-button>
            <el-button type="primary">
              <svg-icon icon-class="database" />
              <router-link :to="'/tool/code/database/'">
                配置数据源
              </router-link>
            </el-button>
          </span>
        </el-col>
      </el-row>
    </div>
    <!--表格渲染-->
    <el-table ref="table" v-loading="loading" border stripe size="mini" :data="tables">
      <el-table-column type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" prop="tableName" label="Table名称" />
      <el-table-column :show-overflow-tooltip="true" prop="comment" label="Table注释" />
      <el-table-column :show-overflow-tooltip="true" prop="engine" label="数据库引擎" />
      <el-table-column :show-overflow-tooltip="true" prop="coding" label="字符编码集" />
      <el-table-column label="配置状态" width="80" align="center">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.isGlobalConfigured && scope.row.isTableConfigured && scope.row.isColumnConfigured"
            type="success"
          >
            已配置
          </el-tag>
          <el-tag v-else type="danger">未配置</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建日期" />
      <el-table-column label="操作" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text">
            <router-link
              :to="{
                name: 'code/generator/config',
                params: {
                  dbId: dbId,
                  schemaName: scope.row.schemaName,
                  tableName: scope.row.tableName,
                  isGlobalConfigured: scope.row.isGlobalConfigured,
                  isTableConfigured: scope.row.isTableConfigured,
                  isColumnConfigured: scope.row.isColumnConfigured
                }
              }"
            >
              配置
            </router-link>
          </el-button>
          <el-button
            size="mini"
            type="text"
            :disabled="!(scope.row.isGlobalConfigured && scope.row.isTableConfigured && scope.row.isColumnConfigured)"
          >
            <router-link
              :to="{
                name: 'code/generator/preview',
                params: {
                  dbId,
                  schemaName: scope.row.schemaName,
                  tableName: scope.row.tableName,
                  isGlobalConfigured: scope.row.isGlobalConfigured,
                  isTableConfigured: scope.row.isTableConfigured,
                  isColumnConfigured: scope.row.isColumnConfigured
                }
              }"
            >
              预览
            </router-link>
          </el-button>
          <el-button
            type="text"
            size="mini"
            :disabled="!(scope.row.isGlobalConfigured && scope.row.isTableConfigured && scope.row.isColumnConfigured)"
            @click="toGenerate(scope.row)"
          >
            生成
          </el-button>
          <el-button
            size="mini"
            type="text"
            :disabled="!(scope.row.isGlobalConfigured && scope.row.isTableConfigured && scope.row.isColumnConfigured)"
            @click="toDownload(scope.row)"
          >
            下载
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-Pagination
      :page-size.sync="page.size"
      :total="page.total"
      :current-page.sync="page.page"
      style="margin-top: 8px;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChangeHandler($event)"
      @current-change="pageChangeHandler"
    />
  </div>
</template>

<script>
import codeApi from '@/api/code/codeApi'
import databaseApi from '@/api/code/databaseApi'
import { downloadFile } from '@/utils'

export default {
  name: 'GeneratorIndex',
  mixins: [],
  data() {
    return {
      loading: false,
      schemaLoading: false,
      syncLoading: false,
      dbId: 1,
      schemaName: null,
      tableName: null,
      schemaOptions: [],
      tables: [],
      page: {
        page: 0,
        size: 10,
        total: 0
      }
    }
  },
  created() {
    this.queryDatabase()
  },
  methods: {
    toGenerate(row) {
      // 生成代码
      codeApi.generateCode({ dbId: this.dbId, schemaName: row.schemaName, tableName: row.tableName }).then(data => {
        this.$message({ type: 'success', message: '生成成功' })
      })
    },
    toDownload(row) {
      // 打包下载
      codeApi.downloadCode({ dbId: this.dbId, schemaName: row.schemaName, tableName: row.tableName }).then(data => {
        downloadFile(data, row.tableName, 'zip')
        this.$message({ type: 'success', message: '下载成功' })
      })
    },
    queryDatabase(val) {
      this.schemaLoading = true
      if (val) {
        databaseApi.list({ schemaName: val }).then(res => {
          this.schemaOptions = res
          this.schemaLoading = false
          if (this.schemaOptions.length === 0) {
            this.$notify({ title: '未找到数据源', type: 'warning', message: '请配置数据源' })
          }
        })
      } else {
        databaseApi.list().then(res => {
          this.schemaOptions = res
          this.schemaLoading = false
          if (this.schemaOptions.length === 0) {
            this.$notify({ title: '未找到数据源', type: 'warning', message: '请配置数据源' })
          }
        })
      }
    },
    selectSchema(val) {
      if (val) {
        this.schemaOptions.forEach(i => {
          if (val === i.id) {
            this.dbId = i.id
            this.schemaName = i.schemaName
            this.queryTables()
          }
        })
      }
    },
    queryTables() {
      this.loading = true
      codeApi
        .getAllTableInSchema({
          dbId: this.dbId,
          schemaName: this.schemaName,
          tableName: this.tableName,
          page: this.page.page,
          size: this.page.size
        })
        .then(res => {
          this.loading = false
          this.tables = res.content
          this.page.total = res.totalElements
        })
        .catch(err => {
          this.loading = false
          this.$notify({ title: '查询表数据失败', type: 'error', message: err })
        })
    },
    resetQuery() {
      this.dbId = null
      this.schemaName = null
      this.tableName = null
      this.tables = []
      this.page.total = 0
    },
    // 当前页改变
    pageChangeHandler(val) {
      this.page.page = val
      this.queryTables()
    },
    // 每页条数改变
    sizeChangeHandler(val) {
      this.page.size = val
      this.page.page = 1
      this.queryTables()
    }
  }
}
</script>

<style scoped></style>
