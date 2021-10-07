<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--侧边部门数据-->
      <el-col :span="10">
        <el-card class="box-card">
          <span style="font-size: 16px; font-weight: bold;">部门列表</span>
          <div style="margin-top: 20px">
            <!--工具栏-->
            <div class="head-container">
              <div v-if="crud.props.searchToggle">
                <!-- 搜索 -->
                <el-input
                  v-model="query.name"
                  clearable
                  size="small"
                  placeholder="输入部门名称搜索"
                  style="width: 200px;"
                  class="filter-item"
                  @keyup.enter.native="crud.toQuery"
                />
                <el-select
                  v-model="query.disabled"
                  clearable
                  size="small"
                  placeholder="状态"
                  class="filter-item"
                  style="width: 90px"
                  @change="crud.toQuery"
                >
                  <el-option
                    v-for="item in disabledTypeOptions"
                    :key="item.key"
                    :label="item.display_name"
                    :value="item.key"
                  />
                </el-select>
                <TableQueryOperation />
              </div>
              <TableOperation :permission="permission" />
            </div>
            <!--表格渲染-->
            <el-table
              ref="table"
              v-loading="crud.loading"
              :data="crud.data"
              row-key="id"
              lazy
              border
              highlight-current-row
              :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
              :load="getDeptTreeList"
              @current-change="handleCurrentChange"
              @select="crud.selectChange"
              @select-all="crud.selectAllChange"
              @selection-change="crud.selectionChangeHandler"
            >
              <el-table-column type="selection" width="55" />
              <el-table-column label="ID" prop="id" />
              <el-table-column label="部门名称" prop="name" />
              <el-table-column label="状态" prop="disabled">
                <template slot-scope="scope">
                  <el-switch
                    v-model="scope.row.disabled"
                    :disabled="scope.row.id === 1"
                    active-color="#409EFF"
                    inactive-color="#F56C6C"
                    :active-value="false"
                    :inactive-value="true"
                    @change="changeDisabledStatus(scope.row, scope.row.disabled)"
                  />
                </template>
              </el-table-column>
            </el-table>
            <!--部门编辑表单-->
            <DeptForm />
            <!--部门用户绑定表单-->
            <DeptUserForm ref="deptUserForm" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card class="box-card">
          <div v-if="selectDept">
            <el-descriptions title="基本信息" :column="2" border>
              <template slot="extra">
                <el-button
                  v-if="checkPer(['admin', 'cas:dept:edit', 'cas:dept:del'])"
                  type="primary"
                  size="small"
                  style="display: block; margin-right: 0;"
                  @click="openDeptUserForm"
                >
                  变更成员
                </el-button>
              </template>
              <el-descriptions-item>
                <template slot="label">
                  ID
                </template>
                {{ selectDept.id }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  部门名称
                </template>
                {{ selectDept.name }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  部门等级
                </template>
                {{ selectDept.label }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  更新者
                </template>
                {{ selectDept.updateBy }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  更新时间
                </template>
                {{ selectDept.updateTime }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  备注
                </template>
                {{ selectDept.note }}
              </el-descriptions-item>
            </el-descriptions>
            <div style="margin-top: 20px;">
              <div style="display: flex; align-items: center;">
                <div style="font-size: 16px; font-weight: bold; display: block;">部门成员</div>
              </div>
              <UserList :dept-id="deptId" />
            </div>
          </div>
          <el-empty v-else description="请选择一个部门" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CRUD, { crud, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import DeptApi from '@/api/cas/dept'
import DeptForm from './DeptForm'
import DeptUserForm from './DeptUserForm'
import UserList from '@/views/cas/user/UserList'

export default {
  name: 'Dept',
  components: {
    TableOperation,
    TableQueryOperation,
    DeptForm,
    DeptUserForm,
    UserList
  },
  cruds() {
    return CRUD({
      title: '部门',
      url: 'cas/dept',
      tableType: 'tree',
      crudMethod: { ...DeptApi }
    })
  },
  mixins: [presenter(), header(), crud()],
  /**
   * 设置数据字典
   */
  dicts: ['dept_status'],
  data() {
    return {
      /**
       * 权限表达式
       */
      permission: {
        add: ['admin', 'cas:dept:add'],
        edit: ['admin', 'cas:dept:edit'],
        del: ['admin', 'cas:dept:del']
      },
      disabledTypeOptions: [{ key: 'true', display_name: '禁用' }, { key: 'false', display_name: '启用' }],
      selectDept: null,
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ]
    }
  },
  methods: {
    /**
     * 获取部门树形列表
     */
    getDeptTreeList(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        DeptApi.treeList(params).then(res => {
          resolve(res)
        })
      }, 100)
    },
    /**
     * 切换状态
     */
    changeDisabledStatus(data, val) {
      this.$confirm('此操作将 "' + this.dict.label.dept_status[val] + '" ' + data.name + '部门, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          DeptApi.edit(data)
            .then(res => {
              this.crud.notify(CRUD.NOTIFICATION_TYPE.SUCCESS, this.dict.label.dept_status[val] + '成功')
            })
            .catch(err => {
              data.disabled = !data.disabled
              console.log(err.response.data.msg)
            })
        })
        .catch(() => {
          data.disabled = !data.disabled
        })
    },
    openDeptUserForm() {
      this.$refs.deptUserForm.handleOpen()
    },
    // 选中字典后，设置字典详情数据
    handleCurrentChange(val) {
      if (val) {
        console.info('handleCurrentChange', val)
        this.selectDept = val
        this.deptId = val.id
      }
    }
  }
}
</script>

<style lang="scss" scoped></style>
