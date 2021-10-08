<template>
  <div>
    <div v-if="data">
      <el-descriptions title="基本信息" :column="2" border>
        <template slot="extra">
          <el-button
            v-if="checkPer(['admin', 'cas:dept:edit', 'cas:dept:del'])"
            type="primary"
            size="small"
            style="display: block; margin-right: 0;"
            @click="openDeptUserForm"
          >
            变更成员
          </el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            ID
          </template>
          {{ data.id }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            部门名称
          </template>
          {{ data.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            部门等级
          </template>
          {{ data.label }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            更新者
          </template>
          {{ data.updateBy }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            更新时间
          </template>
          {{ data.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            备注
          </template>
          {{ data.note }}
        </el-descriptions-item>
      </el-descriptions>
      <div style="margin-top: 20px;">
        <div style="display: flex; align-items: center;">
          <div style="font-size: 16px; font-weight: bold; display: block;">部门成员</div>
        </div>
        <UserPage :dept-id="deptId" />
      </div>
    </div>
    <el-empty v-else description="请选择一个部门" />

    <!--部门用户绑定表单-->
    <DeptUserForm ref="deptUserForm" />
  </div>
</template>

<script>
import UserPage from '@/views/cas/user/UserPage'
import DeptUserForm from './DeptUserForm'

export default {
  name: 'DeptDetail',
  components: { UserPage, DeptUserForm },
  props: {
    dept: {
      type: Object,
      required: false,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      deptId: null,
      data: null
    }
  },
  watch: {
    dept(val) {
      console.info('dept changed', val)
      this.deptId = val.id
      this.data = val
    }
  },
  methods: {
    openDeptUserForm() {
      this.$refs.deptUserForm.handleOpen()
    }
  }
}
</script>
