<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 搜索 -->
      <div v-if="crud.props.searchToggle && allowOperation">
        <el-row>
          <el-col :span="6">
            <el-input
              v-model="query.name"
              clearable
              size="small"
              placeholder="输入职务名搜索"
              class="filter-item"
              style="width: 90%"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
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
                :value="item.value"
              />
            </el-select>
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-select
                v-model="query.type"
                clearable
                size="small"
                placeholder="职务类型"
                class="filter-item"
                style="width: 90%"
                @keyup.enter.native="crud.toQuery"
              >
                <el-option
                  v-for="item in dict['job_type'].options"
                  :key="item.code"
                  :label="item.name"
                  :value="item.value"
                />
              </el-select>
            </el-col>
          </template>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-select
                v-model="query.level"
                clearable
                size="small"
                placeholder="职级"
                class="filter-item"
                style="width: 90%"
                @keyup.enter.native="crud.toQuery"
              >
                <el-option
                  v-for="item in dict['job_profession_level'].options"
                  :key="item.code"
                  :label="item.name"
                  :value="item.value"
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
      <TableOperation v-if="allowOperation" :permission="permission" />
    </div>

    <!--表格-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      border
      highlight-current-row
      @sort-change="crud.changeTableSort"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column v-if="allowOperation" type="selection" width="55" />
      <el-table-column prop="id" label="ID" :sortable="'custom'" />
      <el-table-column prop="name" label="职务名称" />
      <el-table-column prop="type" label="职务类型">
        <template slot-scope="scope">
          {{ dict.label.job_type[scope.row.type] }}
        </template>
      </el-table-column>
      <el-table-column prop="level" label="职级">
        <template slot-scope="scope">
          {{ dict.label.job_profession_level[scope.row.level] }}
        </template>
      </el-table-column>
      <el-table-column v-if="allowOperation" label="状态" prop="disabled">
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
      <el-table-column
        v-if="checkPer(['admin', 'cas:job:edit', 'cas:job:del']) && allowOperation"
        label="操作"
        width="150px"
        align="center"
        fixed="right"
      >
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />

    <!--表单渲染-->
    <Form :dict="dict" />
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import TableQueryOperation from '@crud/TableQueryOperation'
import Pagination from '@crud/Pagination'
import Form from './JobForm'
import JobApi from '@/api/cas/job'

export default {
  name: 'JobPage',
  components: { TableQueryOperation, TableOperation, TableColumnOperation, Pagination, Form },
  mixins: [presenter(), header(), crud()],
  cruds() {
    return CRUD({
      title: '职务',
      url: 'cas/job',
      sort: ['sequence,asc'],
      crudMethod: { ...JobApi }
    })
  },
  // 数据字典
  dicts: ['disabled_status', 'job_type', 'job_profession_level'],
  props: {
    deptId: {
      type: Number,
      required: false,
      default: () => {
        return null
      }
    }
  },
  data() {
    return {
      allowOperation: this.deptId == null,
      permission: {
        add: ['admin', 'cas:job:add'],
        del: ['admin', 'cas:job:del'],
        edit: ['admin', 'cas:job:edit'],
        view: ['admin', 'cas:job:view']
      }
    }
  },
  watch: {
    deptId(value) {
      this.deptId = value
      this.allowOperation = this.deptId == null
      this.crud.toQuery()
    }
  },
  methods: {
    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.crud.query.deptId = this.deptId
    },
    /**
     * 切换禁用状态
     */
    changeStatus(data, val) {
      this.$confirm(
        '此操作将 "' + this.dict.label.disabled_status[val] + '" ' + data.name + '岗位, 是否继续？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          JobApi.edit(data)
            .then(() => {
              this.crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, this.dict.label.disabled_status[val] + '成功')
            })
            .catch(err => {
              data.disabled = !data.disabled
              console.log(err.data.msg)
            })
        })
        .catch(() => {
          data.disabled = !data.disabled
        })
    },
    /**
     * 刷新表数据
     */
    refreshTable() {
      this.crud.toQuery()
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
