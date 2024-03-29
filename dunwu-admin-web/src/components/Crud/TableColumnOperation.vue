<!--
  - 表格行公共操作按钮组合，包含 [ 编辑、删除 ] 按钮。
  -->

<template>
  <div>
    <!--执行权限校验-->
    <div v-if="crud.enablePermission">
      <slot name="left" />
      <!--列表行记录操作按钮 - 编辑-->
      <el-button
        v-permission="permission.edit"
        :loading="crud.status.cu === 2"
        :disabled="disabledEdit"
        class="filter-item"
        size="mini"
        type="text"
        @click="crud.toEdit(data)"
      >
        编辑
      </el-button>
      <el-divider direction="vertical" />
      <!--列表行记录操作按钮 - 删除-->
      <el-popover
        v-model="pop"
        v-permission="permission.del"
        placement="top"
        width="180"
        trigger="manual"
        @show="doPopoverShow"
        @hide="doPopoverHide"
      >
        <p>{{ msg }}</p>
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="doCancel">取消</el-button>
          <el-button
            :loading="crud.dataStatus[crud.getDataId(data)].delete === 2"
            type="primary"
            size="mini"
            @click="crud.doDelete(data)"
          >
            确定
          </el-button>
        </div>
        <el-button
          slot="reference"
          class="filter-item"
          :disabled="disabledDle"
          size="mini"
          type="text"
          @click="doDelete"
        >
          删除
        </el-button>
      </el-popover>

      <slot name="right" />
    </div>
    <!--不执行权限校验-->
    <div v-else>
      <slot name="left" />
      <!--列表行记录操作按钮 - 编辑-->
      <el-button
        :loading="crud.status.cu === 2"
        :disabled="disabledEdit"
        class="filter-item"
        size="mini"
        type="text"
        @click="crud.toEdit(data)"
      >
        编辑
      </el-button>
      <el-divider direction="vertical" />
      <!--列表行记录操作按钮 - 删除-->
      <el-popover
        v-model="pop"
        placement="top"
        width="180"
        trigger="manual"
        @show="doPopoverShow"
        @hide="doPopoverHide"
      >
        <p>{{ msg }}</p>
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="doCancel">取消</el-button>
          <el-button
            :loading="crud.dataStatus[crud.getDataId(data)].delete === 2"
            type="primary"
            size="mini"
            @click="crud.doDelete(data)"
          >
            确定
          </el-button>
        </div>
        <el-button
          slot="reference"
          class="filter-item"
          :disabled="disabledDle"
          size="mini"
          type="text"
          @click="doDelete"
        >
          删除
        </el-button>
      </el-popover>

      <slot name="right" />
    </div>
  </div>
</template>

<script>
import CRUD, { crud } from '@crud/crud'
export default {
  mixins: [crud()],
  props: {
    data: {
      type: Object,
      required: true
    },
    permission: {
      type: Object,
      default: () => {
        return {}
      }
    },
    disabledEdit: {
      type: Boolean,
      default: false
    },
    disabledDle: {
      type: Boolean,
      default: false
    },
    msg: {
      type: String,
      default: '确定删除本条数据吗？'
    }
  },
  data() {
    return {
      pop: false
    }
  },
  methods: {
    [CRUD.HOOK.afterDelete](crud, data) {
      if (data === this.data) {
        this.pop = false
      }
    },
    doCancel() {
      this.pop = false
      this.crud.cancelDelete(this.data)
    },
    doDelete() {
      this.pop = true
    },
    doPopoverShow() {
      setTimeout(() => {
        document.addEventListener('click', this.doDocumentClick)
      }, 0)
    },
    doPopoverHide() {
      document.removeEventListener('click', this.doDocumentClick)
    },
    doDocumentClick(event) {
      this.pop = false
    }
  }
}
</script>
