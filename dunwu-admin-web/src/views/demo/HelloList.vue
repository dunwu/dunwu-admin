<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <label class="el-form-item-label">ID</label>
        <el-input v-model="query.id" clearable placeholder="ID" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">名字</label>
        <el-input v-model="query.name" clearable placeholder="名字" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <label class="el-form-item-label">年龄</label>
        <el-input v-model="query.age" clearable placeholder="年龄" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <queryOperation :crud="crud" />
      </div>
      <crudOperation :permission="permission" />
    </div>

    <!--表单组件-->
    <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" style="width: 370px;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>

      <!--表格渲染-->
    <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
      <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" />
        <el-table-column prop="name" label="名字" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column v-if="checkPer(['admin','demo:hello:edit','demo:hello:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation :data="scope.row" :permission="permission" />
          </template>
        </el-table-column>
      </el-table>

    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import HelloApi from './HelloApi'
import CRUD, {crud, form, header, presenter} from '@crud/crud'
import queryOperation from '@crud/Query.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = {id: null, name: null, age: null, }
export default {
  name: 'hello',
  components: {pagination, crudOperation, queryOperation, udOperation},
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '',
      url: 'api/demo/hello',
      sort: 'id,asc',
      crudMethod: { ...HelloApi }
    })
  },
  data() {
    return {
      permission: {
        add: ['admin', 'demo:hello:add'],
        edit: ['admin', 'demo:hello:edit'],
        del: ['admin', 'demo:hello:del']
      }, rules: {
        name: [
          { required: true, message: '名字不能为空', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '年龄必须为数字', trigger: 'blur', type: 'number' }
        ],
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
