<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.appName"
          clearable
          placeholder="输入应用名称查询"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <date-range-picker v-model="query.createTime" class="date-item" />
        <TableQueryOperation />
      </div>
      <TableOperation :permission="permission">
        <template slot="right">
          <el-button
            v-permission="['admin', 'deploy:add']"
            :disabled="!selectIndex"
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-upload"
            @click="openDeployRollbackForm"
          >
            系统还原
          </el-button>
          <el-button
            v-permission="['admin', 'deploy:add']"
            :disabled="!selectIndex"
            class="filter-item"
            size="mini"
            type="primary"
            icon="el-icon-upload"
            @click="serverStatus"
          >
            状态查询
          </el-button>
          <el-button
            v-permission="['admin', 'deploy:add']"
            :disabled="!selectIndex"
            class="filter-item"
            size="mini"
            type="success"
            icon="el-icon-upload"
            @click="startServer"
          >
            启动
          </el-button>
          <el-button
            v-permission="['admin', 'deploy:add']"
            :disabled="!selectIndex"
            class="filter-item"
            size="mini"
            type="danger"
            icon="el-icon-upload"
            @click="stopServer"
          >
            停止
          </el-button>
          <el-button
            v-permission="['admin', 'deploy:add']"
            :disabled="!selectIndex"
            class="filter-item"
            size="mini"
            type="warning"
            icon="el-icon-upload"
            @click="deploy"
          >
            一键部署
          </el-button>
        </template>
      </TableOperation>
    </div>
    <!--表单组件-->
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.title"
      width="500px"
    >
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="应用" prop="app.id">
          <el-select v-model.number="form.app.id" placeholder="请选择" style="width: 370px">
            <el-option v-for="item in apps" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="服务器" prop="servers">
          <el-select v-model="form.servers" multiple placeholder="请选择" style="width: 370px">
            <el-option v-for="item in servers" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="form.note"
            style="width: 370px"
            placeholder="请输入备注"
            type="textarea"
            rows="3"
            maxlength="150"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
    <!--统还原组件-->
    <DeployRollbackForm ref="deployRollbackForm" :key="times" :app-name="appName" />
    <DeployForm ref="deploy" />
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      highlight-current-row
      stripe
      style="width: 100%"
      @selection-change="handleCurrentChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="app.name" label="应用名称" />
      <el-table-column prop="servers" label="服务器列表">
        <template slot-scope="scope">
          <el-tag v-for="server in scope.row.servers" :key="server.name" style="margin-left: 5px; margin-right: 5px">
            {{ server.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="部署日期" />
      <el-table-column
        v-if="checkPer(['admin', 'deploy:edit', 'deploy:del'])"
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
  </div>
</template>

<script>
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import AppApi from './AppApi'
import ServerApi from './ServerApi'
import DeployApi from './DeployApi'
import DeployForm from './DeployForm'
import DeployRollbackForm from './DeployRollbackForm'

const defaultForm = { id: null, app: { id: null }, servers: [] }
export default {
  name: 'DeployList',
  components: {
    DeployForm,
    DeployRollbackForm,
    Pagination,
    TableOperation,
    TableQueryOperation,
    TableColumnOperation,
    DateRangePicker
  },
  cruds() {
    return CRUD({ title: '部署', url: 'deploy', crudMethod: { ...DeployApi }})
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    return {
      currentRow: {},
      selectIndex: '',
      appName: '',
      urlHistory: '',
      times: 0,
      appId: '',
      deployId: '',
      apps: [],
      servers: [],
      permission: {
        add: ['admin', 'deploy:add'],
        edit: ['admin', 'deploy:edit'],
        del: ['admin', 'deploy:del']
      },
      rules: {
        'app.id': [{ required: true, message: '应用不能为空', trigger: 'blur', type: 'number' }],
        servers: [{ required: true, message: '服务器不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    [CRUD.HOOK.beforeRefresh]() {
      this.selectIndex = ''
      return true
    },
    // 添加编辑前做的操作
    [CRUD.HOOK.beforeToCU](crud, form) {
      this.initSelect()
      const servers = []
      form.servers.forEach(function(deploy, index) {
        servers.push(deploy.id)
      })
      this.form.servers = servers
    },
    // 提交前
    [CRUD.HOOK.beforeSubmit]() {
      const servers = []
      this.form.servers.forEach(function(data, index) {
        const deploy = { id: data }
        servers.push(deploy)
      })
      this.form.servers = servers
      return true
    },
    deploy() {
      this.$refs.deploy.dialog = true
      this.$refs.deploy.deployInfo = this.currentRow
    },
    openDeployRollbackForm() {
      this.$refs.deployRollbackForm.dialog = true
    },
    handleCurrentChange(selection) {
      this.crud.selections = selection
      if (selection.length === 1) {
        const row = selection[0]
        this.selectIndex = row.id
        this.currentRow = row
        this.appName = row.app.name
        this.times = this.times + 1
        this.appId = row.appId
        this.deployId = row.id
      } else {
        this.currentRow = {}
        this.selectIndex = ''
      }
    },
    startServer() {
      DeployApi.startServer(JSON.stringify(this.currentRow))
        .then(res => {
          this.$message({ message: res, type: 'success' })
        })
        .catch(err => {
          this.$message({ message: err, type: 'error' })
        })
    },
    stopServer() {
      DeployApi.stopServer(JSON.stringify(this.currentRow))
        .then(res => {
          this.$message({ message: res, type: 'success' })
        })
        .catch(err => {
          this.$message({ message: err, type: 'error' })
        })
    },
    serverStatus() {
      DeployApi.getServerStatus(JSON.stringify(this.currentRow))
        .then(res => {
          this.$message({ message: res, type: 'success' })
        })
        .catch(err => {
          this.$message({ message: err, type: 'error' })
        })
    },
    initSelect() {
      AppApi.list().then(data => {
        this.apps = data
      })
      ServerApi.list().then(data => {
        this.servers = data
      })
    }
  }
}
</script>

<style scoped></style>
