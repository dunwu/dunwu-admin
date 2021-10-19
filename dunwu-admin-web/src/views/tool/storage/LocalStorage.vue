<template>
  <div class="app-container" style="padding: 8px;">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.blurry"
          clearable
          size="small"
          placeholder="输入内容模糊搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <date-range-picker v-model="query.createTime" class="date-item" />
        <TableQueryOperation />
      </div>
      <TableOperation :permission="permission">
        <!-- 添加 -->
        <el-button
          slot="left"
          v-permission="['admin', 'tool:storage:add']"
          class="filter-item"
          size="mini"
          type="primary"
          icon="el-icon-upload"
          @click="crud.toAdd"
        >
          上传
        </el-button>
      </TableOperation>
    </div>
    <!--表单组件-->
    <el-dialog
      v-el-drag-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.add ? '文件上传' : '编辑文件'"
      width="500px"
    >
      <el-form ref="form" :model="form" size="small" label-width="80px">
        <el-form-item label="命名空间">
          <el-input v-model="form.namespace" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="标签">
          <el-input v-model="form.tag" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="文件名">
          <el-input v-model="form.originName" style="width: 370px;" />
        </el-form-item>
        <el-form-item v-if="crud.status.add">
          <el-upload
            ref="upload"
            drag
            multiple
            :limit="limit"
            :action="
              fileUploadApi +
                '?namespace=' +
                form.namespace +
                '&tag=' +
                form.tag +
                '&originName=' +
                form.originName +
                '&storeType=LOCAL'
            "
            list-type="text"
            :file-size="10"
            :file-list="fileList"
            :headers="headers"
            :before-upload="uploadFileBefore"
            :on-success="uploadFileSuccess"
            :on-error="uploadFileError"
            :on-remove="removeFile"
            :on-exceed="fileExceed"
            :auto-upload="false"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">
              将文件拖到此处，或
              <em>点击上传</em>
            </div>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button v-if="crud.status.add" :loading="loading" type="primary" @click="upload">确认</el-button>
        <el-button v-else :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      style="width: 100%;"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="fileName" label="文件名">
        <template slot-scope="scope">
          <el-popover :content="scope.row.accessUrl" placement="top-start" title="路径" width="200" trigger="hover">
            <a
              slot="reference"
              :href="baseApi + '/file/' + scope.row.type + '/' + scope.row.realName"
              class="el-link--primary"
              style="word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;color: #1890ff;font-size: 13px;"
              target="_blank"
            >
              {{ scope.row.fileName }}
            </a>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column prop="path" label="预览图">
        <template slot-scope="{ row }">
          <el-image
            :src="baseApi + '/file/' + row.type + '/' + row.realName"
            :preview-src-list="[baseApi + '/file/' + row.type + '/' + row.realName]"
            fit="contain"
            lazy
            class="el-avatar"
          >
            <div slot="error">
              <i class="el-icon-document" />
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="namespace" label="命名空间" />
      <el-table-column prop="tag" label="标签" />
      <el-table-column prop="originName" label="源文件名" />
      <el-table-column prop="extension" label="文件扩展名" />
      <el-table-column prop="storeType" label="存储服务类型" />
      <el-table-column prop="size" label="大小" />
      <el-table-column prop="createBy" label="操作人" />
      <el-table-column prop="createTime" label="创建日期" />
    </el-table>
    <!--分页组件-->
    <Pagination />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CRUD, { crud, form, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import ElDragDialog from '@/directive/el-drag-dialog'
import { getToken } from '@/utils/auth'
import StorageApi from './StorageApi'
import qs from 'qs'

const defaultForm = { id: null, namespace: null, tag: null, originName: null, storeType: 'LOCAL' }
const IMAGE_TYPES = ['image/png', 'image/jpeg', 'image/bmp', 'image/gif', 'image/webp']
export default {
  components: { Pagination, TableOperation, TableQueryOperation, DateRangePicker },
  directives: { ElDragDialog },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '文件',
      url: 'tool/storage',
      crudMethod: { ...StorageApi }
    })
  },
  data() {
    return {
      loading: false,
      headers: { DunwuToken: getToken() },
      /**
       * 上传文件数限制
       */
      limit: 1,
      /**
       * 文件列表
       */
      fileList: [],
      fileSize: 10,
      params: {
        namespace: this.crud.form.namespace,
        tag: this.crud.form.tag,
        originName: this.crud.form.originName,
        storeType: this.crud.form.storeType
      },
      urlParams: null,
      permission: {
        edit: ['admin', 'tool:storage:edit'],
        del: ['admin', 'tool:storage:del']
      }
    }
  },
  computed: {
    ...mapGetters(['baseApi', 'fileUploadApi'])
  },
  mounted() {
    this.query.storeType = 'LOCAL'
    this.crud.optShow.add = false
    console.info('params', this.params)
    this.urlParams = qs.stringify(this.params)
    console.info('urlParams', this.urlParams)
  },
  methods: {
    // 上传文件
    upload() {
      const params = {
        namespace: this.crud.form.namespace,
        tag: this.crud.form.tag,
        originName: this.crud.form.originName,
        storeType: this.crud.form.storeType
      }
      this.urlParams = qs.stringify(params)
      console.info('urlParams', this.urlParams)
      this.$refs.upload.submit()
    },
    uploadFileSuccess(response, file, fileList) {
      // console.info('uploadFileSuccess response', response)
      // console.info('uploadFileSuccess file', file)
      // console.info('uploadFileSuccess fileList', fileList)
      if (response && response.code === 0) {
        // file.uid = response.data[0].fileId
        const fileInfo = response.data
        this.fileList.push(fileInfo)
        this.$message({ type: 'success', message: '上传成功' })

        this.crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, '上传成功')
        this.$refs.upload.clearFiles()
        this.crud.status.add = CRUD.STATUS.NORMAL
        this.crud.resetForm()
        this.crud.toQuery()
      } else {
        console.error('上传失败', response.msg)
        this.$message({ type: 'error', message: response.msg })
      }
    },
    uploadFileError(err, file, fileList) {
      const msg = JSON.parse(err.msg)
      this.$message({ type: 'error', message: msg })
      this.loading = false
    },
    removeFile(file, fileList) {
      this.fileList.splice(this.fileList.findIndex(f => f.url === file.url), 1)
    },
    clearFiles() {
      this.fileList.splice(0, this.fileList.length)
    },
    uploadFileBefore(file) {
      // console.info('fileBeforeUpload file type', file.type)
      if (this.listType === 'picture' || this.listType === 'picture-card') {
        if (!IMAGE_TYPES.includes(file.type)) {
          this.$message.error(`允许上传的图片格式：${IMAGE_TYPES}，实际上传的图片格式：${file.type}`)
          return false
        }
      }

      const isFileSize = file.size / 1024 / 1024 < this.fileSize
      if (!isFileSize) {
        this.$message.error('上传文件大小不能超过' + this.fileSize + 'MB')
        return false
      }
    },
    fileExceed() {
      this.$message.error(`允许最多上传 ${this.limit} 个文件，已上传了 ${this.fileList.length} 个文件`)
    }
  }
}
</script>

<style scoped>
::v-deep .el-image__error,
.el-image__placeholder {
  background: none;
}
::v-deep .el-image-viewer__wrapper {
  top: 55px;
}
</style>
