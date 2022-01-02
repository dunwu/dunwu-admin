<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-row>
          <el-col :span="6">
            <el-input
              v-model="query.bizType"
              clearable
              placeholder="请输入业务类型"
              style="width: 90%"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-select
              v-model="query.success"
              placeholder="请选择操作结果"
              style="width: 90%"
              class="filter-item"
              clearable
            >
              <el-option v-for="item in successOptions" :key="item.code" :label="item.desc" :value="item.code" />
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-select
              v-model="query.operation"
              placeholder="请选择操作类型"
              style="width: 90%"
              class="filter-item"
              clearable
            >
              <el-option v-for="item in operationOptions" :key="item.code" :label="item.desc" :value="item.code" />
            </el-select>
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.className"
                clearable
                placeholder="请输入类名"
                style="width: 90%"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.methodName"
                clearable
                placeholder="请输入方法名"
                style="width: 90%"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.operatorId"
                clearable
                placeholder="请输入操作者ID"
                style="width: 90%"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.operatorName"
                clearable
                placeholder="请输入操作者用户名"
                style="width: 90%"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <date-range-picker
                v-model="query.createTimeRange"
                type="datetimerange"
                class="filter-item"
                style="width: 90%"
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
      border
      stripe
      size="mini"
      @sort-change="crud.changeTableSort"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-descriptions
            title="详细信息"
            :column="4"
            style="margin: 20px"
            :label-style="{ width: '150px' }"
            size="mini"
            class="log-descriptions"
            border
          >
            <el-descriptions-item label="id" span="2">
              <span>{{ scope.row.id }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="业务类型" span="2">
              <span>{{ scope.row.bizType }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="操作类型" span="2">
              <span v-if="scope.row.operation === 'ADD'">添加</span>
              <span v-else-if="scope.row.operation === 'EDIT'">更新</span>
              <span v-else-if="scope.row.operation === 'DEL'">删除</span>
              <span v-else-if="scope.row.operation === 'DEL'">保存</span>
              <span v-else-if="scope.row.operation === 'BATCH_ADD'">批量添加</span>
              <span v-else-if="scope.row.operation === 'BATCH_EDIT'">批量更新</span>
              <span v-else-if="scope.row.operation === 'BATCH_DEL'">批量删除</span>
              <span v-else-if="scope.row.operation === 'BATCH_DEL'">批量保存</span>
              <span v-else-if="scope.row.operation === 'EXPORT_LIST'">根据ID导出</span>
              <span v-else-if="scope.row.operation === 'EXPORT_PAGE'">分页查询导出</span>
              <span v-else>无效</span>
            </el-descriptions-item>
            <el-descriptions-item label="操作结果" span="2">
              <el-tag v-if="scope.row.success" size="mini" type="success">成功</el-tag>
              <el-tag v-else size="mini" type="danger">失败</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="请求类" span="2">
              <el-tag size="mini" type="info">{{ scope.row.className }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="请求方法" span="2">
              <el-tag size="mini" type="info">{{ scope.row.methodName }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="请求参数" span="4">
              <span>{{ scope.row.params }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="基本信息" span="4">
              <span>{{ scope.row.message }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="详情信息" span="4">
              <span>{{ scope.row.detail }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="异常信息" span="4">
              <span>{{ scope.row.exception }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="操作者ID" span="2">
              <span>{{ scope.row.operatorId }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="操作者用户名" span="2">
              <span>{{ scope.row.operatorName }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="服务端IP地址" span="2">
              <span>{{ scope.row.serverIp }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="客户端IP地址" span="2">
              <span>{{ scope.row.clientIp }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="客户端地理位置" span="2">
              <span>{{ scope.row.clientLocation }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="客户端设备" span="2">
              <span>{{ scope.row.clientDevice }}</span>
            </el-descriptions-item>
            <el-descriptions-item label="操作耗时" span="2">
              <el-tag v-if="scope.row.requestTime <= 300" size="mini" type="info">{{ scope.row.requestTime }}ms</el-tag>
              <el-tag v-else-if="scope.row.requestTime <= 1000" size="mini" type="warning">
                {{ scope.row.requestTime }}ms
              </el-tag>
              <el-tag v-else size="mini" type="warning">{{ Math.round(scope.row.requestTime / 1000) }}s</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="操作时间" span="2">
              <span>{{ scope.row.createTime }}</span>
            </el-descriptions-item>
          </el-descriptions>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="50" />
      <el-table-column prop="bizType" label="业务类型" width="150px" :show-overflow-tooltip="true" />
      <el-table-column prop="operation" label="操作类型" width="100px">
        <template slot-scope="scope">
          <span v-if="scope.row.operation === 'ADD'">添加</span>
          <span v-else-if="scope.row.operation === 'EDIT'">更新</span>
          <span v-else-if="scope.row.operation === 'DEL'">删除</span>
          <span v-else-if="scope.row.operation === 'DEL'">保存</span>
          <span v-else-if="scope.row.operation === 'BATCH_ADD'">批量添加</span>
          <span v-else-if="scope.row.operation === 'BATCH_EDIT'">批量更新</span>
          <span v-else-if="scope.row.operation === 'BATCH_DEL'">批量删除</span>
          <span v-else-if="scope.row.operation === 'BATCH_DEL'">批量保存</span>
          <span v-else-if="scope.row.operation === 'EXPORT_LIST'">根据ID导出</span>
          <span v-else-if="scope.row.operation === 'EXPORT_PAGE'">分页查询导出</span>
          <span v-else>无效</span>
        </template>
      </el-table-column>
      <el-table-column prop="success" label="操作结果" width="80px">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.success" size="mini" type="success">成功</el-tag>
          <el-tag v-else size="mini" type="danger">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="requestTime" label="操作耗时" width="100px">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.requestTime <= 300" size="mini" type="info">{{ scope.row.requestTime }}ms</el-tag>
          <el-tag v-else-if="scope.row.requestTime <= 1000" size="mini" type="warning">
            {{ scope.row.requestTime }}ms
          </el-tag>
          <el-tag v-else size="mini" type="warning">{{ Math.round(scope.row.requestTime / 1000) }}s</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="message" label="基本信息" :show-overflow-tooltip="true" />
      <el-table-column prop="operatorName" label="操作者名称" width="100px" />
      <el-table-column prop="createTime" label="操作时间" width="180px" />
    </el-table>
    <!--分页组件-->
    <Pagination />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import LogApi from './LogApi'

export default {
  name: 'LogList',
  components: { Pagination, TableOperation, TableQueryOperation, DateRangePicker },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '系统日志记录',
      url: 'sys/log',
      sort: ['create_time,desc'],
      crudMethod: { ...LogApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'sys:log:add'],
        edit: ['admin', 'sys:log:edit'],
        del: ['admin', 'sys:log:del']
      },
      successOptions: [{ code: true, desc: '成功' }, { code: false, desc: '失败' }],
      operationOptions: [
        { code: 'ADD', desc: '添加' },
        { code: 'EDIT', desc: '更新' },
        { code: 'DEL', desc: '删除' },
        { code: 'SAVE', desc: '保存' },
        { code: 'BATCH_ADD', desc: '批量添加' },
        { code: 'BATCH_EDIT', desc: '批量更新' },
        { code: 'BATCH_DEL', desc: '批量删除' },
        { code: 'BATCH_SAVE', desc: '批量保存' },
        { code: 'EXPORT_LIST', desc: '根据ID导出' },
        { code: 'EXPORT_PAGE', desc: '分页查询导出' }
      ]
    }
  },
  created() {
    this.crud.optShow.add = false
    this.crud.optShow.edit = false
  },
  methods: {
    // confirmDelAll() {
    //   this.$confirm(`确认清空所有操作日志吗?`, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   })
    //     .then(() => {
    //       this.crud.batchDelLoading = true
    //       delAllInfo()
    //         .then(res => {
    //           this.crud.batchDelLoading = false
    //           this.crud.dleChangePage(1)
    //           this.crud.delSuccessNotify()
    //           this.crud.toQuery()
    //         })
    //         .catch(err => {
    //           this.crud.batchDelLoading = false
    //           console.log(err.response.data.msg)
    //         })
    //     })
    //     .catch(() => {})
    // }
  }
}
</script>

<style scoped>
.sys-log-table-expand {
  font-size: 0;
}
.sys-log-table-expand label {
  width: 150px;
  color: #99a9bf;
}
.sys-log-table-expand .el-form-item {
  margin-right: 0 !important;
  margin-bottom: 0 !important;
  width: 100%;
}
.sys-log-table-expand .el-form-item__content {
  font-size: 12px;
}
.el-descriptions__title {
  font-size: 12px !important;
  font-weight: 700;
}
</style>
