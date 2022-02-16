<template>
  <el-drawer
    v-if="dictDetail"
    :title="'“' + dictDetail.name + '” 字典选项'"
    :visible.sync="showDrawer"
    size="50%"
    direction="rtl"
  >
    <el-card shadow="never">
      <div class="app-container">
        <!--工具栏-->
        <div class="head-container">
          <div v-if="crud.props.searchToggle">
            <el-row>
              <el-col :span="8">
                <el-input
                  v-model="query.code"
                  clearable
                  size="small"
                  placeholder="输入字典选项编码查询"
                  class="filter-item"
                  style="width: 90%"
                  @keyup.enter.native="crud.toQuery"
                />
              </el-col>
              <el-col :span="8">
                <el-input
                  v-model="query.name"
                  clearable
                  size="small"
                  placeholder="输入字典选项名称查询"
                  class="filter-item"
                  style="width: 90%"
                  @keyup.enter.native="crud.toQuery"
                />
              </el-col>
              <template v-if="crud.showExtendSearch">
                <el-col :span="8">
                  <el-select
                    v-model="query.disabled"
                    clearable
                    size="small"
                    placeholder="状态"
                    class="filter-item"
                    style="width: 90%"
                    @change="crud.toQuery"
                  >
                    <el-option
                      v-for="item in dict['disabled_status'].options"
                      :key="item.code"
                      :label="item.name"
                      :value="item.code"
                    />
                  </el-select>
                </el-col>
              </template>
              <el-col :span="8">
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
          <TableOperation />
        </div>
        <!--表格渲染-->
        <el-table
          ref="table"
          v-loading="crud.loading"
          :data="crud.data"
          highlight-current-row
          style="width: 100%;"
          @selection-change="crud.selectionChangeHandler"
        >
          <el-table-column type="selection" width="50" />
          <el-table-column prop="id" label="ID" width="50" />
          <el-table-column prop="code" label="字典选项编码" :show-overflow-tooltip="true" />
          <el-table-column prop="name" label="字典选项名称" :show-overflow-tooltip="true" />
          <el-table-column prop="note" label="字典选项备注" :show-overflow-tooltip="true" />
          <el-table-column prop="disabled" label="是否启用" width="100">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.disabled"
                active-color="#409EFF"
                inactive-color="#F56C6C"
                :active-value="false"
                :inactive-value="true"
                operation-type
                @change="changeStatus(scope.row, scope.row.disabled)"
              />
            </template>
          </el-table-column>
          <el-table-column
            v-if="checkPer(['admin', 'sys:dict:edit', 'sys:dict:del'])"
            label="操作"
            width="130px"
            fixed="right"
          >
            <template slot-scope="scope">
              <TableColumnOperation :data="scope.row" :permission="permission" />
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->
        <Pagination />
        <!--表单组件-->
        <Form :dict="dict" :dict-id="dictId" />
      </div>
    </el-card>
  </el-drawer>
</template>

<script>
import CRUD, { form, header, presenter } from '@crud/crud'
import Pagination from '@crud/Pagination'
import TableOperation from '@crud/TableOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import ElDragDialog from '@/directive/el-drag-dialog'
import Form from './DictOptionForm'
import DictOptionApi from './DictOptionApi'

/**
 * 表单默认值
 */
const defaultForm = { id: null, dictId: null, code: null, name: null, note: null, disabled: false }
export default {
  name: 'DictOptionPage',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, Form },
  directives: { ElDragDialog },
  cruds() {
    return [
      CRUD({
        title: '字典选项',
        url: 'sys/dict/option',
        importUrl: 'sys/dict/option/import/list',
        optShow: { all: true },
        crudMethod: { ...DictOptionApi },
        query: { dictId: null },
        sort: ['id,asc'],
        queryOnPresenterCreated: false
      })
    ]
  },
  mixins: [presenter(), header(), form(defaultForm)],
  props: {
    display: {
      type: Boolean,
      required: true,
      default() {
        return false
      }
    }
  },
  /**
   * 设置数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      dictId: null,
      dictDetail: null,
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        code: [{ required: true, message: '请输入字典选项编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入字典选项名称', trigger: 'blur' }],
        disabled: [{ required: true, message: '请选择是否启用', trigger: 'blur' }]
      },
      permission: {
        add: ['admin', 'sys:dict:add'],
        edit: ['admin', 'sys:dict:edit'],
        del: ['admin', 'sys:dict:del']
      }
    }
  },
  computed: {
    // 计算属性的 getter
    showDrawer: {
      // getter
      get: function() {
        return this.display
      },
      // setter
      set: function(val) {
        this.$emit('update:display', val)
      }
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      this.crud.query.dictId = this.dictId
      this.form.dictId = this.dictId
      return true
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
          DictOptionApi.edit(data)
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

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
