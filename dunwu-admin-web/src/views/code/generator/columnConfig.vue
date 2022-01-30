<template>
  <el-card class="box-card" shadow="never">
    <div slot="header" class="clearfix">
      <span class="role-span">
        <el-tag type="info">{{ info.tableName }}</el-tag>
        字段级别配置
      </span>
      <el-button
        icon="el-icon-download"
        size="mini"
        style="float: right; padding: 6px 9px; margin-left: 9px;"
        type="primary"
      >
        <router-link
          :to="{
            name: 'code/generator/preview',
            params: {
              ...info
            }
          }"
        >
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
          @click="syncQueryColumnConfig"
        >
          同步
        </el-button>
      </el-tooltip>
    </div>
    <el-form size="small" label-width="90px">
      <el-table v-loading="loading" :data="data" border stripe style="width: 100%;">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-descriptions title="配置信息" :column="4" size="mini" style="margin: 20px" border>
              <el-descriptions-item label="属性名">{{ props.row.propertyName }}</el-descriptions-item>
              <el-descriptions-item label="Label名">{{ props.row.labelName }}</el-descriptions-item>
              <el-descriptions-item label="数据类型">{{ props.row.type }}</el-descriptions-item>
              <el-descriptions-item label="KEY类型">{{ props.row.keyType }}</el-descriptions-item>
              <el-descriptions-item label="是否为空">{{ props.row.notNull }}</el-descriptions-item>
              <el-descriptions-item label="出现在列表">{{ props.row.enableList }}</el-descriptions-item>
              <el-descriptions-item label="出现在表单">{{ props.row.enableForm }}</el-descriptions-item>
              <el-descriptions-item label="出现在查询">{{ props.row.enableQuery }}</el-descriptions-item>
              <el-descriptions-item label="允许排序">{{ props.row.enableSort }}</el-descriptions-item>
              <el-descriptions-item label="允许校验">{{ props.row.enableValidate }}</el-descriptions-item>
              <el-descriptions-item label="列表类型">{{ props.row.listType }}</el-descriptions-item>
              <el-descriptions-item label="表单类型">{{ props.row.formType }}</el-descriptions-item>
              <el-descriptions-item label="查询类型">{{ props.row.queryType }}</el-descriptions-item>
              <el-descriptions-item label="排序类型">{{ props.row.sortType }}</el-descriptions-item>
              <el-descriptions-item label="校验类型">{{ props.row.validateType }}</el-descriptions-item>
              <el-descriptions-item label="日期格式">{{ props.row.datePattern }}</el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column prop="propertyName" label="属性名" :show-overflow-tooltip="true" width="150px">
          <template slot-scope="scope">
            <el-tooltip :content="data[scope.$index].propertyName" placement="bottom">
              <el-input v-model="data[scope.$index].propertyName" size="mini" class="edit-input" disabled />
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="labelName" label="Label名" :show-overflow-tooltip="true" width="150px">
          <template slot-scope="scope">
            <el-tooltip :content="data[scope.$index].labelName" placement="bottom">
              <el-input v-model="data[scope.$index].labelName" size="mini" class="edit-input" />
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="数据类型" :show-overflow-tooltip="true" width="100px" />
        <el-table-column prop="keyType" align="center" label="KEY类型">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.keyType === 'PRI'" size="medium" type="success">主键</el-tag>
            <el-tag v-else-if="scope.row.keyType === 'MUL'" size="medium">键</el-tag>
            <el-tag v-else-if="scope.row.keyType === 'UNI'" size="medium">唯一键</el-tag>
            <span v-else>{{ scope.row.keyType }}</span>
          </template>
        </el-table-column>
        <el-table-column label="非空" align="center" width="70px">
          <template slot-scope="scope">
            <!--所有的键必须不为空-->
            <el-checkbox
              v-if="data[scope.$index].keyType === null || data[scope.$index].keyType === ''"
              v-model="data[scope.$index].notNull"
            />
            <el-checkbox v-else v-model="data[scope.$index].notNull" disabled />
          </template>
        </el-table-column>
        <el-table-column label="出现在列表" align="center" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableList" />
          </template>
        </el-table-column>
        <el-table-column label="出现在表单" align="center" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-if="data[scope.$index].keyType !== 'PRI'" v-model="data[scope.$index].enableForm" />
            <el-checkbox v-else v-model="data[scope.$index].enableForm" disabled />
          </template>
        </el-table-column>
        <el-table-column label="出现在查询" align="center" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableQuery" />
          </template>
        </el-table-column>
        <el-table-column label="允许排序" align="center" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableSort" />
          </template>
        </el-table-column>
        <el-table-column label="允许校验" align="center" width="70px">
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
        <el-table-column label="表单类型" :show-overflow-tooltip="true" width="150px">
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
              <el-option label="精确匹配" value="EQUALS" />
              <el-option label="模糊匹配" value="LIKE" />
              <el-option label="范围匹配" value="BETWEEN" />
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
            <el-tooltip :content="data[scope.$index].datePattern" placement="bottom">
              <el-input
                v-if="data[scope.$index].type === 'datetime'"
                v-model="data[scope.$index].datePattern"
                size="mini"
                class="edit-input"
              />
              <el-input v-else v-model="data[scope.$index].datePattern" disabled size="mini" class="edit-input" />
            </el-tooltip>
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
  props: {
    info: {
      type: Object,
      required: true,
      default: () => {
        return {
          dbId: null,
          schemaName: null,
          tableName: null,
          createBy: null
        }
      }
    }
  },
  data() {
    return {
      data: [],
      dicts: [],
      loading: false,
      configLoading: false,
      genLoading: false,
      downloadLoading: false
    }
  },
  created() {
    this.$nextTick(() => {
      this.queryColumnConfig()
    })
  },
  methods: {
    queryColumnConfig() {
      this.loading = true
      codeApi
        .queryColumnConfig(this.info)
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
          ...this.info,
          columns: this.data
        })
        .then(() => {
          // this.queryColumnConfig()
          this.$emit('getCodeConfigInfo')
          this.$message({ type: 'success', message: '保存成功' })
          this.configLoading = false
        })
        .catch(() => {
          this.$message({ type: 'error', message: '保存失败' })
          this.configLoading = false
        })
    },
    syncQueryColumnConfig() {
      this.loading = true
      codeApi
        .syncQueryColumnConfig(this.info)
        .then(data => {
          this.data = data
          this.$message({ type: 'success', message: '同步成功' })
          this.loading = false
        })
        .catch(() => {
          this.$message({ type: 'error', message: '同步失败' })
          this.loading = false
        })
    },
    toGenerate() {
      // 生成代码
      this.genLoading = true
      codeApi
        .generateCode(this.info)
        .then(data => {
          this.$message({ type: 'success', message: '生成成功' })
          this.genLoading = false
        })
        .catch(() => {
          this.$message({ type: 'error', message: '生成失败' })
          this.genLoading = false
        })
    },
    toDownload() {
      // 打包下载
      this.downloadLoading = true
      codeApi
        .downloadCode(this.info)
        .then(data => {
          downloadFile(data, this.info.tableName, 'zip')
          this.$message({ type: 'success', message: '下载成功' })
          this.downloadLoading = false
        })
        .catch(() => {
          this.$message({ type: 'error', message: '下载失败' })
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
