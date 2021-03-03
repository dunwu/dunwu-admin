<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span class="role-span">{{ tableName }} 表级别配置</span>
        <el-button
          :loading="configLoading"
          icon="el-icon-check"
          size="mini"
          style="float: right; padding: 6px 9px"
          type="primary"
          @click="saveTableConfig"
        >
          保存
        </el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="78px">
        <el-form-item label="作者名称" prop="author">
          <el-input v-model="form.author" style="width: 40%" />
          <span style="color: #C0C0C0;margin-left: 10px;">类上面的作者名称</span>
        </el-form-item>
        <el-form-item label="模块名称" prop="moduleName">
          <el-input v-model="form.moduleName" style="width: 40%" />
          <span style="color: #C0C0C0;margin-left: 10px;">模块的名称，请选择项目中已存在的模块</span>
        </el-form-item>
        <el-form-item label="至于包下" prop="pack">
          <el-input v-model="form.pack" style="width: 40%" />
          <span style="color: #C0C0C0;margin-left: 10px;">项目包的名称，生成的代码放到哪个包里面</span>
        </el-form-item>
        <el-form-item label="接口名称" prop="apiAlias">
          <el-input v-model="form.apiAlias" style="width: 40%" />
          <span style="color: #C0C0C0;margin-left: 10px;">接口的名称，用于控制器与接口文档中</span>
        </el-form-item>
        <el-form-item label="前端路径" prop="path">
          <el-input v-model="form.path" style="width: 40%" />
          <span style="color: #C0C0C0;margin-left: 10px;">输入views文件夹下的目录，不存在即创建</span>
        </el-form-item>
        <el-form-item label="去表前缀" prop="prefix">
          <el-input v-model="form.prefix" placeholder="默认不去除表前缀" style="width: 40%" />
          <span style="color: #C0C0C0;margin-left: 10px;">默认不去除表前缀，可自定义</span>
        </el-form-item>
        <el-form-item label="是否覆盖" prop="cover">
          <el-radio-group v-model="form.cover" size="mini" style="width: 40%">
            <el-radio-button label="true">是</el-radio-button>
            <el-radio-button label="false">否</el-radio-button>
          </el-radio-group>
          <span style="color: #C0C0C0;margin-left: 10px;">谨防误操作，请慎重选择</span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import crud from '@/mixins/crud'
import dictApi from '@/api/system/dict'
import tableConfigApi from '@/api/generator/tableConfigApi'
export default {
  name: 'TableConfig',
  components: {},
  mixins: [crud],
  data() {
    return {
      schemaName: '',
      tableName: '',
      tableHeight: 550,
      configLoading: false,
      dicts: [],
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
