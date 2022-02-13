<template>
  <el-card class="box-card" shadow="never">
    <div slot="header" class="clearfix">
      <span class="role-span">
        <el-tag type="info">{{ info.tableName }}</el-tag>
        字段级别配置
      </span>
      <el-button
        icon="el-icon-view"
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
    <el-form size="mini" label-width="90px">
      <el-table v-loading="loading" :data="data" border stripe style="width: 100%;">
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-descriptions title="配置信息" :column="4" size="mini" style="margin: 20px" border>
              <el-descriptions-item label="属性名">{{ scope.row.propertyName }}</el-descriptions-item>
              <el-descriptions-item label="注释">{{ scope.row.comment }}</el-descriptions-item>
              <el-descriptions-item label="展示名">{{ scope.row.labelName }}</el-descriptions-item>
              <el-descriptions-item label="DB数据类型">{{ scope.row.type }}</el-descriptions-item>
              <el-descriptions-item label="Java数据类型">{{ scope.row.javaType }}</el-descriptions-item>
              <el-descriptions-item label="KEY类型">
                <el-tag v-if="scope.row.keyType === 'PRI'" size="mini">主键</el-tag>
                <el-tag v-else-if="scope.row.keyType === 'MUL'" size="mini">键</el-tag>
                <el-tag v-else-if="scope.row.keyType === 'UNI'" size="mini">唯一键</el-tag>
                <span v-else>{{ scope.row.keyType }}</span>
              </el-descriptions-item>
              <el-descriptions-item label="是否为空">{{ scope.row.notNull }}</el-descriptions-item>
              <el-descriptions-item label="列表显示">{{ scope.row.enableList }}</el-descriptions-item>
              <el-descriptions-item label="表单显示">{{ scope.row.enableForm }}</el-descriptions-item>
              <el-descriptions-item label="查询显示">{{ scope.row.enableQuery }}</el-descriptions-item>
              <el-descriptions-item label="允许排序">{{ scope.row.enableSort }}</el-descriptions-item>
              <el-descriptions-item label="允许校验">{{ scope.row.enableValidate }}</el-descriptions-item>
              <el-descriptions-item label="列表类型">{{ scope.row.listType }}</el-descriptions-item>
              <el-descriptions-item label="表单类型">{{ scope.row.formType }}</el-descriptions-item>
              <el-descriptions-item label="查询类型">{{ scope.row.queryType }}</el-descriptions-item>
              <el-descriptions-item label="排序类型">{{ scope.row.sortType }}</el-descriptions-item>
              <el-descriptions-item label="校验类型">{{ scope.row.validateType }}</el-descriptions-item>
              <el-descriptions-item label="日期格式">{{ scope.row.datePattern }}</el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column prop="propertyName" label="属性名" :show-overflow-tooltip="true" width="120px">
          <template slot-scope="scope">
            <el-tooltip :content="data[scope.$index].propertyName" placement="bottom">
              <span>{{ data[scope.$index].propertyName }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="labelName" label="展示名" :show-overflow-tooltip="true" width="150px">
          <template slot-scope="scope">
            <el-tooltip :content="data[scope.$index].labelName" placement="bottom">
              <el-input v-model="data[scope.$index].labelName" size="mini" class="edit-input" />
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="非空" align="center" width="50px">
          <template slot-scope="scope">
            <!--所有的键必须不为空-->
            <el-checkbox
              v-if="data[scope.$index].keyType === null || data[scope.$index].keyType === ''"
              v-model="data[scope.$index].notNull"
            />
            <el-checkbox v-else v-model="data[scope.$index].notNull" disabled />
          </template>
        </el-table-column>
        <el-table-column label="列表显示" align="center" width="50px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableList" />
          </template>
        </el-table-column>
        <el-table-column label="表单显示" align="center" width="50px">
          <template slot-scope="scope">
            <el-checkbox v-if="data[scope.$index].keyType !== 'PRI'" v-model="data[scope.$index].enableForm" />
            <el-checkbox v-else v-model="data[scope.$index].enableForm" disabled />
          </template>
        </el-table-column>
        <el-table-column label="查询显示" align="center" width="50px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableQuery" />
          </template>
        </el-table-column>
        <el-table-column label="允许排序" align="center" width="50px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableSort" />
          </template>
        </el-table-column>
        <el-table-column label="允许校验" align="center" width="50px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableValidate" />
          </template>
        </el-table-column>
        <el-table-column label="列表类型" width="100px">
          <template slot-scope="scope">
            <el-select
              v-model="data[scope.$index].listType"
              :disabled="!data[scope.$index].enableList"
              filterable
              class="edit-input"
              size="mini"
              placeholder="请选择"
            >
              <el-option label="文本" value="Text" />
              <el-option label="图片" value="Image" />
              <el-option label="日期" value="Date" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="表单类型" :show-overflow-tooltip="true" width="150px">
          <template slot-scope="scope">
            <el-select
              v-model="data[scope.$index].formType"
              :disabled="!data[scope.$index].enableForm"
              filterable
              class="edit-input"
              size="mini"
              placeholder="请选择"
            >
              <el-option label="输入框" value="Input" />
              <el-option label="文本域输入框" value="Textarea" />
              <el-option label="计数器" value="InputNumber" />
              <el-option label="单选框" value="Radio" />
              <el-option label="选择器" value="Select" />
              <el-option label="日期时间选择器" value="DateTimePicker" />
              <el-option label="字典" value="Dict" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="查询类型" width="150px">
          <template slot-scope="scope">
            <el-select
              v-model="data[scope.$index].queryType"
              :disabled="!data[scope.$index].enableQuery"
              filterable
              class="edit-input"
              size="mini"
              placeholder="请选择"
            >
              <el-option label="精确匹配" value="EQUALS" />
              <el-option label="模糊匹配" value="LIKE" />
              <el-option label="范围匹配" value="BETWEEN" />
              <el-option label="字典" value="Dict" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="排序类型" width="100px">
          <template slot-scope="scope">
            <el-select
              v-model="data[scope.$index].sortType"
              :disabled="!data[scope.$index].enableSort"
              filterable
              class="edit-input"
              size="mini"
              placeholder="请选择"
            >
              <el-option label="升序" value="asc" />
              <el-option label="降序" value="desc" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="校验类型" width="120px">
          <template slot-scope="scope">
            <el-select
              v-model="data[scope.$index].validateType"
              :disabled="!data[scope.$index].enableValidate"
              filterable
              class="edit-input"
              size="mini"
              placeholder="请选择"
            >
              <el-option label="字符串" value="string" />
              <el-option label="数字" value="number" />
              <el-option label="布尔" value="boolean" />
              <el-option label="整型" value="integer" />
              <el-option label="浮点型" value="float" />
              <el-option label="URL" value="url" />
              <el-option label="邮件" value="email" />
              <el-option label="日期" value="date" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="字典类型" width="120px">
          <template slot-scope="scope">
            <el-select
              v-model="data[scope.$index].dictCode"
              :disabled="data[scope.$index].formType !== 'Dict'"
              filterable
              class="edit-input"
              size="mini"
              placeholder="请选择"
            >
              <el-option
                v-for="item in dictOptions"
                :key="item.code"
                :label="item.name"
                :value="item.code"
                :disabled="item.disabled"
              >
                <span style="float: left">名称：{{ item.name }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px; margin-left: 10px">
                  编码：{{ item.code }}
                </span>
              </el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="日期格式" width="150px">
          <template slot-scope="scope">
            <el-tooltip :content="data[scope.$index].datePattern" placement="bottom">
              <el-input
                v-model="data[scope.$index].datePattern"
                :disabled="data[scope.$index].type !== 'datetime'"
                size="mini"
                class="edit-input"
              />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </el-card>
</template>

<script>
import CodeApi from '@/api/code/codeApi'
import DictApi from '@/views/sys/dict/DictApi'
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
      dictOptions: [],
      loading: false,
      configLoading: false,
      genLoading: false,
      downloadLoading: false
    }
  },
  created() {
    this.$nextTick(() => {
      this.queryColumnConfig()
      this.queryDict()
    })
  },
  methods: {
    queryColumnConfig() {
      this.loading = true
      CodeApi.queryColumnConfig(this.info)
        .then(data => {
          this.loading = false
          this.data = data
        })
        .catch(err => {
          this.loading = false
          console.error('保存失败', err.response.data.msg)
        })
    },
    queryDict() {
      DictApi.list()
        .then(data => {
          this.dictOptions = data
        })
        .catch(err => {
          console.error('查询数据字典失败', err)
          this.$message({ type: 'error', message: '查询数据字典失败' })
        })
    },
    saveColumnConfig() {
      this.configLoading = true
      CodeApi.saveColumnConfig({
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
      CodeApi.syncQueryColumnConfig(this.info)
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
      CodeApi.generateCode(this.info)
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
      CodeApi.downloadCode(this.info)
        .then(result => {
          downloadFile(result, this.info.tableName, 'zip')
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
