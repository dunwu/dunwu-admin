<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="应用编号">
        <el-input v-model="form.appId" style="width: 90%" placeholder="请输入应用编号" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.note" style="width: 90%" placeholder="请输入备注" />
      </el-form-item>
      <el-form-item label="创建者">
        <el-input v-model="form.createBy" style="width: 90%" placeholder="请输入创建者" />
      </el-form-item>
      <el-form-item label="更新者">
        <el-input v-model="form.updateBy" style="width: 90%" placeholder="请输入更新者" />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="form.createTime" style="width: 90%" placeholder="请输入创建时间" />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-input v-model="form.updateTime" style="width: 90%" placeholder="请输入更新时间" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { form } from '@crud/crud'

/**
 * 表单实体默认值
 */
const defaultForm = { appId: null, note: null, createBy: null, updateBy: null, createTime: null, updateTime: null, }
export default {
  name: 'DeployForm',
  mixins: [form(defaultForm)],
  data() {
    return {
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
      }
    }
  }
}
</script>

<style scoped></style>
