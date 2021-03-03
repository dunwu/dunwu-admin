<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="全局级别配置" name="globalConfig">
        <globalConfig />
      </el-tab-pane>
      <el-tab-pane label="表级别配置" name="tableConfig">
        <tableConfig />
      </el-tab-pane>
      <el-tab-pane label="字段级别配置" name="columnConfig">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="role-span">{{ tableName }} 字段级别配置</span>
            <el-button
              :loading="genLoading"
              icon="el-icon-s-promotion"
              size="mini"
              style="float: right; padding: 6px 9px;"
              type="success"
              @click="toGen"
            >
              保存&生成
            </el-button>
            <el-button
              :loading="columnLoading"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px;margin-right: 9px"
              type="primary"
              @click="saveColumnConfig"
            >
              保存
            </el-button>
            <el-tooltip class="item" effect="dark" content="数据库中表字段变动时使用该功能" placement="top-start">
              <el-button
                :loading="syncLoading"
                icon="el-icon-refresh"
                size="mini"
                style="float: right; padding: 6px 9px;"
                type="info"
                @click="sync"
              >
                同步
              </el-button>
            </el-tooltip>
          </div>
          <el-form size="small" label-width="90px">
            <el-table
              v-loading="loading"
              :data="data"
              :max-height="tableHeight"
              stripe
              size="small"
              style="width: 100%;margin-bottom: 15px"
            >
              <el-table-column prop="name" label="字段名称" width="150px" />
              <el-table-column prop="comment" label="字段注释" width="150px" />
              <el-table-column prop="type" label="字段数据类型" width="100px" />
              <el-table-column prop="javaType" label="字段 Java 类型" width="100px" />
              <el-table-column prop="keyType" label="字段KEY类型" />
              <el-table-column align="enabled" label="启用" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].enabled" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="非空" width="70px">
                <template slot-scope="scope">
                  <!--所有的键必须不为空-->
                  <el-checkbox
                    v-if="data[scope.$index].keyType === null || data[scope.$index].keyType === ''"
                    v-model="data[scope.$index].notNull"
                  />
                  <el-checkbox v-else v-model="data[scope.$index].notNull" disabled />
                </template>
              </el-table-column>
              <el-table-column align="center" label="出现在列表" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].enableList" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="出现在表单" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-if="data[scope.$index].keyType !== 'PRI'" v-model="data[scope.$index].enableForm" />
                  <el-checkbox v-else v-model="data[scope.$index].enableForm" disabled />
                </template>
              </el-table-column>
              <el-table-column align="center" label="出现在查询" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].enableQuery" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="允许排序" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].enableSort" />
                </template>
              </el-table-column>
              <el-table-column align="center" label="允许校验" width="70px">
                <template slot-scope="scope">
                  <el-checkbox v-model="data[scope.$index].enableValidate" />
                </template>
              </el-table-column>
              <el-table-column label="列表类型">
                <template slot-scope="scope">
                  <el-select
                    v-if="data[scope.$index].enableList"
                    v-model="data[scope.$index].listType"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                  >
                    <el-option label="文本" value="Text" />
                    <el-option label="图片" value="Image" />
                    <el-option label="日期" value="Date" />
                  </el-select>
                  <el-select
                    v-else
                    v-model="data[scope.$index].listType"
                    filterable
                    class="edit-input"
                    clearable
                    disabled
                    size="mini"
                    placeholder="请选择"
                  />
                </template>
              </el-table-column>
              <el-table-column label="表单类型">
                <template slot-scope="scope">
                  <el-select
                    v-if="data[scope.$index].enableForm"
                    v-model="data[scope.$index].formType"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                  >
                    <el-option label="文本框" value="Input" />
                    <el-option label="文本域" value="Textarea" />
                    <el-option label="单选框" value="Radio" />
                    <el-option label="下拉框" value="Select" />
                    <el-option label="日期框" value="Date" />
                  </el-select>
                  <el-select
                    v-else
                    v-model="data[scope.$index].formType"
                    filterable
                    class="edit-input"
                    disabled
                    clearable
                    size="mini"
                    placeholder="请选择"
                  />
                </template>
              </el-table-column>
              <el-table-column label="查询类型">
                <template slot-scope="scope">
                  <el-select
                    v-if="data[scope.$index].enableQuery"
                    v-model="data[scope.$index].queryType"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                  >
                    <el-option label="等于" value="=" />
                    <el-option label="不等于" value="!=" />
                    <el-option label="大于等于" value=">=" />
                    <el-option label="小于等于" value="<=" />
                    <el-option label="模糊匹配" value="Like" />
                    <el-option label="非空" value="NotNull" />
                    <el-option label="范围查询" value="BetWeen" />
                  </el-select>
                  <el-select
                    v-else
                    v-model="data[scope.$index].queryType"
                    filterable
                    class="edit-input"
                    clearable
                    disabled
                    size="mini"
                    placeholder="请选择"
                  />
                </template>
              </el-table-column>
              <el-table-column label="排序类型">
                <template slot-scope="scope">
                  <el-select
                    v-if="data[scope.$index].enableSort"
                    v-model="data[scope.$index].sortType"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                  >
                    <el-option label="升序" value="asc" />
                    <el-option label="降序" value="desc" />
                  </el-select>
                  <el-select
                    v-else
                    v-model="data[scope.$index].sortType"
                    filterable
                    class="edit-input"
                    clearable
                    disabled
                    size="mini"
                    placeholder="请选择"
                  />
                </template>
              </el-table-column>
              <el-table-column label="校验类型">
                <template slot-scope="scope">
                  <el-select
                    v-if="data[scope.$index].enableValidate"
                    v-model="data[scope.$index].validateType"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                  >
                    <el-option label="string" value="string" />
                    <el-option label="number" value="number" />
                  </el-select>
                  <el-select
                    v-else
                    v-model="data[scope.$index].sortType"
                    filterable
                    class="edit-input"
                    clearable
                    disabled
                    size="mini"
                    placeholder="请选择"
                  />
                </template>
              </el-table-column>
              <el-table-column label="关联字典">
                <template slot-scope="scope">
                  <el-select
                    v-model="data[scope.$index].dictName"
                    filterable
                    class="edit-input"
                    clearable
                    size="mini"
                    placeholder="请选择"
                  >
                    <el-option
                      v-for="item in dicts"
                      :key="item.id"
                      :label="item.note === '' ? item.name : item.note"
                      :value="item.name"
                    />
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="字段展示名称" width="150px">
                <template slot-scope="scope">
                  <el-input v-model="data[scope.$index].propertyName" size="mini" class="edit-input" />
                </template>
              </el-table-column>
              <el-table-column label="日期表达式" width="150px">
                <template slot-scope="scope">
                  <el-input
                    v-if="data[scope.$index].type === 'datetime'"
                    v-model="data[scope.$index].dateExpression"
                    size="mini"
                    class="edit-input"
                  />
                  <el-input
                    v-else
                    v-model="data[scope.$index].dateExpression"
                    disabled
                    size="mini"
                    class="edit-input"
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import dictApi from '@/api/system/dict'
import globalConfig from './globalConfig'
import tableConfig from './tableConfig'
import tableConfigApi from '@/api/generator/tableConfigApi'
import generatorApi from '@/api/generator/generatorApi'
export default {
  name: 'GeneratorConfig',
  components: { globalConfig, tableConfig },
  mixins: [crud],
  data() {
    return {
      activeName: 'globalConfig',
      tableId: null,
      schemaName: '',
      tableName: '',
      tableHeight: 550,
      columnLoading: false,
      configLoading: false,
      dicts: [],
      syncLoading: false,
      genLoading: false,
      globalForm: {
        id: null,
        tableName: '',
        author: '',
        pack: '',
        path: '',
        moduleName: '',
        cover: 'false',
        apiPath: '',
        prefix: '',
        apiAlias: null
      },
      form: {
        id: null,
        tableName: '',
        author: '',
        pack: '',
        path: '',
        moduleName: '',
        cover: 'false',
        apiPath: '',
        prefix: '',
        apiAlias: null
      },
      rules: {
        author: [{ required: true, message: '作者不能为空', trigger: 'blur' }],
        pack: [{ required: true, message: '包路径不能为空', trigger: 'blur' }],
        moduleName: [{ required: true, message: '包路径不能为空', trigger: 'blur' }],
        path: [{ required: true, message: '前端路径不能为空', trigger: 'blur' }],
        apiAlias: [{ required: true, message: '接口名称不能为空', trigger: 'blur' }],
        cover: [{ required: true, message: '不能为空', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.tableHeight = document.documentElement.clientHeight - 385
    this.tableName = this.$route.params.tableName
    this.schemaName = this.$route.params.schemaName
    this.$nextTick(() => {
      this.init()
      tableConfigApi.find({ schemaName: this.schemaName, tableName: this.tableName }).then(data => {
        this.form = data
        // this.form.cover = this.form.cover.toString()
      })
      dictApi.list().then(data => {
        this.dicts = data
      })
    })
  },
  methods: {
    beforeInit() {
      this.url = 'api/generator/column'
      this.tableType = 'list'
      const schemaName = this.schemaName
      const tableName = this.tableName
      this.params = { schemaName, tableName }
      return true
    },
    saveColumnConfig() {
      this.columnLoading = true
      generatorApi
        .saveBatch({ schemaName: this.schemaName, tableName: this.tableName, columns: this.data })
        .then(res => {
          this.$notify('保存成功', 'success')
          this.columnLoading = false
        })
        .catch(err => {
          this.columnLoading = false
          console.log(err.response.data.message)
        })
    },
    saveTableConfig() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.configLoading = true
          if (this.form.id) {
            tableConfigApi
              .edit(this.form)
              .then(res => {
                this.$notify('保存成功', 'success')
                // this.form = res
                // this.form.cover = this.form.cover.toString()
                this.configLoading = false
              })
              .catch(err => {
                this.configLoading = false
                console.log(err.response.data.message)
              })
          } else {
            tableConfigApi
              .add(this.form)
              .then(res => {
                this.$notify('保存成功', 'success')
                // this.form = res
                // this.form.cover = this.form.cover.toString()
                this.configLoading = false
              })
              .catch(err => {
                this.configLoading = false
                console.log(err.response.data.message)
              })
          }
        }
      })
    },
    sync() {
      this.syncLoading = true
      generatorApi
        .sync([this.tableName])
        .then(() => {
          this.init()
          this.$notify('同步成功', 'success')
          this.syncLoading = false
        })
        .then(() => {
          this.syncLoading = false
        })
    },
    toGen() {
      this.genLoading = true
      generatorApi
        .save(this.data)
        .then(res => {
          this.$notify('保存成功', 'success')
          // 生成代码
          generatorApi
            .generator(this.schemaName, this.tableName, 0)
            .then(data => {
              this.genLoading = false
              this.$notify('生成成功', 'success')
            })
            .catch(err => {
              this.genLoading = false
              console.log(err.response.data.message)
            })
        })
        .catch(err => {
          this.genLoading = false
          console.log(err.response.data.message)
        })
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
