<template>
  <div class="app-container">
    <!--查询组件-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-row>
          <el-col :span="6">
            <el-input
              v-model="query.id"
              clearable
              size="small"
              placeholder="输入字典类型ID查询"
              class="filter-item"
              style="width: 90%"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.code"
              clearable
              size="small"
              placeholder="输入字典类型编码查询"
              class="filter-item"
              style="width: 90%"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.name"
                clearable
                size="small"
                placeholder="输入字典类型名称查询"
                class="filter-item"
                style="width: 90%"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-select
                v-model="query.disabled"
                clearable
                size="small"
                :placeholder="'请选择' + dict['disabled_status'].name"
                class="filter-item"
                style="width: 90%"
                @keyup.enter.native="crud.toQuery"
              >
                <el-option
                  v-for="item in dict['disabled_status'].options"
                  :key="item.code"
                  :label="item.name"
                  :value="item.code"
                  :disabled="item.disabled"
                />
              </el-select>
            </el-col>
          </template>
          <el-col :span="6">
            <TableQueryOperation />
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
      <TableOperation :permission="permission">
        <el-button slot="left" class="filter-item" type="info" icon="el-icon-coffee-cup" size="mini">
          <router-link :to="'/sys/dict/DictImportedByEnumForm'">根据Java枚举导入字典</router-link>
        </el-button>
      </TableOperation>
    </div>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      border
      highlight-current-row
      style="width: 100%"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="50" />
      <el-table-column prop="id" label="ID" width="50" />
      <el-table-column prop="code" label="字典编码" :show-overflow-tooltip="true" />
      <el-table-column prop="name" label="字典名称" :show-overflow-tooltip="true" />
      <el-table-column prop="note" label="字典描述" :show-overflow-tooltip="true" />
      <el-table-column prop="disabled" label="是否启用" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.disabled"
            active-color="#409EFF"
            inactive-color="#F56C6C"
            :active-value="false"
            :inactive-value="true"
            @change="changeStatus(scope.row, scope.row.disabled)"
          />
        </template>
      </el-table-column>
      <el-table-column v-if="checkPer(['admin', 'sys:dict:edit', 'sys:dict:del'])" label="操作">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission">
            <template slot="right">
              <el-divider direction="vertical" />
              <el-button slot="reference" size="mini" type="text" @click="handleCurrentChange(scope.row)">
                配置字典
              </el-button>
            </template>
          </TableColumnOperation>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <Pagination />
    <!--表单组件-->
    <Form />

    <DictOptionPage ref="dictOption" :display.sync="showDictOptionPage" :permission="permission" />
  </div>
</template>

<script>
import CRUD, { header, presenter } from '@crud/crud'
import Pagination from '@crud/Pagination'
import TableOperation from '@crud/TableOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import ElDragDialog from '@/directive/el-drag-dialog'
import DictOptionPage from './DictOptionPage'
import Form from './DictForm'
import DictApi from './DictApi'
import { getToken } from '@/utils/auth'

export default {
  name: 'DictPage',
  components: {
    Pagination,
    TableOperation,
    TableQueryOperation,
    TableColumnOperation,
    DictOptionPage,
    Form
  },
  directives: { ElDragDialog },
  cruds() {
    return [
      CRUD({
        title: '字典',
        url: 'sys/dict',
        importUrl: 'sys/dict/import/list',
        optShow: { all: true },
        crudMethod: { ...DictApi }
      })
    ]
  },
  mixins: [presenter(), header()],
  /**
   * 设置数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      // fileList: [],
      showDictOptionPage: false,
      headers: {
        DunwuToken: getToken()
      },
      permission: {
        add: ['admin', 'sys:dict:add'],
        edit: ['admin', 'sys:dict:edit'],
        del: ['admin', 'sys:dict:del']
      }
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      if (this.$refs['dictOption']) {
        this.$refs['dictOption'].query.dictId = null
      }
      return true
    },
    // 选中字典后，设置字典详情数据
    handleCurrentChange(val) {
      if (val) {
        this.$refs['dictOption'].dictId = val.id
        this.$refs['dictOption'].dictDetail = val
        this.$refs['dictOption'].query.dictId = val.id
        this.$refs['dictOption'].crud.toQuery()
        this.showDictOptionPage = true
      }
    },
    /**
     * 切换禁用状态
     */
    changeStatus(data, val) {
      this.$confirm('此操作将 "' + this.dict.label.disabled_status[val] + '" ' + data.name + ', 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          DictApi.edit(data)
            .then(() => {
              this.crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, this.dict.label.disabled_status[val] + '成功')
            })
            .catch(err => {
              data.disabled = !data.disabled
              console.error(err.data.msg)
            })
        })
        .catch(() => {
          data.disabled = !data.disabled
        })
    }
  }
}
</script>

<style scoped></style>
