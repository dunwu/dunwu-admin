<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="border-card" :stretch="true" @tab-click="handleClick">
      <el-tab-pane label="全局级别配置" name="globalConfig" lazy>
        <span v-if="isGlobalConfigured" slot="label" style="color: #67c23a">
          <i class="el-icon-circle-check" />
          全局级别配置
          <el-tag size="mini" type="success">已配置</el-tag>
        </span>
        <span v-else slot="label">
          全局级别配置
        </span>
        <globalConfig v-if="globalConfigActivated" :info="info" />
      </el-tab-pane>
      <el-tab-pane label="表级别配置" name="tableConfig" lazy>
        <span v-if="isTableConfigured" slot="label" style="color: #67c23a">
          <i class="el-icon-circle-check" />
          表级别配置
          <el-tag size="mini" type="success">已配置</el-tag>
        </span>
        <span v-else slot="label">
          表级别配置
        </span>
        <tableConfig v-if="tableConfigActivated" :info="info" @getCodeConfigInfo="getCodeConfigInfo" />
      </el-tab-pane>
      <el-tab-pane label="字段级别配置" name="columnConfig" lazy>
        <span v-if="isColumnConfigured" slot="label" style="color: #67c23a">
          <i class="el-icon-circle-check" />
          字段级别配置
          <el-tag size="mini" type="success">已配置</el-tag>
        </span>
        <span v-else slot="label">
          字段级别配置
        </span>
        <columnConfig v-if="columnConfigActivated" :info="info" @getCodeConfigInfo="getCodeConfigInfo" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import globalConfig from './globalConfig'
import tableConfig from './tableConfig'
import columnConfig from './columnConfig'
import codeApi from '@/api/code/codeApi'

export default {
  name: 'GeneratorConfig',
  components: { globalConfig, tableConfig, columnConfig },
  data() {
    return {
      activeName: 'globalConfig',
      info: {
        dbId: null,
        schemaName: null,
        tableName: null
      },
      globalConfigActivated: true,
      tableConfigActivated: false,
      columnConfigActivated: false,
      isGlobalConfigured: false,
      isTableConfigured: false,
      isColumnConfigured: false
    }
  },
  created() {
    // 根据 router、store 获取页面必要属性
    this.info.dbId = this.$route.params.dbId
    this.info.schemaName = this.$route.params.schemaName
    this.info.tableName = this.$route.params.tableName

    this.getCodeConfigInfo()
  },
  methods: {
    handleClick(tab) {
      if (tab.name === 'globalConfig') {
        this.activeGlobalConfig()
      } else if (tab.name === 'tableConfig') {
        this.activeTableConfig()
      } else if (tab.name === 'columnConfig') {
        this.activeColumnConfig()
      }
    },
    getCodeConfigInfo() {
      codeApi.getCodeConfigInfo(this.info).then(res => {
        this.isGlobalConfigured = res.isGlobalConfigured
        this.isTableConfigured = res.isTableConfigured
        this.isColumnConfigured = res.isColumnConfigured

        if (this.isColumnConfigured && this.isTableConfigured) {
          this.activeName = 'columnConfig'
          this.activeColumnConfig()
        } else if (this.isTableConfigured) {
          this.activeName = 'tableConfig'
          this.activeTableConfig()
        } else {
          this.activeName = 'globalConfig'
          this.activeGlobalConfig()
        }
      })
    },
    activeGlobalConfig() {
      this.globalConfigActivated = true
      this.tableConfigActivated = false
      this.columnConfigActivated = false
    },
    activeTableConfig() {
      this.globalConfigActivated = false
      this.tableConfigActivated = true
      this.columnConfigActivated = false
    },
    activeColumnConfig() {
      this.globalConfigActivated = false
      this.tableConfigActivated = false
      this.columnConfigActivated = true
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
