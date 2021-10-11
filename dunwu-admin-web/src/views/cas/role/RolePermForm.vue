<template>
  <el-card class="box-card" shadow="never">
    <div slot="header" class="clearfix">
      <el-tooltip class="item" effect="dark" content="选择指定角色分配菜单" placement="top">
        <span class="role-span">菜单分配</span>
      </el-tooltip>
      <el-button
        :disabled="!showButton"
        :loading="menuLoading"
        icon="el-icon-check"
        size="mini"
        style="float: right; padding: 6px 9px"
        type="primary"
        @click="saveMenu"
      >
        保存
      </el-button>
    </div>
    <el-tree
      ref="menu"
      lazy
      :data="menus"
      :default-checked-keys="menuIds"
      :load="getMenuDatas"
      :props="defaultProps"
      check-strictly
      accordion
      show-checkbox
      node-key="id"
      @check="menuChange"
    />
  </el-card>
</template>

<script>
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import RoleApi from '@/api/cas/role'
import MenuApi from '@/api/cas/menu'

export default {
  name: 'RolePermForm',
  components: {},
  props: {
    role: {
      type: Object,
      required: false,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      defaultProps: { children: 'children', label: 'label', isLeaf: 'leaf' },
      roleId: null,
      menuLoading: false,
      showButton: false,
      menus: [],
      menuIds: [],
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        permission: [{ required: true, message: '请输入权限', trigger: 'blur' }]
      }
    }
  },
  watch: {
    role(value) {
      const that = this
      setTimeout(() => {
        MenuApi.treeList().then(res => {
          that.menuIds.splice(0, this.menuIds.length)
          value.menus.forEach(function(data) {
            that.menuIds.push(data.id)
          })
          that.$refs.menu.setCheckedKeys([])
        })
      }, 100)
    }
  },
  mounted() {
    console.info('role', this.role)
  },
  methods: {
    getMenuDatas(node, resolve) {
      const that = this
      setTimeout(() => {
        MenuApi.treeList({ pid: node.data.id }).then(res => {
          resolve(res)

          that.menuIds.splice(0, this.menuIds.length)
          that.role.menus.forEach(function(data) {
            that.menuIds.push(data.id)
          })
          that.$refs.menu.setCheckedKeys([])
        })
      }, 100)
    },
    menuChange(menu) {
      this.showButton = true
      // 获取该节点的所有子节点，id 包含自身
      MenuApi.childrenIds(menu.id).then(ids => {
        // 判断是否在 menuIds 中，如果存在则删除，否则添加
        if (this.menuIds.indexOf(menu.id) !== -1) {
          for (let i = 0; i < ids.length; i++) {
            const index = this.menuIds.indexOf(ids[i])
            if (index !== -1) {
              this.menuIds.splice(index, 1)
            }
          }
        } else {
          for (let i = 0; i < ids.length; i++) {
            const index = this.menuIds.indexOf(ids[i])
            if (index === -1) {
              this.menuIds.push(ids[i])
            }
          }
        }
        this.$refs.menu.setCheckedKeys(this.menuIds)
      })
    },
    // 保存菜单
    saveMenu() {
      this.menuLoading = true
      const role = { id: this.role.id, menus: [] }
      // 得到已选中的 key 值
      this.menuIds.forEach(function(id) {
        const menu = { id: id }
        role.menus.push(menu)
      })
      RoleApi.editMenu(role)
        .then(() => {
          this.$message({ message: '保存成功', type: 'success' })
          this.menuLoading = false
        })
        .catch(err => {
          this.$message({ message: err, type: 'error' })
          this.menuLoading = false
        })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.role-span {
  font-weight: bold;
  color: #303133;
  font-size: 15px;
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
::v-deep .vue-treeselect__multi-value {
  margin-bottom: 0;
}
::v-deep .vue-treeselect__multi-value-item {
  border: 0;
  padding: 0;
}
</style>
