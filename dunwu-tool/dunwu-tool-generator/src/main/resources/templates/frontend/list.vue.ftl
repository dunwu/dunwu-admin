<#--noinspection ALL-->
<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
<#if table.enableQuery>
      <div v-if="crud.props.searchToggle">
        <el-row>
  <#list table.queryFields as field>
    <#if field.queryType!='BETWEEN'>
          <el-col :span="6">
            <el-input
                v-model="query.${field.propertyName}"
                clearable
                placeholder="请输入<#if field.comment != ''>${field.comment}<#else>${field.propertyName}</#if>"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
            />
          </el-col>
    <#else>

      <#if (field.javaType == "Date") || (field.javaType == "LocalDate") || field.javaType == "LocalDateTime">
          <el-col :span="6">
            <date-range-picker
                v-model="query.${field.propertyName}Range"
                class="date-item"
                style="width: 90%"
            />
          </el-col>
      <#else>
      </#if>
    </#if>
  </#list>
  <#if table.queryExtFields??>
    <#list table.queryExtFields as field>
          <template v-if="crud.showExtendSearch">
      <#if field.queryType!='BETWEEN'>
            <el-col :span="6">
              <el-input
                  v-model="query.${field.propertyName}"
                  clearable
                  placeholder="请输入<#if field.comment != ''>${field.comment}<#else>${field.propertyName}</#if>"
                  style="width: 90%;"
                  class="filter-item"
                  @keyup.enter.native="crud.toQuery"
              />
            </el-col>
      <#else>
        <#if (field.javaType == "Date") || (field.javaType == "LocalDate") || field.javaType == "LocalDateTime">
            <el-col :span="6">
              <date-range-picker
                  v-model="query.${field.propertyName}Range"
                  class="date-item"
                  style="width: 90%"
              />
            </el-col>
        <#else>
        </#if>
      </#if>
          </template>
    </#list>
  </#if>
          <el-col :span="6">
            <queryOperation :crud="crud" />
            <el-button v-if="crud.showExtendSearch" type="text" @click="crud.toggleExtendSearch">
              折叠
              <i class="el-icon-arrow-up el-icon--right" />
            </el-button>
            <el-button v-else type="text" @click="crud.toggleExtendSearch">
              展开
              <i class="el-icon-arrow-down el-icon--right" />
            </el-button>
          </el-col>
        </el-row>
      </div>
  <#if table.enablePermission>
      <crudOperation :permission="permission" />
  <#else>
      <crudOperation />
  </#if>
</#if>
    </div>

<#if table.enableForm>
    <!--表单组件-->
    <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
      <el-form ref="form" :model="form"<#if table.enableValidate> :rules="rules"</#if> size="small" label-width="80px">
  <#list table.fields as field>
    <#if field.enableForm>
        <el-form-item label="<#if field.comment != ''>${field.comment}<#else>${field.propertyName}</#if>"<#if field.notNull> prop="${field.propertyName}"</#if>>
      <#if field.formType = 'Input'>
          <el-input v-model="form.${field.propertyName}" style="width: 370px;" />
      <#elseif field.formType = 'Textarea'>
          <el-input v-model="form.${field.propertyName}" :rows="3" type="textarea" style="width: 370px;" />
      <#elseif field.formType = 'Radio'>
        <#if (field.dictName)?? && (field.dictName)!="">
          <el-radio v-model="form.${field.propertyName}" v-for="item in dict.${field.dictName}" :key="item.id" :label="item.value">{{ item.label }}</el-radio>
        <#else>
                未设置字典，请手动设置 Radio
        </#if>
      <#elseif field.formType = 'Select'>
        <#if (field.dictName)?? && (field.dictName)!="">
          <el-select v-model="form.${field.propertyName}" filterable placeholder="请选择">
            <el-option
              v-for="item in dict.${field.dictName}"
              :key="item.id"
              :label="item.label"
              :value="item.value" />
          </el-select>
        <#else>
            未设置字典，请手动设置 Select
        </#if>
      <#else>
          <el-date-picker v-model="form.${field.propertyName}" type="datetime" style="width: 370px;" />
      </#if>
        </el-form-item>
    </#if>
  </#list>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
</#if>

      <!--表格渲染-->
<#if table.enableList>
    <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
      <el-table-column type="selection" width="55" />
  <#list table.fields as field>
    <#if field.enableList>
      <#if (field.dictName)?? && (field.dictName)!="">
        <el-table-column prop="${field.propertyName}" label="<#if field.comment != ''>${field.comment}<#else>${field.propertyName}</#if>">
          <template slot-scope="scope">
            {{ dict.label.${field.dictName}[scope.row.${field.propertyName}] }}
          </template>
        </el-table-column>
      <#else>
        <el-table-column prop="${field.propertyName}" label="<#if field.comment != ''>${field.comment}<#else>${field.propertyName}</#if>" />
      </#if>
    </#if>
  </#list>
  <#if table.enablePermission>
        <el-table-column v-if="checkPer(['admin','<#if package.ModuleName??>${package.ModuleName}</#if>:${table.entityPath}:edit','<#if package.ModuleName??>${package.ModuleName}</#if>:${table.entityPath}:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation :data="scope.row" :permission="permission" />
          </template>
        </el-table-column>
  <#else>
        <el-table-column label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation :data="scope.row" />
          </template>
        </el-table-column>
  </#if>
      </el-table>
</#if>

    <!--分页组件-->
    <pagination />
  </div>
</template>

<script>
import ${table.apiName} from './${table.apiName}'
import CRUD, {crud, form, header, presenter} from '@crud/crud'
import queryOperation from '@crud/Query.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'

const defaultForm = {<#if table.fields??><#list table.fields as field>${field.fieldName}: null, </#list></#if>}
export default {
  name: '${table.entityPath}',
  components: { pagination, crudOperation, queryOperation, udOperation, DateRangePicker },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '${table.comment!}',
      url: 'api<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>',
      sort: 'id,asc',
      crudMethod: { ...${table.apiName} }
    })
  },
  data() {
    return {
      <#if table.enablePermission>permission: {
        add: ['admin', '<#if package.ModuleName??>${package.ModuleName}</#if>:${table.entityPath}:add'],
        edit: ['admin', '<#if package.ModuleName??>${package.ModuleName}</#if>:${table.entityPath}:edit'],
        del: ['admin', '<#if package.ModuleName??>${package.ModuleName}</#if>:${table.entityPath}:del']
      }, </#if><#if table.enableValidate>rules: {
<#list table.fields as field>
  <#if field.enableValidate>
        ${field.propertyName}: [
    <#if field.validateType??>
      <#if field.validateType == 'number'>
          { required: true, message: '<#if field.comment != ''>${field.comment}</#if>必须为数字', trigger: 'blur', type: 'number' }
      <#else>
          { required: true, message: '<#if field.comment != ''>${field.comment}</#if>不能为空', trigger: 'blur' }
      </#if>
    <#else>
          { required: true, message: '<#if field.comment != ''>${field.comment}</#if>不能为空', trigger: 'blur' }
    </#if>
        ],
  </#if>
</#list>
      }</#if>
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped></style>
