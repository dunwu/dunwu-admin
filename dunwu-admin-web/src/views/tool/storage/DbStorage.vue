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
          v-permission="['admin', 'storage:add']"
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
        <el-form-item label="文件名">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>
        <!--   上传文件   -->
        <el-form-item v-if="crud.status.add" label="上传">
          <Upload ref="upload" :files.sync="files" :limit="3" list-type="text" store-type="DB" />
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
      <el-table-column prop="extension" label="文件扩展名" />
      <el-table-column prop="contentType" label="文件实际类型" />
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
import CRUD, { crud, presenter, header, form } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'
import Upload from '@/components/Upload'
import ElDragDialog from '@/directive/el-drag-dialog'
import { getToken } from '@/utils/auth'
import StorageApi from './StorageApi'

const defaultForm = { id: null, name: '' }
export default {
  components: { Pagination, TableOperation, TableQueryOperation, DateRangePicker, Upload },
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
      uploadLimit: 1,
      /**
       * 文件列表
       */
      files: [],
      permission: {
        edit: ['admin', 'storage:edit'],
        del: ['admin', 'storage:del']
      }
    }
  },
  computed: {
    ...mapGetters(['baseApi', 'fileUploadApi'])
  },
  created() {
    this.query.storeType = 'DB'
    this.crud.optShow.add = false
  },
  methods: {
    // 上传文件
    upload() {
      this.$refs.upload.submit()
    },
    beforeUpload(file) {
      let isLt2M = true
      isLt2M = file.size / 1024 / 1024 < 100
      if (!isLt2M) {
        this.loading = false
        this.$message.error('上传文件大小不能超过 100MB!')
      }
      this.form.name = file.name
      return isLt2M
    },
    handleSuccess(response, file, fileList) {
      this.crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, '上传成功')
      this.$refs.upload.clearFiles()
      this.crud.status.add = CRUD.STATUS.NORMAL
      this.crud.resetForm()
      this.crud.toQuery()
    },
    // 监听上传失败
    handleError(e, file, fileList) {
      const msg = JSON.parse(e.message)
      this.$notify({
        title: msg.message,
        type: 'error',
        duration: 2500
      })
      this.loading = false
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
