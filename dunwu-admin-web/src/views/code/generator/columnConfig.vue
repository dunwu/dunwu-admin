<template>
  <el-card class="box-card" shadow="never">
    <div slot="header" class="clearfix">
      <span class="role-span">
        <el-tag type="info">{{ tableName }}</el-tag>
        字段级别配置
      </span>
      <el-button
        icon="el-icon-download"
        size="mini"
        style="float: right; padding: 6px 9px; margin-left: 9px;"
        type="primary"
      >
        <router-link :to="'/tool/code/preview/' + dbId + '/' + schemaName + '/' + tableName">
          预览
        </router-link>
      </el-button>
      <el-button
        :loading="downloadLoading"
        icon="el-icon-download"
        size="mini"
        style="float: right; padding: 6px 9px;"
        type="primary"
        @click="toDownload"
      >
        下载
      </el-button>
      <el-button
        :loading="genLoading"
        icon="el-icon-s-promotion"
        size="mini"
        style="float: right; padding: 6px 9px;"
        type="primary"
        @click="toGenerate"
      >
        生成
      </el-button>
      <el-button
        :loading="configLoading"
        icon="el-icon-check"
        size="mini"
        style="float: right; padding: 6px 9px;"
        type="primary"
        @click="saveColumnConfig"
      >
        保存
      </el-button>
      <el-tooltip class="item" effect="dark" content="数据库中表字段变动时使用该功能" placement="top-start">
        <el-button
          :loading="loading"
          icon="el-icon-refresh"
          size="mini"
          style="float: right; padding: 6px 9px;"
          type="info"
          @click="syncTable"
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
        style="width: 100%; margin-bottom: 15px"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="generator-table-expand">
              <el-form-item label="字段名称：">
                <span>{{ props.row.fieldName }}</span>
              </el-form-item>
              <el-form-item label="字段注释：">
                <span>{{ props.row.comment }}</span>
              </el-form-item>
              <el-form-item label="字段数据类型：">
                <span>{{ props.row.type }}</span>
              </el-form-item>
              <el-form-item label="字段 Java 类型：">
                <span>{{ props.row.javaType }}</span>
              </el-form-item>
              <el-form-item label="Not Null：">
                <span>{{ props.row.notNull }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="propertyName" label="属性名" width="150px">
          <template slot-scope="scope">
            <el-input v-model="data[scope.$index].propertyName" size="mini" class="edit-input" />
          </template>
        </el-table-column>
        <el-table-column prop="labelName" label="Label名" width="150px">
          <template slot-scope="scope">
            <el-input v-model="data[scope.$index].labelName" size="mini" class="edit-input" />
          </template>
        </el-table-column>
        <el-table-column prop="type" label="数据类型" width="100px" />
        <el-table-column prop="keyType" label="KEY类型">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.keyType === 'PRI'" size="medium" type="success">主键</el-tag>
            <el-tag v-else-if="scope.row.keyType === 'MUL'" size="medium">键</el-tag>
            <el-tag v-else-if="scope.row.keyType === 'UNI'" size="medium">唯一键</el-tag>
            <span v-else>{{ scope.row.keyType }}</span>
          </template>
        </el-table-column>
        <el-table-column label="非空" width="70px">
          <template slot-scope="scope">
            <!--所有的键必须不为空-->
            <el-checkbox
              v-if="data[scope.$index].keyType === null || data[scope.$index].keyType === ''"
              v-model="data[scope.$index].notNull"
            />
            <el-checkbox v-else v-model="data[scope.$index].notNull" disabled />
          </template>
        </el-table-column>
        <el-table-column label="出现在列表" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableList" />
          </template>
        </el-table-column>
        <el-table-column label="出现在表单" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-if="data[scope.$index].keyType !== 'PRI'" v-model="data[scope.$index].enableForm" />
            <el-checkbox v-else v-model="data[scope.$index].enableForm" disabled />
          </template>
        </el-table-column>
        <el-table-column label="出现在查询" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableQuery" />
          </template>
        </el-table-column>
        <el-table-column label="允许排序" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableSort" />
          </template>
        </el-table-column>
        <el-table-column label="允许校验" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableValidate" />
          </template>
        </el-table-column>
        <el-table-column label="列表类型" width="150px">
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
        <el-table-column label="表单类型" width="150px">
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
              <el-option label="输入框" value="Input" />
              <el-option label="文本域输入框" value="Textarea" />
              <el-option label="计数器" value="InputNumber" />
              <el-option label="单选框" value="Radio" />
              <el-option label="选择器" value="Select" />
              <el-option label="日期时间选择器" value="DateTimePicker" />
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
        <el-table-column label="查询类型" width="150px">
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
              <el-option label="普通查询" value="EQUALS" />
              <el-option label="范围查询" value="BETWEEN" />
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
        <el-table-column label="排序类型" width="100px">
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
        <el-table-column label="校验类型" width="150px">
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
              <el-option label="boolean" value="boolean" />
              <el-option label="integer" value="integer" />
              <el-option label="float" value="float" />
              <el-option label="url" value="url" />
              <el-option label="email" value="email" />
              <el-option label="date" value="date" />
            </el-select>
            <el-select
              v-else
              v-model="data[scope.$index].validateType"
              filterable
              class="edit-input"
              clearable
              disabled
              size="mini"
              placeholder="请选择"
            />
          </template>
        </el-table-column>
        <el-table-column label="日期格式" width="150px">
          <template slot-scope="scope">
            <el-input
              v-if="data[scope.$index].type === 'datetime'"
              v-model="data[scope.$index].datePattern"
              size="mini"
              class="edit-input"
            />
            <el-input v-else v-model="data[scope.$index].datePattern" disabled size="mini" class="edit-input" />
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </el-card>
</template>

<script>
import codeApi from '@/api/code/codeApi'
import { downloadFile } from '@/utils'
export default {
  name: 'ColumnConfig',
  components: {},
  data() {
    return {
      dbId: null,
      schemaName: '',
      tableName: '',
      createBy: '',
      tableHeight: 550,
      data: [],
      dicts: [],
      loading: false,
      configLoading: false,
      genLoading: false,
      downloadLoading: false
    }
  },
  created() {
    this.tableHeight = document.documentElement.clientHeight - 385

    // 根据 router、store 获取页面必要属性
    this.dbId = this.$route.params.dbId
    this.tableName = this.$route.params.tableName
    this.schemaName = this.$route.params.schemaName
    console.info('this.$store.state.user', this.$store.state.user)
    if (this.$store.state.user) {
      if (this.$store.state.user.user) {
        this.createBy = this.$store.state.user.user.username
      }
    } else {
      this.createBy = 'admin'
    }

    this.$nextTick(() => {
      this.queryColumnConfig()
    })
  },
  methods: {
    queryColumnConfig() {
      this.loading = true
      codeApi
        .queryColumnConfig({
          dbId: this.dbId,
          schemaName: this.schemaName,
          tableName: this.tableName,
          createBy: this.createBy
        })
        .then(data => {
          this.loading = false
          this.data = data
        })
        .catch(err => {
          this.loading = false
          console.error('保存失败', err.response.data.msg)
        })
    },
    saveColumnConfig() {
      this.configLoading = true
      codeApi
        .saveColumnConfig({
          dbId: this.dbId,
          schemaName: this.schemaName,
          tableName: this.tableName,
          createBy: this.createBy,
          columns: this.data
        })
        .then(() => {
          this.queryColumnConfig()
          this.configLoading = false
          this.$notify({ title: '保存成功', type: 'success' })
        })
        .catch(err => {
          this.configLoading = false
          console.error(err.response.data.msg)
        })
    },
    syncTable() {
      this.loading = true
      codeApi
        .syncTable({
          dbId: this.dbId,
          schemaName: this.schemaName,
          tableName: this.tableName,
          createBy: this.createBy
        })
        .then(data => {
          this.loading = false
          this.data = data
        })
        .catch(err => {
          this.loading = false
          this.$notify({ title: err, type: 'error' })
        })
    },
    toGenerate(row) {
      // 生成代码
      this.genLoading = true
      codeApi.generateCode({ schemaName: this.schemaName, tableName: this.tableName }).then(data => {
        this.$notify({ title: '生成成功', type: 'success' })
        this.genLoading = false
      })
    },
    toDownload(row) {
      // 打包下载
      this.downloadLoading = true
      codeApi.downloadCode({ schemaName: this.schemaName, tableName: this.tableName }).then(data => {
        downloadFile(data, this.tableName, 'zip')
        this.$notify({ title: '下载成功', type: 'success' })
        this.downloadLoading = false
      })
    }
  }
}
</script>

<style scoped>
.generator-table-expand {
  font-size: 0;
}
.generator-table-expand label {
  width: 100px;
}
.generator-table-expand .el-form-item {
  color: #6a8bad;
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
</style>
