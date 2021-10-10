<template>
  <div class="app-container">
    <!--表单组件-->
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible="crud.status.cu > 0"
      :title="crud.status.title"
      width="500px"
    >
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="字典编码" prop="code">
          <el-input v-model="form.code" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="字典名称" prop="name">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="状态" prop="disabled">
          <el-radio
            v-for="item in dict['disabled_status'].options"
            :key="item.id"
            v-model="form.disabled"
            :label="item.code"
          >
            {{ item.name }}
          </el-radio>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.note" style="width: 370px;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
    <!-- 字典列表 -->
    <el-row :gutter="10">
      <el-col :xs="24" :sm="24" :md="10" :lg="11" :xl="11" style="margin-bottom: 10px">
        <el-card class="box-card">
          <!--工具栏-->
          <div class="head-container">
            <div v-if="crud.props.searchToggle">
              <!-- 搜索 -->
              <el-input
                v-model="query.blurry"
                clearable
                size="small"
                placeholder="输入名称或者描述搜索"
                style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
              <TableQueryOperation />
            </div>
            <TableOperation :permission="permission" />
          </div>
          <!--表格渲染-->
          <el-table
            ref="table"
            v-loading="crud.loading"
            :data="crud.data"
            highlight-current-row
            style="width: 100%;"
            @selection-change="crud.selectionChangeHandler"
            @current-change="handleCurrentChange"
          >
            <el-table-column type="selection" width="55" />
            <el-table-column :show-overflow-tooltip="true" prop="code" label="编码" />
            <el-table-column :show-overflow-tooltip="true" prop="name" label="名称" />
            <el-table-column :show-overflow-tooltip="true" prop="note" label="描述" />
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
        </el-card>
      </el-col>
      <!-- 字典详情列表 -->
      <el-col :xs="24" :sm="24" :md="14" :lg="13" :xl="13">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>字典详情</span>
            <el-button
              v-if="checkPer(['admin', 'dict:add']) && this.$refs.dictOption && this.$refs.dictOption.query.dictId"
              class="filter-item"
              size="mini"
              style="float: right;padding: 4px 10px"
              type="primary"
              icon="el-icon-plus"
              @click="$refs.dictOption && $refs.dictOption.crud.toAdd()"
            >
              添加
            </el-button>
          </div>
          <DictOption ref="dictOption" :permission="permission" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CRUD, { presenter, header, form } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import DictOption from './DictOption'
import DictApi from './DictApi'

/**
 * 表单默认值
 */
const defaultForm = { id: null, code: null, name: null, note: null, disabled: false, dictOptions: [] }

export default {
  name: 'Dict',
  components: { TableOperation, Pagination, TableQueryOperation, TableColumnOperation, DictOption },
  cruds() {
    return [CRUD({ title: '字典', url: 'sys/dict', crudMethod: { ...DictApi }})]
  },
  mixins: [presenter(), header(), form(defaultForm)],
  /**
   * 设置数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      queryTypeOptions: [{ key: 'name', display_name: '字典名称' }, { key: 'note', display_name: '描述' }],
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
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
      if (this.$refs.dictOption) {
        this.$refs.dictOption.query.dictId = null
      }
      return true
    },
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      form.disabled = `${form.disabled}`
    },
    // 选中字典后，设置字典详情数据
    handleCurrentChange(val) {
      if (val) {
        this.$refs.dictOption.query.dictId = val.id
        this.$refs.dictOption.dictId = val.id
        this.$refs.dictOption.crud.toQuery()
      }
    }
  }
}
</script>

<style scoped></style>
