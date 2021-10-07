<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--侧边部门数据-->
      <el-col :xs="9" :sm="6" :md="5" :lg="4" :xl="4">
        <div class="head-container">
          <el-input
            v-model="deptName"
            clearable
            size="small"
            placeholder="输入部门名称搜索"
            prefix-icon="el-icon-search"
            class="filter-item"
            @input="getDeptDatas"
          />
        </div>
        <el-tree
          :data="deptDatas"
          :load="getDeptDatas"
          :props="defaultProps"
          :expand-on-click-node="false"
          lazy
          @node-click="handleNodeClick"
        />
      </el-col>
      <!--用户数据-->
      <el-col :xs="15" :sm="18" :md="19" :lg="20" :xl="20">
        <UserList ref="userList" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import deptApi from '@/api/cas/dept'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import UserList from './UserList'

export default {
  name: 'User',
  components: { UserList },
  data() {
    return {
      height: document.documentElement.clientHeight - 180 + 'px;',
      deptName: '',
      depts: [],
      deptDatas: [],
      defaultProps: { children: 'children', label: 'name', isLeaf: 'leaf' },
      level: 3
    }
  },
  mounted: function() {
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 180 + 'px;'
    }
  },
  methods: {
    // 获取左侧部门数据
    getDeptDatas(node, resolve) {
      const sort = 'id,desc'
      const params = { sort: sort }
      if (typeof node !== 'object') {
        if (node) {
          params['name'] = node
        }
      } else if (node.level !== 0) {
        params['pid'] = node.data.id
      }
      setTimeout(() => {
        deptApi.treeList(params).then(res => {
          if (resolve) {
            resolve(res)
          } else {
            this.deptDatas = res
          }
        })
      }, 100)
    },
    getDepts() {
      deptApi.treeList({ disabled: true }).then(res => {
        this.depts = res.map(function(obj) {
          if (obj.hasChildren) {
            obj.children = null
          }
          return obj
        })
      })
    },
    getSupDepts(deptId) {
      deptApi.superiorTreeList(deptId).then(res => {
        const data = res
        this.buildDepts(data)
        this.depts = data
      })
    },
    buildDepts(depts) {
      depts.forEach(data => {
        if (data.children) {
          this.buildDepts(data.children)
        }
        if (data.hasChildren && !data.children) {
          data.children = null
        }
      })
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        deptApi.treeList({ disabled: true, pid: parentNode.id }).then(res => {
          parentNode.children = res.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 200)
        })
      }
    },
    // 切换部门
    handleNodeClick(data) {
      // if (data.pid === 0) {
      //   this.query.deptId = null
      // } else {
      //   this.query.deptId = data.id
      // }
      // this.crud.toQuery()
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .vue-treeselect__control,
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  height: 30px;
  line-height: 30px;
}
</style>
