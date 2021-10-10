<template>
  <el-descriptions v-if="dept" title="基本信息" :column="2" border>
    <el-descriptions-item>
      <template slot="label">
        ID
      </template>
      {{ dept.id }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        部门名称
      </template>
      {{ dept.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        部门等级
      </template>
      {{ dept.label }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        更新者
      </template>
      {{ dept.updateBy }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        更新时间
      </template>
      {{ dept.updateTime }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        备注
      </template>
      {{ dept.note }}
    </el-descriptions-item>
  </el-descriptions>
  <el-empty v-else description="数据为空" />
</template>

<script>
import DeptApi from '@/api/cas/dept'

export default {
  name: 'DeptDetail',
  props: {
    deptId: {
      type: Number,
      required: true,
      default: () => {
        return null
      }
    }
  },
  data() {
    return {
      dept: null
    }
  },
  watch: {
    deptId(val) {
      this.refreshByDeptId(val)
    }
  },
  mounted() {
    this.refreshByDeptId(this.deptId)
  },
  methods: {
    refreshByDeptId(deptId) {
      DeptApi.getById(deptId)
        .then(data => {
          this.dept = data
        })
        .catch(() => {
          this.$message({ type: 'error', message: '查询 id = ' + deptId + ' 的部门信息失败' })
        })
    }
  }
}
</script>
