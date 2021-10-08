<template>
  <el-dialog append-to-body :visible.sync="showDeptUserForm" :before-close="handleClose" title="测试" width="70%">
    <div>
      <div class="dept-user-form-transfer" style="text-align: center">
        <el-transfer
          v-model="value"
          style="text-align: left; display: inline-block; width: 800px; height: 600px"
          filterable
          :left-default-checked="[0]"
          :props="{ key: 'id', label: 'nickname' }"
          :titles="['Source', 'Target']"
          :format="{
            noChecked: '${total}',
            hasChecked: '${checked}/${total}'
          }"
          :data="users"
          @change="handleChange"
        >
          <!--          <span slot-scope="{ option }">{{ option.key }} - {{ option.label }}</span>-->
          <el-button slot="left-footer" class="transfer-footer" size="small">操作</el-button>
          <el-button slot="right-footer" class="transfer-footer" size="small">操作</el-button>
        </el-transfer>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleClose">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import UserApi from '@/api/cas/user'

export default {
  name: 'DeptUserForm',
  data() {
    return {
      showDeptUserForm: false,
      users: [],
      value: [],
      form: null
    }
  },
  mounted() {
    UserApi.list().then(data => {
      this.users = data
      console.info('users', this.users)
    })
  },
  methods: {
    handleChange(value, direction, movedKeys) {
      console.log(value, direction, movedKeys)
    },
    handleOpen() {
      this.showDeptUserForm = true
    },
    handleClose() {
      this.showDeptUserForm = false
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-transfer-panel {
  width: 300px;
  height: 500px;
}
</style>
