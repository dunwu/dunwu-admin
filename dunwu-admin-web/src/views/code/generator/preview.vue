<template>
  <el-tabs v-model="activeName" type="card">
    <el-tab-pane v-for="item in data" :key="item.name" :lazy="true" :label="item.name" :name="item.name">
      <Java :value="item.content" :height="height" />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Java from '@/components/JavaEdit/index'
import codeApi from '@/api/code/codeApi'
export default {
  name: 'Preview',
  components: { Java },
  data() {
    return {
      data: null,
      info: {
        dbId: null,
        schemaName: null,
        tableName: null,
        createBy: null
      },
      height: '',
      activeName: 'Entity'
    }
  },
  created() {
    this.height = document.documentElement.clientHeight - 180 + 'px'
    // 根据 router、store 获取页面必要属性
    this.info.dbId = this.$route.params.dbId
    this.info.schemaName = this.$route.params.schemaName
    this.info.tableName = this.$route.params.tableName
    // console.info('this.$route.params', this.$route.params)
    if (this.$store.state.user) {
      if (this.$store.state.user.user) {
        this.info.createBy = this.$store.state.user.user.username
      }
    } else {
      this.info.createBy = 'admin'
    }
    codeApi
      .previewCode(this.info)
      .then(data => {
        this.data = data
      })
      .catch(err => {
        console.error('预览代码失败', err)
        this.$router.go(-1)
      })
  }
}
</script>
