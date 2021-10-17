<template>
  <el-dialog
    append-to-body
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="120px">
      <el-form-item label="菜单类型" prop="menuType">
        <el-radio-group v-model="form.menuType" size="mini" style="width: 450px">
          <el-radio-button label="0">目录</el-radio-button>
          <el-radio-button label="1">菜单</el-radio-button>
          <el-radio-button label="2">按钮</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="form.code" placeholder="编码" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="名称" style="width: 450px;" />
      </el-form-item>
      <el-form-item v-if="form.menuType.toString() !== '0'" label="权限表达式" prop="expression">
        <el-input v-model="form.expression" :disabled="form.frame" placeholder="权限标识" style="width: 450px;" />
      </el-form-item>
      <el-form-item v-show="form.menuType.toString() !== '2'" label="菜单图标" prop="icon">
        <el-popover placement="bottom-start" trigger="click" @show="$refs['iconSelect'].reset()">
          <IconSelect ref="iconSelect" @selected="selected" />
          <el-input slot="reference" v-model="form.icon" style="width: 450px;" placeholder="点击选择图标" readonly>
            <svg-icon
              v-if="form.icon"
              slot="prefix"
              :icon-class="form.icon"
              class="el-input__icon"
              style="height: 32px; width: 16px;"
            />
            <i v-else slot="prefix" class="el-icon-search el-input__icon" />
          </el-input>
        </el-popover>
      </el-form-item>
      <el-form-item v-show="form.menuType.toString() !== '2'" label="外链菜单" prop="frame">
        <el-radio-group v-model="form.frame" size="mini">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-show="form.menuType.toString() === '1'" label="菜单缓存" prop="cached">
        <el-radio-group v-model="form.cached" size="mini">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-show="form.menuType.toString() !== '2'" label="菜单可见" prop="hidden">
        <el-radio-group v-model="form.hidden" size="mini">
          <el-radio-button label="false">是</el-radio-button>
          <el-radio-button label="true">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.menuType.toString() !== '2'" label="路由地址" prop="path">
        <el-input v-model="form.path" placeholder="路由地址" style="width: 450px;" />
      </el-form-item>
      <el-form-item label="菜单排序" prop="sequence">
        <el-input-number
          v-model.number="form.sequence"
          :min="0"
          :max="999"
          controls-position="right"
          style="width: 450px;"
        />
      </el-form-item>
      <el-form-item v-if="!form.frame && form.menuType.toString() === '1'" label="组件路径" prop="component">
        <el-input v-model="form.component" style="width: 450px;" placeholder="组件路径" />
      </el-form-item>
      <el-form-item label="上级类目" prop="pid">
        <treeselect
          v-model="form.pid"
          :options="menus"
          :load-options="treeListByPid"
          style="width: 450px"
          placeholder="选择上级类目"
          :auto-load-root-options="true"
        />
      </el-form-item>
      <el-form-item label="是否禁用" prop="disabled">
        <el-radio-group v-model="form.disabled" size="mini">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input
          v-model="form.note"
          style="width: 450px;"
          placeholder="请输入备注"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10 }"
          maxlength="1000"
          show-word-limit
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import CRUD, { crud, form } from '@crud/crud'
import MenuApi from '@/api/cas/menu'
import IconSelect from '@/components/IconSelect'
import Treeselect, { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

/**
 * 表单默认值
 */
const defaultForm = {
  id: null,
  pid: 0,
  childrenNum: 0,
  code: null,
  name: null,
  menuType: 1,
  expression: null,
  component: null,
  sequence: 1,
  icon: null,
  path: null,
  frame: false,
  cached: false,
  hidden: false,
  note: null,
  disabled: false,
  roles: []
}
export default {
  name: 'MenuForm',
  components: { Treeselect, IconSelect },
  mixins: [form(defaultForm), crud()],
  cruds() {
    return CRUD({
      title: '菜单',
      url: 'cas/menu',
      tableType: 'tree',
      crudMethod: { ...MenuApi }
    })
  },
  props: {
    permission: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      menus: [],
      /**
       * 校验规则
       * @see https://github.com/yiminghe/async-validator
       */
      rules: {
        code: [{ required: true, trigger: 'blur', message: '编码不能为空' }],
        name: [{ required: true, trigger: 'blur', message: '名称不能为空' }],
        menuType: [{ required: true, trigger: 'blur', message: '菜单类型不能为空' }],
        expression: [{ required: true, trigger: 'blur', message: '权限表达式不能为空' }],
        component: [{ required: true, trigger: 'blur', message: '组件不能为空' }],
        icon: [{ required: true, trigger: 'blur', message: '图标不能为空' }],
        path: [{ required: true, trigger: 'blur', message: '链接地址不能为空' }],
        frame: [{ required: true, trigger: 'blur', message: '是否外链不能为空' }],
        cached: [{ required: true, trigger: 'blur', message: '缓存不能为空' }],
        hidden: [{ required: true, trigger: 'blur', message: '隐藏不能为空' }]
      }
    }
  },
  methods: {
    /**
     * 添加与编辑前做的操作
     */
    [CRUD.HOOK.afterToCU](crud, form) {
      this.menus = []
      if (form.id != null) {
        // 编辑窗口
        this.getSuperiorTreeList(form.pid)
      } else {
        // 添加窗口
        this.menus.push({ id: 0, label: '顶级类目', hasChildren: true, children: null })
      }
    },
    getSuperiorTreeList(pid) {
      MenuApi.superiorTreeList(pid).then(res => {
        const children = res.map(function(obj) {
          return obj
        })
        this.menus.push({ id: 0, label: '顶级类目', hasChildren: true, children })
        console.info('getSuperiorTreeList menus', this.menus)
      })
    },
    /**
     * 根据 pid 查 treeList
     */
    treeListByPid({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        MenuApi.treeList({ pid: parentNode.id !== 0 ? parentNode.id : null }).then(res => {
          parentNode.children = res.map(function(obj) {
            return obj
          })
          setTimeout(() => {
            callback()
          }, 100)
        })
      }
    },
    // 选中图标
    selected(name) {
      this.form.icon = name
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
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
