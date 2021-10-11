<template>
  <el-descriptions v-if="user" title="基本信息" :column="2" border>
    <el-descriptions-item>
      <template slot="label">
        ID
      </template>
      {{ user.id }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        用户名
      </template>
      {{ user.username }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        昵称
      </template>
      {{ user.nickname }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        性别
      </template>
      {{ user.gender }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        手机号码
      </template>
      {{ user.phone }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        邮箱
      </template>
      {{ user.email }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        部门
      </template>
      {{ user.dept.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        职务
      </template>
      {{ user.job.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        备注
      </template>
      {{ user.note }}
    </el-descriptions-item>
  </el-descriptions>
  <el-empty v-else description="数据为空" />
</template>

<script>
import UserApi from '@/api/cas/user'

export default {
  name: 'DeptDetail',
  props: {
    userId: {
      type: Number,
      required: true,
      default: () => {
        return null
      }
    }
  },
  data() {
    return {
      user: null
    }
  },
  watch: {
    userId(val) {
      this.refreshById(val)
    }
  },
  mounted() {
    this.refreshById(this.userId)
  },
  methods: {
    refreshById(id) {
      UserApi.getById(id)
        .then(data => {
          this.user = data
        })
        .catch(() => {
          this.$message({ type: 'error', message: '查询 id = ' + id + ' 的信息失败' })
        })
    }
  }
}
</script>
