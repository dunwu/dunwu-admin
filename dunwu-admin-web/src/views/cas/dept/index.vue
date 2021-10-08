<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="10">
        <!--左侧为部门树形列表-->
        <el-card class="box-card">
          <DeptTreeList @selectDept="selectDept" />
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card class="box-card">
          <!--右侧为部门详情页-->
          <DeptDetail :dept="dept" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import DeptTreeList from './DeptTreeList'
import DeptDetail from './DeptDetail'

export default {
  name: 'Dept',
  components: { DeptTreeList, DeptDetail },
  data() {
    return {
      dept: null
    }
  },
  methods: {
    selectDept(val) {
      this.dept = val
    }
  }
}
</script>

<style lang="scss" scoped></style>
