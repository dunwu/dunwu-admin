<template>
  <el-tabs v-model="activeName" style="padding-left: 8px;" @tab-click="tabClick">
    <el-tab-pane label="本地存储" name="local">
      <LocalStorage ref="local" />
    </el-tab-pane>
    <el-tab-pane label="DB存储" name="db">
      <DbStorage ref="db" />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import LocalStorage from './LocalStorage'
import DbStorage from './DbStorage'

export default {
  name: 'Storage',
  components: { DbStorage, LocalStorage },
  data() {
    return {
      activeName: 'local'
    }
  },
  methods: {
    tabClick(name) {
      if (this.activeName === 'local') {
        this.$refs.local.crud.toQuery()
      } else {
        this.$refs.db.crud.toQuery()
      }
    }
  }
}
</script>

<style scoped></style>
