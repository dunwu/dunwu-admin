<template>
  <div class="app-container">
    <UserPage ref="userPage" />
  </div>
</template>

<script>
import UserPage from './UserPage'

export default {
  name: 'User',
  components: { UserPage },
  data() {
    return {
      height: document.documentElement.clientHeight - 180 + 'px;'
    }
  },
  mounted: function() {
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 180 + 'px;'
    }
  },
  methods: {}
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped></style>
