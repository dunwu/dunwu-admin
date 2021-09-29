<template>
  <div class="app-container">
    <div class="head-container">
      <Search />
      <TableOperation>
        <el-button
          slot="left"
          class="filter-item"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :loading="crud.delAllLoading"
          @click="confirmDelAll()"
        >
          清空
        </el-button>
      </TableOperation>
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      style="width: 100%;"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="id">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="日志级别">
              <span>{{ props.row.level }}</span>
            </el-form-item>
            <el-form-item label="业务类型">
              <span>{{ props.row.bizType }}</span>
            </el-form-item>
            <el-form-item label="操作类型">
              <span>{{ props.row.operateType }}</span>
            </el-form-item>
            <el-form-item label="日志消息">
              <span>{{ props.row.message }}</span>
            </el-form-item>
            <el-form-item label="日志异常消息">
              <span>{{ props.row.exceptionMessage }}</span>
            </el-form-item>
            <el-form-item label="请求类">
              <span>{{ props.row.className }}</span>
            </el-form-item>
            <el-form-item label="请求方法">
              <span>{{ props.row.methodName }}</span>
            </el-form-item>
            <el-form-item label="请求参数">
              <span>{{ props.row.params }}</span>
            </el-form-item>
            <el-form-item label="操作者ID">
              <span>{{ props.row.operatorId }}</span>
            </el-form-item>
            <el-form-item label="操作者用户名">
              <span>{{ props.row.operatorName }}</span>
            </el-form-item>
            <el-form-item label="服务端IP地址">
              <span>{{ props.row.serverIp }}</span>
            </el-form-item>
            <el-form-item label="客户端IP地址">
              <span>{{ props.row.clientIp }}</span>
            </el-form-item>
            <el-form-item label="客户端地理位置">
              <span>{{ props.row.clientLocation }}</span>
            </el-form-item>
            <el-form-item label="客户端设备">
              <span>{{ props.row.clientDevice }}</span>
            </el-form-item>
            <el-form-item label="HTTP请求的耗时">
              <span>{{ props.row.requestTime }}ms</span>
            </el-form-item>
            <el-form-item label="日志记录时间">
              <span>{{ props.row.createTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="level" label="日志级别">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.level === 'ERROR'" type="danger">{{ scope.row.level }}</el-tag>
          <el-tag v-else type="primary">{{ scope.row.level }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="bizType" label="业务类型" />
      <el-table-column prop="operateType" label="操作类型" />
      <el-table-column prop="message" label="日志内容" />
      <el-table-column prop="operatorName" label="操作者" />
      <el-table-column prop="clientIp" label="IP" />
      <el-table-column :show-overflow-tooltip="true" prop="clientLocation" label="IP来源" />
      <el-table-column prop="clientDevice" label="客户端设备" />
      <el-table-column prop="requestTime" label="请求耗时">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.requestTime <= 300">{{ scope.row.requestTime }}ms</el-tag>
          <el-tag v-else-if="scope.row.requestTime <= 1000" type="warning">{{ scope.row.requestTime }}ms</el-tag>
          <el-tag v-else type="danger">{{ scope.row.requestTime }}ms</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建日期" width="180px" />
    </el-table>
    <!--分页组件-->
    <Pagination />
  </div>
</template>

<script>
import Search from './search'
import { delAllInfo } from './api'
import CRUD, { presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import logApi from '@/api/system/log'

export default {
  name: 'Log',
  components: { Search, TableOperation, Pagination },
  cruds() {
    return CRUD({ title: '日志', url: 'sys/log', crudMethod: { ...logApi }})
  },
  mixins: [presenter()],
  created() {
    this.crud.optShow = {
      add: false,
      edit: false,
      del: false,
      exportPage: true
    }
  },
  methods: {
    confirmDelAll() {
      this.$confirm(`确认清空所有操作日志吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.crud.delAllLoading = true
          delAllInfo()
            .then(res => {
              this.crud.delAllLoading = false
              this.crud.dleChangePage(1)
              this.crud.delSuccessNotify()
              this.crud.toQuery()
            })
            .catch(err => {
              this.crud.delAllLoading = false
              console.log(err.response.data.msg)
            })
        })
        .catch(() => {})
    }
  }
}
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 150px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
.demo-table-expand .el-form-item__content {
  font-size: 12px;
}
</style>
