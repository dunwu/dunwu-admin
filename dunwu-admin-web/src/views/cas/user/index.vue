<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12">
        <!--左侧为部门树形列表-->
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size: 18px; font-weight: bold;">用户列表</span>
          </div>
          <UserPage ref="userPage" @selectCurrent="selectCurrent" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span style="font-size: 18px; font-weight: bold;">用户信息</span>
          </div>
          <!--右侧为部门详情页-->
          <div v-if="userId">
            <UserDetail :id="userId" />
            <div style="margin-top: 20px;">
              <div style="display: flex; align-items: center;">
                <div style="font-size: 16px; font-weight: bold; display: block;">用户角色信息</div>
              </div>
              <RolePage ref="rolePage" :user-id="userId" />
            </div>
          </div>
          <el-empty v-else description="请选择一个用户" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import UserPage from './UserPage'
import UserDetail from './UserDetail'
import RolePage from '@/views/cas/role/RolePage'

export default {
  name: 'User',
  components: { UserPage, RolePage, UserDetail },
  data() {
    return {
      user: null,
      userId: null
    }
  },
  mounted: function() {},
  methods: {
    selectCurrent(val) {
      this.user = val
      this.userId = val.id
    }
  }
}
</script>

<style lang="scss" scoped></style>
