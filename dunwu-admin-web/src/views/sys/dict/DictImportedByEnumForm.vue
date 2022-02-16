<template>
  <div class="app-container">
    <el-page-header content="根据Java枚举上传数据字典" @back="goBack" />

    <el-alert
      title="使用说明"
      type="info"
      description="1️⃣ 先上传一个上传Java枚举文件，后台会自动解析枚举的信息；2️⃣ 选择枚举的参数作为数据字典项的编码和名称，点击保存即可。"
      show-icon
      style="margin-top: 20px; margin-bottom: 20px"
    />

    <el-steps :active="step" align-center style="margin-top: 20px">
      <el-step title="1️⃣ 上传Java枚举文件" icon="el-icon-upload" />
      <el-step title="2️⃣ 选择枚举参数作为数据字典项" icon="el-icon-edit" />
    </el-steps>

    <div v-if="step === 1" style="margin:0 auto; width:600px;">
      <el-upload
        accept=".java"
        :action="importUrl"
        drag
        :headers="headers"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="onUploadSuccess"
        :on-error="onUploadError"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
      </el-upload>
    </div>
    <div v-else>
      <el-card shadow="hover">
        <div slot="header">
          <span>待保存的数据字典信息</span>
          <el-button size="mini" type="primary" style="float: right;" :loading="saveLoading" @click="saveDict">
            保存
          </el-button>
        </div>
        <el-row type="flex" class="row-bg" justify="center">
          <el-col :span="8">
            <div class="el-descriptions__header" style="margin-top: 20px">
              <div class="el-descriptions__title">字典信息</div>
            </div>
            <el-form ref="form" :model="form" :rules="rules" size="mini" label-width="100px" style="margin-right: 20px">
              <el-form-item label="字典编码" prop="code">
                <el-input v-model="form.code" />
              </el-form-item>
              <el-form-item label="字典名称" prop="name">
                <el-input v-model="form.name" />
              </el-form-item>
              <el-form-item label="是否启用" prop="disabled">
                <el-radio
                  v-for="item in dict['disabled_status'].options"
                  :key="item.id"
                  v-model="form.disabled"
                  :label="item.code"
                >
                  {{ item.name }}
                </el-radio>
              </el-form-item>
              <el-form-item label="备注">
                <el-input
                  v-model="form.note"
                  type="textarea"
                  :autosize="{ minRows: 3, maxRows: 5 }"
                  placeholder="请输入内容"
                />
              </el-form-item>
              <el-form-item label="字典选项编码">
                <el-select
                  v-model="dictOptionCodeKey"
                  placeholder="请选择字典选项编码"
                  @change="changeDictOptionCodeKey"
                >
                  <el-option v-for="item in paramOptions" :key="item.code" :label="item.name" :value="item.code" />
                </el-select>
              </el-form-item>
              <el-form-item label="字典选项名称">
                <el-select
                  v-model="dictOptionNameKey"
                  placeholder="请选择字典选项名称"
                  @change="changeDictOptionNameKey"
                >
                  <el-option v-for="item in paramOptions" :key="item.code" :label="item.name" :value="item.code" />
                </el-select>
              </el-form-item>
              <el-form-item label="字典选项备注">
                <el-select
                  v-model="dictOptionNoteKey"
                  placeholder="请选择字典选项备注"
                  @change="changeDictOptionNoteKey"
                >
                  <el-option v-for="item in paramOptions" :key="item.code" :label="item.name" :value="item.code" />
                </el-select>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="12">
            <div class="el-descriptions__header" style="margin-top: 20px">
              <div class="el-descriptions__title">字典选项信息</div>
            </div>
            <el-table ref="table" :data="options" border stripe max-height="600" style="width: 100%;">
              <el-table-column prop="code" label="字典选项编码" :show-overflow-tooltip="true" />
              <el-table-column prop="name" label="字典选项名称" :show-overflow-tooltip="true" />
              <el-table-column prop="note" label="字典选项备注" :show-overflow-tooltip="true" />
            </el-table>
          </el-col>
        </el-row>
      </el-card>

      <el-card shadow="hover" style="margin-top: 20px">
        <div slot="header" class="clearfix">
          <span>根据Java文件解析出的枚举信息</span>
        </div>
        <el-descriptions title="枚举基本信息" size="mini" :column="2" border>
          <el-descriptions-item>
            <template slot="label">
              枚举名
            </template>
            {{ enumInfo.name }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              枚举注释
            </template>
            {{ enumInfo.comment }}
          </el-descriptions-item>
        </el-descriptions>
        <div class="el-descriptions__header" style="margin-top: 20px">
          <div class="el-descriptions__title">枚举项信息</div>
        </div>
        <el-table ref="table" :data="enumEntries" border stripe max-height="600" style="width: 100%;">
          <el-table-column prop="name" label="枚举项名称" :show-overflow-tooltip="true" />
          <el-table-column prop="comment" label="枚举项注释" :show-overflow-tooltip="true" />
          <el-table-column v-for="(item, index) in enumEntryParamKeys" :key="item" :label="item">
            <template slot-scope="scope">
              <span>
                {{ scope.row.params[index] }}
              </span>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import ElDragDialog from '@/directive/el-drag-dialog'
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
import DictApi from './DictApi'

export default {
  name: 'DictImportedByEnumForm',
  components: {},
  directives: { ElDragDialog },
  /**
   * 设置数据字典
   */
  dicts: ['disabled_status'],
  data() {
    return {
      importUrl: this.baseApi + '/' + 'sys/dict/upload/javaenum',
      step: 1,
      headers: {
        DunwuToken: getToken()
      },
      permission: {
        add: ['admin', 'sys:dict:add'],
        edit: ['admin', 'sys:dict:edit'],
        del: ['admin', 'sys:dict:del']
      },
      form: {
        code: null,
        name: null,
        disabled: false,
        note: null,
        options: []
      },
      enumInfo: {
        name: null,
        comment: null,
        entries: []
      },
      rules: {},
      options: [],
      enumEntries: [],
      enumEntryParamKeys: [],
      paramOptions: [{ code: 'name', name: '枚举项名称' }, { code: 'comment', name: '枚举项注释' }],
      dictOptionCodeKey: 'name',
      dictOptionNameKey: 'name',
      dictOptionNoteKey: 'comment',
      saveLoading: false
    }
  },
  computed: {
    ...mapGetters(['baseApi'])
  },
  created() {
    this.importUrl = this.baseApi + '/' + 'sys/dict/upload/javaenum'
    this.form.disabled = `${this.form.disabled}`
  },
  methods: {
    goBack() {
      this.$router.go(-1)
    },
    beforeUpload(file) {
      const isLt1M = file.size / 1024 / 1024 < 1
      if (isLt1M) {
        return true
      }

      this.$message({ message: '请不要上传大于1m的文件.', type: 'warning' })
      return false
    },
    onUploadSuccess(response, file, fileList) {
      if (response && response.code === 0) {
        this.$message({ type: 'success', message: '上传成功' })
        this.step = 2
        if (response.data) {
          this.enumInfo = response.data
          this.form.code = this.enumInfo.name
          this.form.name = this.enumInfo.name
          this.form.note = this.enumInfo.comment
          this.enumEntries = this.enumInfo.entries
          if (this.enumEntries && this.enumEntries.length > 0) {
            // 枚举有参数
            if (this.enumEntries[0].params && this.enumEntries[0].params.length > 0) {
              const len = this.enumEntries[0].params.length
              this.enumEntryParamKeys = []
              for (let i = 0; i < len; i++) {
                const paramName = '参数' + (i + 1)
                this.enumEntryParamKeys.push(paramName)
                this.paramOptions.push({ code: i.toString(), name: paramName })
              }
            }
            this.refreshDictOptions()
          }
        }
      } else {
        console.error('上传失败', response)
        this.$message({ type: 'error', message: '上传失败' })
      }
    },
    onUploadError(err, file, fileList) {
      console.error('上传失败', err)
      this.$message({ type: 'error', message: '上传失败' })
    },
    changeDictOptionCodeKey() {
      this.refreshDictOptions()
    },
    changeDictOptionNameKey() {
      this.refreshDictOptions()
    },
    changeDictOptionNoteKey() {
      this.refreshDictOptions()
    },
    refreshDictOptions() {
      this.options = []
      if (this.enumEntries && this.enumEntries.length > 0) {
        this.enumEntries.forEach(item => {
          const option = { code: null, name: null, note: null, disabled: false }

          // 根据选择，获取字典选项编码
          if (this.dictOptionCodeKey === 'name') {
            option.code = item.name
          } else if (this.dictOptionCodeKey === 'comment') {
            option.code = item.comment
          } else {
            const index = Number.parseInt(this.dictOptionCodeKey)
            option.code = item.params[index]
          }

          // 根据选择，获取字典选项名称
          if (this.dictOptionNameKey === 'name') {
            option.name = item.name
          } else if (this.dictOptionNameKey === 'comment') {
            option.name = item.comment
          } else {
            const index = Number.parseInt(this.dictOptionNameKey)
            option.name = item.params[index]
          }

          // 根据选择，获取字典选项备注
          if (this.dictOptionNoteKey === 'name') {
            option.note = item.name
          } else if (this.dictOptionNoteKey === 'comment') {
            option.note = item.comment
          } else {
            const index = Number.parseInt(this.dictOptionNoteKey)
            option.note = item.params[index]
          }

          // 获取字典选项备注
          // option.note = item.comment
          this.options.push(option)
          this.form.options = this.options
        })
      }
    },
    saveDict() {
      this.saveLoading = true
      DictApi.saveDictWithOptions(this.form)
        .then(res => {
          this.saveLoading = false
          if (res) {
            this.$message({ message: '成功', type: 'success' })
            this.goBack()
          }
        })
        .catch(() => {
          this.saveLoading = false
        })
    }
  }
}
</script>

<style scoped>
/deep/ .el-upload {
  margin: 0 auto;
  width: 100%;
}
/deep/ .el-upload-dragger {
  margin: 0 auto;
  width: 80%;
}
/deep/ .el-upload-dragger .el-upload__text {
  font-size: 20px;
}

/deep/ .el-table__header-wrapper {
  background: #909399;
}
/deep/ .el-descriptions__title {
  font-size: 14px;
}
</style>
