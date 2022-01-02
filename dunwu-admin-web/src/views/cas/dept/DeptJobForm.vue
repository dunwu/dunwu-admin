<template>
  <div style="margin: 20px">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>添加职务</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-select
            v-model="ids"
            multiple
            filterable
            remote
            reserve-keyword
            size="mini"
            style="width: 90%"
            class="filter-item"
            placeholder="输入职务ID或职务名搜索"
            :remote-method="getExistsList"
            :loading="listOptionsLoading"
          >
            <el-option v-for="item in list" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-button :loading="addLoading" type="primary" plain size="mini" @click="bindJobToDept">
            添加职务
          </el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card shadow="never" style="margin-top: 30px">
      <div slot="header" class="clearfix">
        <span>已有职务</span>
      </div>
      <!--工具栏-->
      <div class="head-container">
        <div v-if="crud.props.searchToggle">
          <el-row>
            <el-col :span="8">
              <el-input
                v-model="query.name"
                clearable
                size="mini"
                style="width: 90%"
                class="filter-item"
                placeholder="输入职务名搜索"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
            <template v-if="crud.showExtendSearch">
              <el-col :span="8">
                <el-select
                  v-model="query.disabled"
                  clearable
                  size="mini"
                  style="width: 90%"
                  class="filter-item"
                  placeholder="状态"
                  @keyup.enter.native="crud.toQuery"
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
            <template v-if="crud.showExtendSearch">
              <el-col :span="8">
                <el-select
                  v-model="query.type"
                  clearable
                  size="mini"
                  style="width: 90%"
                  class="filter-item"
                  placeholder="职务类型"
                  @keyup.enter.native="crud.toQuery"
                >
                  <el-option
                    v-for="item in dict['job_type'].options"
                    :key="item.code"
                    :label="item.name"
                    :value="item.code"
                  />
                </el-select>
              </el-col>
            </template>
            <template v-if="crud.showExtendSearch">
              <el-col :span="8">
                <el-select
                  v-model="query.level"
                  clearable
                  size="mini"
                  style="width: 90%"
                  class="filter-item"
                  placeholder="职级"
                  @keyup.enter.native="crud.toQuery"
                >
                  <el-option
                    v-for="item in dict['job_profession_level'].options"
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
        <TableOperation>
          <el-button
            slot="right"
            type="danger"
            icon="el-icon-delete"
            size="mini"
            class="filter-item"
            :loading="crud.batchDelLoading"
            :disabled="crud.selections.length === 0"
            @click="unbindJobToDept(crud.selections)"
          >
            删除
          </el-button>
        </TableOperation>
      </div>
      <!--表格渲染-->
      <el-table
        ref="table"
        v-loading="crud.loading"
        :data="crud.data"
        border
        @sort-change="crud.changeTableSort"
        @selection-change="crud.selectionChangeHandler"
      >
        <el-table-column type="selection" width="55" />
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
      </el-table>
      <!--分页组件-->
      <Pagination />
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import JobApi from '@/api/cas/job'

export default {
  name: 'DeptJobForm',
  components: { TableOperation, TableQueryOperation, Pagination },
  cruds() {
    return CRUD({
      title: '职务',
      url: 'cas/job',
      sort: ['id,asc'],
      crudMethod: { ...JobApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  // 数据字典
  dicts: ['disabled_status', 'job_type', 'job_profession_level'],
  props: {
    dept: {
      type: Object,
      required: false,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      listOptionsLoading: false,
      addLoading: false,
      list: [],
      ids: []
    }
  },
  computed: {
    ...mapGetters(['user'])
  },
  mounted() {
    this.crud.optShow.add = false
    this.crud.optShow.edit = false
    this.crud.optShow.del = false
    this.crud.optShow.export = false
    this.ids = []
    this.getExistsList()
  },
  methods: {
    [CRUD.HOOK.beforeRefresh](crud, form) {
      this.crud.query.deptId = this.dept.id
    },
    /**
     * 根据查询条件搜索可以添加的选项
     */
    getExistsList(val) {
      let params = {}
      if (val) {
        params = { blurry: val }
      }
      JobApi.list(params).then(data => {
        this.list = data
      })
    },
    /**
     * 添加部门职务
     */
    bindJobToDept() {
      this.addLoading = true
      JobApi.bindDept(this.dept.id, this.ids)
        .then(data => {
          this.addLoading = false
          if (data) {
            this.$message({ message: '添加职务成功', type: 'success' })
          }
          this.crud.toQuery()
          this.clear()
        })
        .catch(() => {
          this.addLoading = false
        })
    },
    /**
     * 删除部门职务
     */
    unbindJobToDept(val) {
      this.$confirm(`确认删除选中的${val.length}条数据?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.crud.batchDelLoading = true
          const ids = val.map((item, index, arr) => {
            return item.id
          })
          JobApi.unbindDept(this.dept.id, ids)
            .then(data => {
              this.crud.batchDelLoading = false
              if (data) {
                this.$message({ message: '删除成功', type: 'success' })
              }
              this.crud.toQuery()
            })
            .catch(() => {
              this.crud.batchDelLoading = false
            })
        })
        .catch(() => {})
    },
    clear() {
      this.ids = []
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped></style>
