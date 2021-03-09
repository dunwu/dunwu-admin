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
              style="width: 90%"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.name"
              clearable
              placeholder="请输入名字"
              style="width: 90%"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.age"
              clearable
              placeholder="请输入年龄"
              style="width: 90%"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <date-range-picker v-model="query.createTimeRange" class="date-item" style="width: 90%" />
            </el-col>
          </template>
          <el-col :span="6">
            <queryOperation :crud="crud" />
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
      <crudOperation />
    </div>

    <!--表单组件-->
    <el-dialog
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.title"
      width="500px"
    >
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" style="width: 370px" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" style="width: 370px" />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="form.createTime" style="width: 370px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>

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
      <el-table-column prop="name" label="名字" :sortable="'custom'" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" width="150px" align="center">
        <template slot-scope="scope">
          <udOperation :data="scope.row" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import HelloApi from './HelloApi'
import CRUD, { crud, form, header, presenter } from '@crud/crud'
import queryOperation from '@crud/Query.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'

const defaultForm = { name: null, age: null, createTime: null }
export default {
  name: 'hello',
  components: { pagination, crudOperation, queryOperation, udOperation, DateRangePicker },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '测试',
      url: 'api/demo/hello',
      sort: 'id,asc',
      crudMethod: { ...HelloApi }
    })
  },
  data() {
    return {
      rules: {
        name: [{ required: true, trigger: 'blur', type: 'string' }],
        age: [{ required: true, trigger: 'blur', type: 'number' }],
        createTime: [{ required: true, trigger: 'blur', type: 'date' }]
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
