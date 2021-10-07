<template>
  <el-upload
    ref="upload"
    multiple
    :limit="limit"
    :action="fileUploadApi + '?' + urlParams"
    :list-type="listType"
    :file-list="fileList"
    :file-size="fileSize"
    :headers="headers"
    :before-upload="uploadFileBefore"
    :on-success="uploadFileSuccess"
    :on-error="uploadFileError"
    :on-remove="removeFile"
    :on-exceed="fileExceed"
  >
    <el-button size="mini" type="primary">点击上传</el-button>
  </el-upload>
</template>

<script>
import { mapGetters } from 'vuex'
import { getToken } from '@/utils/auth'
import qs from 'qs'

const IMAGE_TYPES = ['image/png', 'image/jpeg', 'image/bmp', 'image/gif', 'image/webp']
export default {
  name: 'Upload',
  props: {
    /**
     * 上传文件数限制
     */
    limit: {
      type: Number,
      required: false,
      default: 1
    },
    /**
     * 文件大小限制，单位 MB
     */
    fileSize: {
      type: Number,
      required: false,
      default: 10
    },
    /**
     * 文件列表的类型，支持：text/picture/picture-card
     */
    listType: {
      type: String,
      required: false,
      default: 'text'
    },
    /**
     * 命名空间。一般对应业务系统
     */
    namespace: {
      type: String,
      required: false,
      default: null
    },
    /**
     * 标签。供业务系统使用
     */
    tag: {
      type: String,
      required: false,
      default: null
    },
    /**
     * 源文件名
     */
    originName: {
      type: String,
      required: false,
      default: null
    },
    /**
     * 文件存储服务类型。支持类型：LOCAL、DB、FDFS
     */
    storeType: {
      type: String,
      required: false,
      default: 'DB'
    },
    /**
     * 文件列表。和上层组件同步的真实数据
     */
    files: {
      type: Array,
      required: true,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      IMAGE_TYPES,
      /**
       * 本组件和父组件双向绑定的文件列表
       */
      fileList: this.files,
      headers: { DunwuToken: getToken() },
      params: {
        namespace: this.namespace,
        tag: this.tag,
        originName: this.originName,
        storeType: this.storeType
      },
      urlParams: null
    }
  },
  computed: {
    ...mapGetters(['fileUploadApi'])
  },
  watch: {
    files(val) {
      // console.info('监听到 files 变化', val)
      this.fileList = val
    }
  },
  mounted() {
    console.info('params', this.params)
    this.urlParams = qs.stringify(this.params)
    console.info('urlParams', this.urlParams)
  },
  methods: {
    uploadFileSuccess(response, file, fileList) {
      console.info('uploadFileSuccess response', response)
      console.info('uploadFileSuccess file', file)
      console.info('uploadFileSuccess fileList', fileList)
      if (response && response.code === 0) {
        // file.uid = response.data[0].fileId
        const fileInfo = response.data
        this.fileList.push(fileInfo)
        this.$emit('update:files', this.fileList)
        this.$message({ type: 'success', message: '上传成功' })
      } else {
        console.error('上传失败', response.msg)
        this.$message({ type: 'error', message: response.msg })
      }
    },
    uploadFileError(err, file, fileList) {
      const msg = JSON.parse(err.msg)
      this.$message({ type: 'error', message: msg })
    },
    removeFile(file, fileList) {
      this.fileList.splice(
        this.fileList.findIndex(f => f.url === file.url),
        1
      )
      this.$emit('update:files', this.fileList)
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

<style scoped></style>
