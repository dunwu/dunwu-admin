<template>
  <el-descriptions v-if="detail" title="基本信息" :column="2" border>
    <el-descriptions-item>
      <template slot="label">
        ID
      </template>
      {{ detail.id }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        用户名
      </template>
      {{ detail.username }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        昵称
      </template>
      {{ detail.nickname }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        性别
      </template>
      {{ detail.gender }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        手机号码
      </template>
      {{ detail.phone }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        邮箱
      </template>
      {{ detail.email }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        部门
      </template>
      {{ detail.dept.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        职务
      </template>
      {{ detail.job.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        备注
      </template>
      {{ detail.note }}
    </el-descriptions-item>
  </el-descriptions>
  <el-empty v-else description="数据为空" />
</template>

<script>
import UserApi from '@/api/cas/user'

export default {
  name: 'UserDetail',
  props: {
    id: {
      type: Number,
      required: true,
      default: () => {
        return null
      }
    }
  },
  data() {
    return {
      detail: null
    }
  },
  watch: {
    id(val) {
      this.refreshById(val)
    }
  },
  mounted() {
    this.refreshById(this.id)
  },
  methods: {
    refreshById(id) {
      UserApi.getById(id)
        .then(data => {
          this.detail = data
        })
        .catch(() => {
          this.$message({ type: 'error', message: '查询 id = ' + id + ' 的信息失败' })
        })
    }
  }
}
</script>
