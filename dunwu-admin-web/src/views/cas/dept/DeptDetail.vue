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
        部门名称
      </template>
      {{ detail.name }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        部门等级
      </template>
      {{ detail.label }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        更新者
      </template>
      {{ detail.updaterName }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        更新时间
      </template>
      {{ detail.updateTime }}
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
import DeptApi from '@/api/cas/dept'

export default {
  name: 'DeptDetail',
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
      DeptApi.getById(id)
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
