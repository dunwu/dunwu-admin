<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <!--左侧为部门树形列表-->
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size: 18px; font-weight: bold;">角色列表</span>
          </div>
          <RolePage ref="rolePage" @selectCurrent="selectCurrent" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <el-row :gutter="20">
              <el-col :span="20">
                <span style="font-size: 18px; font-weight: bold;">角色信息</span>
              </el-col>
              <el-col :span="4">
                <el-button
                  v-if="checkPer(['admin', 'cas:dept:edit', 'cas:dept:del'])"
                  type="primary"
                  size="mini"
                  @click="openRolePermForm"
                >
                  变更权限
                </el-button>
              </el-col>
            </el-row>
          </div>
          <!--右侧为部门详情页-->
          <div v-if="roleId">
            <RoleDetail :id="roleId" />
            <div style="margin-top: 20px;">
              <div style="display: flex; align-items: center;">
                <div style="font-size: 16px; font-weight: bold; display: block;">用户角色</div>
              </div>
              <!--              <JobPage ref="jobPage" :role-id="roleId" />-->
            </div>
            <div style="margin-top: 20px;">
              <div style="display: flex; align-items: center;">
                <div style="font-size: 16px; font-weight: bold; display: block;">用户权限</div>
              </div>
              <!--              <UserPage ref="userPage" :role-id="roleId" />-->
            </div>
          </div>
          <el-empty v-else description="请选择一个部门" />
        </el-card>
      </el-col>
    </el-row>

    <el-drawer
      v-if="role"
      :title="role.name + ' 角色权限'"
      :visible.sync="showRolePermForm"
      direction="rtl"
      size="50%"
      :before-close="closeRolePermForm"
    >
      <RolePermForm ref="rolePermForm" :role="role" />
    </el-drawer>
  </div>
</template>

<script>
import RolePage from './RolePage'
import RoleDetail from './RoleDetail'
import RolePermForm from './RolePermForm'

export default {
  name: 'Role',
  components: { RolePage, RoleDetail, RolePermForm },
  data() {
    return {
      role: null,
      roleId: null,
      showRolePermForm: false
    }
  },
  methods: {
    selectCurrent(val) {
      this.role = val
      this.roleId = val.id
    },
    /**
     * 打开部门职务绑定表单
     */
    openRolePermForm() {
      this.showRolePermForm = true
    },
    /**
     * 关闭部门职务绑定表单
     */
    closeRolePermForm() {
      this.showRolePermForm = false
      this.$refs['rolePage'].refreshTable()
    }
  }
}
</script>

<style lang="scss" scoped></style>
