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
              v-model="query.code"
              clearable
              placeholder="请输入字典编码"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.name"
              clearable
              placeholder="请输入字典名称"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.note"
                clearable
                placeholder="请输入备注"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-select v-model="query.disabled" filterable placeholder="请选择是否禁用">
                <el-option
                  v-for="item in dict.disabled_status.options"
                  :key="item.code"
                  :label="item.name"
                  :value="item.code"
                  :disabled="item.disabled"
                />
              </el-select>
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
      <el-table-column prop="id" label="ID" :sortable="'custom'" :show-overflow-tooltip="true" />
      <el-table-column prop="code" label="字典编码" :show-overflow-tooltip="true" />
      <el-table-column prop="name" label="字典名称" :show-overflow-tooltip="true" />
      <el-table-column prop="note" label="备注" :show-overflow-tooltip="true" />
      <el-table-column prop="disabled" label="是否禁用" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ dict.label.disabled_status[scope.row.disabled] }}
        </template>
      </el-table-column>
      <el-table-column prop="updateBy" label="更新者" :show-overflow-tooltip="true" />
      <el-table-column prop="updateTime" label="更新时间" :show-overflow-tooltip="true" />
      <el-table-column v-if="checkPer(['admin', 'sys:dict:edit', 'sys:dict:del'])" label="操作" width="150px">
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单组件-->
    <DictForm />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import DictApi from './DictApi'
import DictForm from './DictForm'

export default {
  name: 'DictList',
  components: { Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DictForm },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '数据字典',
      url: 'sys/dict',
      sort: ['id,asc'],
      crudMethod: { ...DictApi }
    })
  },
  /**
   * 数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      permission: {
        add: ['admin', 'sys:dict:add'],
        edit: ['admin', 'sys:dict:edit'],
        del: ['admin', 'sys:dict:del']
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
