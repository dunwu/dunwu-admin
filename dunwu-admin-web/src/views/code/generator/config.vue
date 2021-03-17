<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="全局级别配置" name="globalConfig" lazy>
        <globalConfig v-if="globalConfigActived" />
      </el-tab-pane>
      <el-tab-pane label="表级别配置" name="tableConfig" lazy>
        <tableConfig v-if="tableConfigActived" />
      </el-tab-pane>
      <el-tab-pane label="字段级别配置" name="columnConfig" lazy>
        <columnConfig v-if="columnConfigActived" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import globalConfig from './globalConfig'
import tableConfig from './tableConfig'
import columnConfig from './columnConfig'

export default {
  name: 'GeneratorConfig',
  components: { globalConfig, tableConfig, columnConfig },
  data() {
    return {
      activeName: 'globalConfig',
      globalConfigActived: true,
      tableConfigActived: false,
      columnConfigActived: false
    }
  },
  created() {},
  methods: {
    handleClick(tab) {
      if (tab.name === 'globalConfig') {
        this.globalConfigActived = true
        this.tableConfigActived = false
        this.columnConfigActived = false
      } else if (tab.name === 'tableConfig') {
        this.globalConfigActived = false
        this.tableConfigActived = true
        this.columnConfigActived = false
      } else if (tab.name === 'columnConfig') {
        this.globalConfigActived = false
        this.tableConfigActived = false
        this.columnConfigActived = true
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.edit-input {
  .el-input__inner {
    border: 1px solid #e5e6e7;
  }
}
</style>

<style scoped>
::v-deep .input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>
