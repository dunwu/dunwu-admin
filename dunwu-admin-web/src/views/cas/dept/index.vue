<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="10">
        <!--左侧为部门树形列表-->
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size: 18px; font-weight: bold;">部门列表</span>
          </div>
          <DeptTreeList @selectDept="selectDept" />
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <el-row :gutter="20">
              <el-col :span="18">
                <span style="font-size: 18px; font-weight: bold;">部门信息</span>
              </el-col>
              <el-col :span="3">
                <el-button
                  v-if="checkPer(['admin', 'cas:dept:edit', 'cas:dept:del'])"
                  type="primary"
                  size="mini"
                  @click="openDeptJobForm"
                >
                  变更职务
                </el-button>
              </el-col>
              <el-col :span="3">
                <el-button
                  v-if="checkPer(['admin', 'cas:dept:edit', 'cas:dept:del'])"
                  type="primary"
                  size="mini"
                  @click="openDeptUserForm"
                >
                  变更成员
                </el-button>
              </el-col>
            </el-row>
          </div>
          <!--右侧为部门详情页-->
          <div v-if="deptId">
            <DeptDetail :dept-id="deptId" />
            <div style="margin-top: 20px;">
              <div style="display: flex; align-items: center;">
                <div style="font-size: 16px; font-weight: bold; display: block;">部门职务</div>
              </div>
              <JobPage ref="jobPage" :dept-id="deptId" />
            </div>
            <div style="margin-top: 20px;">
              <div style="display: flex; align-items: center;">
                <div style="font-size: 16px; font-weight: bold; display: block;">部门成员</div>
              </div>
              <UserPage ref="userPage" :dept-id="deptId" />
            </div>
          </div>
          <el-empty v-else description="请选择一个部门" />
        </el-card>
      </el-col>
    </el-row>

    <!--部门职务绑定表单-->
    <el-drawer
      v-if="dept"
      :title="dept.name + ' 部门职务'"
      :visible.sync="showDeptJobForm"
      direction="rtl"
      size="50%"
      :before-close="closeDeptJobForm"
    >
      <DeptJobForm ref="deptJobForm" :dept="dept" />
    </el-drawer>
    <!--部门用户绑定表单-->
    <el-drawer
      v-if="dept"
      :title="dept.name + ' 部门成员'"
      :visible.sync="showDeptUserForm"
      direction="rtl"
      size="50%"
      :before-close="closeDeptUserForm"
    >
      <DeptUserForm ref="deptUserForm" :dept="dept" />
    </el-drawer>
  </div>
</template>

<script>
import DeptTreeList from './DeptTreeList'
import DeptDetail from './DeptDetail'
import DeptUserForm from './DeptUserForm'
import DeptJobForm from './DeptJobForm'
import JobPage from '@/views/cas/job/JobPage'
import UserPage from '@/views/cas/user/UserPage'

export default {
  name: 'Dept',
  components: { DeptTreeList, DeptDetail, DeptJobForm, DeptUserForm, JobPage, UserPage },
  data() {
    return {
      dept: null,
      deptId: null,
      showDeptJobForm: false,
      showDeptUserForm: false
    }
  },
  methods: {
    selectDept(val) {
      this.dept = val
      this.deptId = val.id
    },
    /**
     * 打开部门职务绑定表单
     */
    openDeptJobForm() {
      this.showDeptJobForm = true
    },
    /**
     * 关闭部门职务绑定表单
     */
    closeDeptJobForm() {
      this.showDeptJobForm = false
      this.$refs['deptJobForm'].clear()
      this.$refs['jobPage'].refreshTable()
    },
    /**
     * 打开部门用户绑定表单
     */
    openDeptUserForm() {
      this.showDeptUserForm = true
    },
    /**
     * 关闭部门用户绑定表单
     */
    closeDeptUserForm() {
      this.showDeptUserForm = false
      this.$refs['deptUserForm'].clear()
      this.$refs['userPage'].refreshTable()
    }
  }
}
</script>

<style lang="scss" scoped></style>
