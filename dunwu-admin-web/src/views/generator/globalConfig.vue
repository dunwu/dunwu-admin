<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span class="role-span">全局级别配置</span>
        <el-button
          :loading="configLoading"
          icon="el-icon-check"
          size="mini"
          style="float: right; padding: 6px 9px"
          type="primary"
          @click="saveGlobalConfig"
        >
          保存
        </el-button>
      </div>
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="150px">
        <el-form-item label="开启权限校验" prop="enablePermission">
          <el-radio-group v-model="form.enablePermission" size="mini" style="width: 370px;">
            <el-radio-button label="true">是</el-radio-button>
            <el-radio-button label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开启文件覆盖模式" prop="enableOverride">
          <el-radio-group v-model="form.enableOverride" size="mini" style="width: 370px;">
            <el-radio-button label="true">是</el-radio-button>
            <el-radio-button label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开启Swagger2" prop="enableSwagger2">
          <el-radio-group v-model="form.enableSwagger2" size="mini" style="width: 370px;">
            <el-radio-button label="true">是</el-radio-button>
            <el-radio-button label="false">否</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="输出路径" prop="outputDir">
          <el-input v-model="form.outputDir" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="后端代码路径" prop="backendPath">
          <el-input v-model="form.backendPath" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="前端代码路径" prop="frontendPath">
          <el-input v-model="form.frontendPath" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="包路径" prop="packagePath">
          <el-input v-model="form.packagePath" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="主键类型" prop="idType">
          <el-input v-model="form.idType" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="时间类型" prop="dateType">
          <el-input v-model="form.dateType" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="时间格式化" prop="datePattern">
          <el-input v-model="form.datePattern" style="width: 370px;" />
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import Constant from '@/utils/constant'
import generatorApi from '@/api/generator/generatorApi'
export default {
  name: 'GlobalConfig',
  components: {},
  data() {
    return {
      tableHeight: 550,
      columnLoading: false,
      configLoading: false,
      dicts: [],
      form: {
        id: null,
        enablePermission: false,
        enableOverride: false,
        enableSwagger2: false,
        author: null,
        outputDir: null,
        backendPath: null,
        frontendPath: null,
        packagePath: null,
        idType: null,
        dateType: null,
        datePattern: null
      },
      rules: {}
    }
  },
  created() {
    this.$nextTick(() => {
      generatorApi.findGlobalConfig().then(data => {
        this.form = data
      })
      // dictApi.list().then(data => {
      //   this.dicts = data
      // })
    })
  },
  methods: {
    saveGlobalConfig() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.configLoading = true
          generatorApi
            .saveGlobalConfig(this.form)
            .then(res => {
              this.configLoading = false
              if (res.code === Constant.SUCCESS) {
                this.$notify({ title: '保存成功', type: 'success' })
              } else {
                this.$notify({ title: res.message, type: 'error' })
              }
            })
            .catch(err => {
              this.configLoading = false
              this.$notify({ title: err, type: 'error' })
            })
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
