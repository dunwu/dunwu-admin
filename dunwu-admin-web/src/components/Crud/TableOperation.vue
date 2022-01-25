<!--
  - 表格公共操作按钮组合，包含 [ 添加、编辑、删除、导出 ] 按钮。
  -->
<template>
  <div class="crud-opts">
    <!--执行权限校验-->
    <span v-if="crud.enablePermission" class="crud-opts-left">
      <!--左侧插槽-->
      <slot name="left" />
      <el-button
        v-if="enableAdd"
        v-permission="permission.add"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="crud.toAdd"
      >
        添加
      </el-button>
      <el-button
        v-if="enableEdit"
        v-permission="permission.edit"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        :disabled="crud.selections.length !== 1"
        @click="crud.toEdit(crud.selections[0])"
      >
        编辑
      </el-button>
      <el-upload
        v-if="importUrl && enableImport"
        class="filter-item"
        accept=".xls,.xlsx"
        :action="importUrl"
        :headers="headers"
        :loading="crud.importLoading"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="onImportSuccess"
        :on-error="onImportError"
      >
        <el-button size="mini" type="primary" icon="el-icon-upload2">导入</el-button>
      </el-upload>
      <el-button
        v-if="crud.optShow.export === undefined || crud.optShow.export === null || crud.optShow.export"
        :loading="crud.exportLoading"
        :disabled="!crud.data.length"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-download"
        @click="crud.doExport"
      >
        导出
      </el-button>
      <el-button
        v-if="enableDel"
        slot="reference"
        v-permission="permission.del"
        class="filter-item"
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :loading="crud.batchDelLoading"
        :disabled="crud.selections.length === 0"
        @click="toDelete(crud.selections)"
      >
        删除
      </el-button>
      <!--右侧-->
      <slot name="right" />
    </span>
    <!--不执行权限校验-->
    <span v-else class="crud-opts-left">
      <!--左侧插槽-->
      <slot name="left" />
      <el-button
        v-if="enableAdd"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="crud.toAdd"
      >
        添加
      </el-button>
      <el-button
        v-if="enableEdit"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-edit"
        :disabled="crud.selections.length !== 1"
        @click="crud.toEdit(crud.selections[0])"
      >
        编辑
      </el-button>
      <el-upload
        v-if="importUrl && enableImport"
        class="filter-item"
        accept=".xls,.xlsx"
        :action="importUrl"
        :headers="headers"
        :loading="crud.importLoading"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="onImportSuccess"
        :on-error="onImportError"
      >
        <el-button size="mini" type="primary" icon="el-icon-upload2">导入</el-button>
      </el-upload>
      <el-button
        v-if="crud.optShow.export === undefined || crud.optShow.export === null || crud.optShow.export"
        :loading="crud.exportLoading"
        :disabled="!crud.data.length"
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-download"
        @click="crud.doExport"
      >
        导出
      </el-button>
      <el-button
        v-if="enableDel"
        slot="reference"
        class="filter-item"
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :loading="crud.batchDelLoading"
        :disabled="crud.selections.length === 0"
        @click="toDelete(crud.selections)"
      >
        删除
      </el-button>
      <!--右侧-->
      <slot name="right" />
    </span>
    <el-button-group
      v-if="crud.optShow.tools === undefined || crud.optShow.tools === null || crud.optShow.tools"
      class="crud-opts-right"
    >
      <el-button size="mini" plain type="info" icon="el-icon-search" @click="toggleSearch()" />
      <el-button size="mini" icon="el-icon-refresh" @click="crud.refresh()" />
      <el-popover placement="bottom-end" width="150" trigger="click">
        <el-button slot="reference" size="mini" icon="el-icon-s-grid">
          <i class="fa fa-caret-down" aria-hidden="true" />
        </el-button>
        <el-checkbox
          v-model="allColumnsSelected"
          :indeterminate="allColumnsSelectedIndeterminate"
          @change="handleCheckAllChange"
        >
          全选
        </el-checkbox>
        <el-checkbox
          v-for="item in tableColumns"
          :key="item.property"
          v-model="item.visible"
          @change="handleCheckedTableColumnsChange(item)"
        >
          {{ item.label }}
        </el-checkbox>
      </el-popover>
    </el-button-group>
  </div>
</template>

<script>
import CRUD, { crud } from '@crud/crud'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'

function sortWithRef(src, ref) {
  const result = Object.assign([], ref)
  let cursor = -1
  src.forEach(e => {
    const idx = result.indexOf(e)
    if (idx === -1) {
      cursor += 1
      result.splice(cursor, 0, e)
    } else {
      cursor = idx
    }
  })
  return result
}

