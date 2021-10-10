<template>
  <el-card shadow="never">
    <div slot="header" class="clearfix">
      <span class="role-span">
        <el-tag type="info">{{ tableName }}</el-tag>
        表级别配置
      </span>
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
    <el-form ref="form" v-loading="loading" :model="form" :rules="rules" size="small" label-width="150px">
      <el-form-item label="开启权限校验" prop="enablePermission">
        <el-radio-group v-model="form.enablePermission" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="开启文件覆盖模式" prop="enableOverride">
        <el-radio-group v-model="form.enableOverride" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
          <span style="color: #C0C0C0;margin-left: 10px;">生成文件如果存在，则覆盖文件</span>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="开启Swagger2" prop="enableSwagger">
        <el-radio-group v-model="form.enableSwagger" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
          <span style="color: #C0C0C0;margin-left: 10px;">是否开启 Swagger2</span>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="form.author" style="width: 40%" />
        <span style="color: #C0C0C0;margin-left: 10px;">生成代码 javadoc 中的作者名称</span>
      </el-form-item>
      <el-form-item label="输出路径" prop="outputDir">
        <el-input v-model="form.outputDir" style="width: 40%" />
        <span style="color: #C0C0C0;margin-left: 10px;">
          代码生成根路径，如果没有指定后端代码路径和前端代码路径时有效
        </span>
      </el-form-item>
      <el-form-item label="后端代码路径" prop="backendPath">
        <el-input v-model="form.backendPath" style="width: 40%" />
        <span style="color: #C0C0C0;margin-left: 10px;">输入后端模块文件夹下的目录，不存在即创建</span>
      </el-form-item>
      <el-form-item label="前端代码路径" prop="frontendPath">
        <el-input v-model="form.frontendPath" style="width: 40%" />
        <span style="color: #C0C0C0;margin-left: 10px;">输入views文件夹下的目录，不存在即创建</span>
      </el-form-item>
      <el-form-item label="包路径" prop="packagePath">
        <el-input v-model="form.packagePath" style="width: 40%" />
        <span style="color: #C0C0C0;margin-left: 10px;">项目包的名称，生成的代码放到哪个包里面</span>
      </el-form-item>
      <el-form-item label="主键类型" prop="idType">
        <el-select v-model="form.idType" class="edit-input" style="width: 40%" placeholder="请选择">
          <el-option label="无" value="NONE" />
          <el-option label="自增主键" value="AUTO" />
          <el-option label="用户输入" value="INPUT" />
          <el-option label="自动输入" value="ASSIGN_ID" />
          <el-option label="自动输入UUID" value="ASSIGN_UUID" />
        </el-select>
      </el-form-item>
      <el-form-item label="时间类型" prop="dateType">
        <el-select v-model="form.dateType" class="edit-input" style="width: 40%" placeholder="请选择">
          <el-option label="无" value="null" />
          <el-option label="java.util.date 类型" value="ONLY_DATE" />
          <el-option label="java.sql 类型" value="SQL_PACK" />
          <el-option label="java.time 类型" value="TIME_PACK" />
        </el-select>
      </el-form-item>
      <el-form-item label="时间格式化" prop="datePattern">
        <el-input v-model="form.datePattern" style="width: 40%" />
      </el-form-item>
      <el-form-item label="允许列表" prop="enableList">
        <el-radio-group v-model="form.enableList" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="允许表单" prop="enableForm">
        <el-radio-group v-model="form.enableForm" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="允许查询" prop="enableQuery">
        <el-radio-group v-model="form.enableQuery" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="允许排序" prop="enableSort">
        <el-radio-group v-model="form.enableSort" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="允许校验" prop="enableValidate">
        <el-radio-group v-model="form.enableValidate" size="mini" style="width: 40%">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="模块名称" prop="moduleName">
        <el-input v-model="form.moduleName" style="width: 40%" />
      </el-form-item>
      <el-form-item label="REST接口根路径" prop="apiBaseUrl">
        <el-input v-model="form.apiBaseUrl" style="width: 40%" />
      </el-form-item>
      <el-form-item label="表前缀" prop="tablePrefix">
        <el-input v-model="form.tablePrefix" style="width: 40%" />
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import codeApi from '@/api/code/codeApi'
import databaseApi from '@/api/code/databaseApi'
export default {
  name: 'TableConfig',
  components: {},
  data() {
    return {
      loading: false,
      databaseLoading: false,
      configLoading: false,
      dbId: null,
      schemaName: '',
      tableName: '',
      createBy: '',
      tableHeight: 550,
      database: null,
      form: {
        id: null,
        dbId: null,
        schemaName: null,
        tableName: null,
        enablePermission: false,
        enableOverride: false,
        enableSwagger: false,
        author: null,
        outputDir: null,
        backendPath: null,
        frontendPath: null,
        packagePath: null,
        idType: null,
        dateType: null,
        datePattern: null,
        enableQuery: true,
        enableList: true,
        enableForm: true,
        enableSort: true,
        enableValidate: true,
        moduleName: null,
        tablePrefix: null,
        apiBaseUrl: null
      },
      rules: {
        author: [{ required: true, message: '作者不能为空', trigger: 'blur' }],
        moduleName: [{ required: true, message: '模块名不能为空', trigger: 'blur' }],
        packagePath: [{ required: true, message: '包路径不能为空', trigger: 'blur' }],
        outputDir: [{ required: true, message: '前端路径不能为空', trigger: 'blur' }],
        apiBaseUrl: [{ required: true, message: '接口名称不能为空', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.tableHeight = document.documentElement.clientHeight - 385

    // 根据 router、store 获取页面必要属性
    this.dbId = this.$route.params.dbId
    this.tableName = this.$route.params.tableName
    this.schemaName = this.$route.params.schemaName
    if (this.$store.state.user) {
      if (this.$store.state.user.user) {
        this.createBy = this.$store.state.user.user.username
      }
    } else {
      this.createBy = 'admin'
    }

    this.$nextTick(() => {
      this.findDatabase()
      this.queryTableConfig()
    })
  },
  methods: {
    findDatabase() {
      this.databaseLoading = false
      databaseApi
        .getById(this.dbId)
        .then(data => {
          this.database = data
          this.databaseLoading = true
        })
        .catch(err => {
          this.loading = false
          console.error('查询数据库失败', err)
        })
    },
    queryTableConfig() {
      this.loading = true
      codeApi
        .queryTableConfig({
          dbId: this.dbId,
          schemaName: this.schemaName,
          tableName: this.tableName,
          createBy: this.createBy
        })
        .then(data => {
          this.loading = false
          this.form = data
        })
        .catch(err => {
          this.loading = false
          console.error(err.response.data.msg)
        })
    },
    saveTableConfig() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.configLoading = true
          this.form.dbId = this.dbId
          codeApi
            .saveTableConfig(this.form)
            .then(res => {
              this.configLoading = false
              this.$notify({ title: '保存成功', type: 'success' })
              this.queryTableConfig()
            })
            .catch(err => {
              this.configLoading = false
              console.error('保存失败', err.response.data.msg)
            })
        }
      })
    }
  }
}
</script>

<style scoped></style>