export default {
  mixins: [crud()],
  props: {
    permission: {
      type: Object,
      default: () => {
        return {}
      }
    },
    hiddenColumns: {
      type: Array,
      default: () => {
        return []
      }
    },
    ignoreColumns: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      tableColumns: [],
      allColumnsSelected: true,
      allColumnsSelectedIndeterminate: false,
      tableUnwatcher: null,
      // 忽略下次表格列变动
      ignoreNextTableColumnsChange: false,
      headers: {
        DunwuToken: getToken()
      },
      // 导入地址
      importUrl: null,
      // 允许展示添加按钮
      enableAdd: false,
      // 允许展示编辑按钮
      enableEdit: false,
      // 允许展示删除按钮
      enableDel: false,
      // 允许展示导入按钮
      enableImport: false,
      // 允许展示导出按钮
      enableExport: false
    }
  },
  computed: {
    ...mapGetters(['baseApi'])
  },
  watch: {
    'crud.props.table'() {
      this.updateTableColumns()
      this.tableColumns.forEach(column => {
        if (this.hiddenColumns.indexOf(column.property) !== -1) {
          column.visible = false
          this.updateColumnVisible(column)
        }
      })
    },
    'crud.props.table.store.states.columns'() {
      this.updateTableColumns()
    }
  },
  created() {
    this.crud.updateProp('searchToggle', true)
    if (this.crud.importUrl) {
      this.importUrl = this.baseApi + '/' + this.crud.importUrl
    } else {
      console.error(this.crud.title + ' 页面没有配置 importUrl，不能使用导入功能')
    }

    this.enableAdd = this.getEnableStatus(this.crud.optShow.add)
    this.enableEdit = this.getEnableStatus(this.crud.optShow.edit)
    this.enableDel = this.getEnableStatus(this.crud.optShow.del)
    this.enableImport = this.getEnableStatus(this.crud.optShow.import)
    this.enableExport = this.getEnableStatus(this.crud.optShow.export)
  },
  methods: {
    updateTableColumns() {
      const table = this.crud.getTable()
      if (!table) {
        this.tableColumns = []
        return
      }
      let cols = null
      const columnFilter = e => e && e.type === 'default' && e.property && this.ignoreColumns.indexOf(e.property) === -1
      const refCols = table.columns.filter(columnFilter)
      if (this.ignoreNextTableColumnsChange) {
        this.ignoreNextTableColumnsChange = false
        return
      }
      this.ignoreNextTableColumnsChange = false
      const columns = []
      const fullTableColumns = table.$children.map(e => e.columnConfig).filter(columnFilter)
      cols = sortWithRef(fullTableColumns, refCols)
      cols.forEach(config => {
        const column = {
          property: config.property,
          label: config.label,
          visible: refCols.indexOf(config) !== -1
        }
        columns.push(column)
      })
      this.tableColumns = columns
    },
    toDelete(data) {
      this.$confirm(`确认删除选中的${data.length}条数据?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.crud.batchDelLoading = true
          this.crud.doDelete(data)
        })
        .catch(() => {})
    },
    handleCheckAllChange(val) {
      if (val === false) {
        this.allColumnsSelected = true
        return
      }
      this.tableColumns.forEach(column => {
        if (!column.visible) {
          column.visible = true
          this.updateColumnVisible(column)
        }
      })
      this.allColumnsSelected = val
      this.allColumnsSelectedIndeterminate = false
    },
    handleCheckedTableColumnsChange(item) {
      let totalCount = 0
      let selectedCount = 0
      this.tableColumns.forEach(column => {
        ++totalCount
        selectedCount += column.visible ? 1 : 0
      })
      if (selectedCount === 0) {
        this.crud.message(CRUD.NOTIFICATION_TYPE.WARNING, '请至少选择一列')
        this.$nextTick(function() {
          item.visible = true
        })
        return
      }
      this.allColumnsSelected = selectedCount === totalCount
      this.allColumnsSelectedIndeterminate = selectedCount !== totalCount && selectedCount !== 0
      this.updateColumnVisible(item)
    },
    updateColumnVisible(item) {
      const table = this.crud.props.table
      const vm = table.$children.find(e => e.prop === item.property)
      const columnConfig = vm.columnConfig
      if (item.visible) {
        // 找出合适的插入点
        const columnIndex = this.tableColumns.indexOf(item)
        vm.owner.store.commit('insertColumn', columnConfig, columnIndex + 1, null)
      } else {
        vm.owner.store.commit('removeColumn', columnConfig, null)
      }
      this.ignoreNextTableColumnsChange = true
    },
    toggleSearch() {
      this.crud.props.searchToggle = !this.crud.props.searchToggle
    },
    beforeUpload(file) {
      console.info('beforeUpload', file)
      const isLt1M = file.size / 1024 / 1024 < 1
      if (isLt1M) {
        return true
      }

      this.$message({
        message: '请不要上传大于1m的文件.',
        type: 'warning'
      })
      return false
    },
    onImportSuccess(response, file, fileList) {
      if (response && response.code === 0) {
        this.$message({ type: 'success', message: '导入成功' })
        this.crud.toQuery()
      } else {
        console.error('导入失败', response)
        this.$message({ type: 'error', message: '导入失败' })
      }
    },
    onImportError(err, file, fileList) {
      console.error('导入失败', err)
      this.$message({ type: 'error', message: '导入失败' })
    },
    getEnableStatus(val) {
      return this.crud.optShow.all === true || val === undefined || val === null || val
    }
  }
}
</script>

<style>
.crud-opts {
  padding: 4px 0;
  display: -webkit-flex;
  display: flex;
  align-items: center;
}

.crud-opts .crud-opts-right {
  margin-left: auto;
}
</style>
