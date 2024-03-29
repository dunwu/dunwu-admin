import { downloadFile, parseTime } from '@/utils/index'
import Vue from 'vue'

/**
 * CRUD配置
 * @author moxun
 * @param {*} options <br>
 * @return crud instance.
 * @example
 * 要使用多crud时，请在关联crud的组件处使用crud-tag进行标记，如：<jobForm :job-status="dict['disabled_status']" crud-tag="job" />
 */
function CRUD(options) {
  const defaultOptions = {
    tag: 'default',
    // id字段名
    idField: 'id',
    // 标题
    title: '',
    // 表类型
    tableType: 'page',
    // 请求数据的url
    url: '',
    // 上传请求的url
    importUrl: '',
    // 表格数据
    data: [],
    // 选择项
    selections: [],
    // 待查询的对象
    query: {},
    // 查询数据的参数
    params: {},
    // Form 表单
    form: {},
    // 重置表单
    defaultForm: () => {},
    // 排序规则，默认 id 降序， 支持多字段排序 ['id,desc', 'createTime,asc']
    sort: ['id,desc'],
    // 等待时间
    time: 50,
    // CRUD Method
    crudMethod: {
      add: data => {},
      edit: data => {},
      del: id => {},
      delBatch: ids => {},
      list: params => {},
      treeList: params => {},
      page: params => {},
      getById: id => {},
      exportList: ids => {},
      exportPage: params => {}
    },
    // 主页操作栏显示哪些按钮
    optShow: {
      all: false,
      add: true,
      edit: true,
      del: true,
      import: false,
      export: true,
      query: true,
      reset: true,
      tools: true
    },
    // 自定义一些扩展属性
    props: {},
    // 权限校验
    enablePermission: false,
    // 在主页准备
    queryOnPresenterCreated: true,
    // 调试开关
    debug: false
  }
  options = mergeOptions(defaultOptions, options)
  const data = {
    ...options,
    // 记录数据状态
    dataStatus: {},
    status: {
      add: CRUD.STATUS.NORMAL,
      edit: CRUD.STATUS.NORMAL,
      // 添加或编辑状态
      get cu() {
        if (this.add === CRUD.STATUS.NORMAL && this.edit === CRUD.STATUS.NORMAL) {
          return CRUD.STATUS.NORMAL
        } else if (this.add === CRUD.STATUS.PREPARED || this.edit === CRUD.STATUS.PREPARED) {
          return CRUD.STATUS.PREPARED
        } else if (this.add === CRUD.STATUS.PROCESSING || this.edit === CRUD.STATUS.PROCESSING) {
          return CRUD.STATUS.PROCESSING
        }
        throw new Error("wrong crud's cu status")
      },
      // 标题
      get title() {
        const editTitle = this.edit > CRUD.STATUS.NORMAL ? `编辑${crud.title}` : crud.title
        return this.add > CRUD.STATUS.NORMAL ? `添加${crud.title}` : editTitle
      }
    },
    msg: {
      submit: '提交成功',
      add: '添加成功',
      edit: '编辑成功',
      del: '删除成功'
    },
    page: {
      // 页码
      page: 0,
      // 每页数据条数
      size: 10,
      // 总数据条数
      total: 0
    },
    // 是否展示扩展搜索栏
    showExtendSearch: false,
    // 整体loading
    loading: false,
    // 导出的 Loading
    exportLoading: false,
    // 删除的 Loading
    batchDelLoading: false
  }
  const methods = {
    /**
     * 通用的提示
     */
    submitSuccessNotify() {
      crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, crud.msg.submit)
    },
    addSuccessNotify() {
      crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, crud.msg.add)
    },
    editSuccessNotify() {
      crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, crud.msg.edit)
    },
    delSuccessNotify() {
      crud.message(CRUD.NOTIFICATION_TYPE.SUCCESS, crud.msg.del)
    },
    /**
     * 展开/折叠 扩展搜索栏
     */
    toggleExtendSearch() {
      crud.showExtendSearch = !crud.showExtendSearch
    },
    /**
     * 查询并刷新表数据
     */
    toQuery() {
      crud.page.page = 1
      crud.refresh()
    },
    /**
     * 切换排序类型，并刷新表数据
     * @param val
     */
    changeTableSort(val) {
      // 转换 Element UI 的排序类型
      const order = val.order === 'ascending' ? 'ASC' : 'DESC'
      crud.sort = val.prop + ',' + order
      crud.refresh()
    },
    // 刷新
    refresh() {
      if (!callVmHook(crud, CRUD.HOOK.beforeRefresh)) {
        return
      }
      return new Promise((resolve, reject) => {
        crud.loading = true
        // 请求数据
        if (this.tableType === 'tree') {
          crud.crudMethod
            .treeList(crud.getQueryParams())
            .then(data => {
              const table = crud.getTable()
              if (table && table.lazy) {
                // 懒加载子节点数据，清掉已加载的数据
                table.store.states.treeData = {}
                table.store.states.lazyTreeNodeMap = {}
              }
              crud.data = data
              crud.resetDataStatus()
              // time 毫秒后显示表格
              setTimeout(() => {
                crud.loading = false
                callVmHook(crud, CRUD.HOOK.afterRefresh)
              }, crud.time)
              resolve(data)
            })
            .catch(err => {
              crud.loading = false
              reject(err)
            })
        } else if (this.tableType === 'list') {
          crud.crudMethod
            .list(crud.getQueryParams())
            .then(data => {
              crud.data = data
              crud.resetDataStatus()
              // time 毫秒后显示表格
              setTimeout(() => {
                crud.loading = false
                callVmHook(crud, CRUD.HOOK.afterRefresh)
              }, crud.time)
              resolve(data)
            })
            .catch(err => {
              crud.loading = false
              reject(err)
            })
        } else {
          crud.crudMethod
            .page(crud.getQueryParams())
            .then(data => {
              // const table = crud.getTable()
              // if (table && table.lazy) {
              //   // 懒加载子节点数据，清掉已加载的数据
              //   table.store.states.treeData = {}
              //   table.store.states.lazyTreeNodeMap = {}
              // }
              crud.page.total = data.totalElements
              crud.data = data.content
              crud.resetDataStatus()
              // time 毫秒后显示表格
              setTimeout(() => {
                crud.loading = false
                callVmHook(crud, CRUD.HOOK.afterRefresh)
              }, crud.time)
              resolve(data)
            })
            .catch(err => {
              crud.loading = false
              reject(err)
            })
        }
      })
    },
    /**
     * 启动添加操作
     */
    toAdd() {
      crud.resetForm()
      if (!(callVmHook(crud, CRUD.HOOK.beforeToAdd, crud.form) && callVmHook(crud, CRUD.HOOK.beforeToCU, crud.form))) {
        return
      }
      crud.status.add = CRUD.STATUS.PREPARED
      callVmHook(crud, CRUD.HOOK.afterToAdd, crud.form)
      callVmHook(crud, CRUD.HOOK.afterToCU, crud.form)
    },
    /**
     * 启动编辑操作
     * @param {*} data 数据项
     */
    toEdit(data) {
      crud.resetForm(JSON.parse(JSON.stringify(data)))
      if (!(callVmHook(crud, CRUD.HOOK.beforeToEdit, crud.form) && callVmHook(crud, CRUD.HOOK.beforeToCU, crud.form))) {
        return
      }
      crud.status.edit = CRUD.STATUS.PREPARED
      crud.getDataStatus(crud.getDataId(data)).edit = CRUD.STATUS.PREPARED
      callVmHook(crud, CRUD.HOOK.afterToEdit, crud.form)
      callVmHook(crud, CRUD.HOOK.afterToCU, crud.form)
    },
    /**
     * 启动删除操作
     * @param {*} data 数据项
     */
    toDelete(data) {
      crud.getDataStatus(crud.getDataId(data)).delete = CRUD.STATUS.PREPARED
    },
    /**
     * 取消删除操作
     * @param {*} data 数据项
     */
    cancelDelete(data) {
      if (!callVmHook(crud, CRUD.HOOK.beforeDeleteCancel, data)) {
        return
      }
      crud.getDataStatus(crud.getDataId(data)).delete = CRUD.STATUS.NORMAL
      callVmHook(crud, CRUD.HOOK.afterDeleteCancel, data)
    },
    /**
     * 取消 添加/编辑 操作
     */
    cancelCU() {
      const addStatus = crud.status.add
      const editStatus = crud.status.edit
      if (addStatus === CRUD.STATUS.PREPARED) {
        if (!callVmHook(crud, CRUD.HOOK.beforeAddCancel, crud.form)) {
          return
        }
        crud.status.add = CRUD.STATUS.NORMAL
      }
      if (editStatus === CRUD.STATUS.PREPARED) {
        if (!callVmHook(crud, CRUD.HOOK.beforeEditCancel, crud.form)) {
          return
        }
        crud.status.edit = CRUD.STATUS.NORMAL
        crud.getDataStatus(crud.getDataId(crud.form)).edit = CRUD.STATUS.NORMAL
      }
      crud.resetForm()
      if (addStatus === CRUD.STATUS.PREPARED) {
        callVmHook(crud, CRUD.HOOK.afterAddCancel, crud.form)
      }
      if (editStatus === CRUD.STATUS.PREPARED) {
        callVmHook(crud, CRUD.HOOK.afterEditCancel, crud.form)
      }
      // 清除表单验证
      if (crud.findVM('form').$refs['form']) {
        crud.findVM('form').$refs['form'].clearValidate()
      }
    },
    /**
     * 提交操作（添加/编辑）
     */
    submitCU() {
      if (!callVmHook(crud, CRUD.HOOK.beforeValidateCU)) {
        return
      }
      crud.findVM('form').$refs['form'].validate(valid => {
        if (!valid) {
          return
        }
        if (!callVmHook(crud, CRUD.HOOK.afterValidateCU)) {
          return
        }
        if (crud.status.add === CRUD.STATUS.PREPARED) {
          crud.doAdd()
        } else if (crud.status.edit === CRUD.STATUS.PREPARED) {
          crud.doEdit()
        }
      })
    },
    /**
     * 执行添加
     */
    doAdd() {
      if (!callVmHook(crud, CRUD.HOOK.beforeSubmit)) {
        return
      }
      crud.status.add = CRUD.STATUS.PROCESSING
      crud.crudMethod
        .add(crud.form)
        .then(() => {
          crud.status.add = CRUD.STATUS.NORMAL
          crud.resetForm()
          crud.addSuccessNotify()
          callVmHook(crud, CRUD.HOOK.afterSubmit)
          crud.toQuery()
        })
        .catch(() => {
          crud.status.add = CRUD.STATUS.PREPARED
          callVmHook(crud, CRUD.HOOK.afterAddError)
        })
    },
    /**
     * 执行编辑
     */
    doEdit() {
      if (!callVmHook(crud, CRUD.HOOK.beforeSubmit)) {
        return
      }
      crud.status.edit = CRUD.STATUS.PROCESSING
      crud.crudMethod
        .edit(crud.form)
        .then(() => {
          crud.status.edit = CRUD.STATUS.NORMAL
          crud.getDataStatus(crud.getDataId(crud.form)).edit = CRUD.STATUS.NORMAL
          crud.editSuccessNotify()
          crud.resetForm()
          callVmHook(crud, CRUD.HOOK.afterSubmit)
          crud.refresh()
        })
        .catch(() => {
          crud.status.edit = CRUD.STATUS.PREPARED
          callVmHook(crud, CRUD.HOOK.afterEditError)
        })
    },
    /**
     * 执行删除
     * @param {*} data 数据项
     */
    doDelete(data) {
      let batchDel = false
      let dataStatus
      let id
      const ids = []
      if (data instanceof Array) {
        batchDel = true
        data.forEach(val => {
          ids.push(crud.getDataId(val))
        })
      } else {
        id = crud.getDataId(data)
        ids.push(id)
        dataStatus = crud.getDataStatus(id)
      }
      if (!callVmHook(crud, CRUD.HOOK.beforeDelete, data)) {
        return
      }
      if (!batchDel && crud.crudMethod.del) {
        // 如果是删除单条记录，并且存在 del 方法则请求删除单条记录
        dataStatus.delete = CRUD.STATUS.PROCESSING
        return crud.crudMethod
          .del(id)
          .then(() => {
            dataStatus.delete = CRUD.STATUS.PREPARED
            crud.dleChangePage(1)
            crud.delSuccessNotify()
            callVmHook(crud, CRUD.HOOK.afterDelete, data)
            crud.refresh()
          })
          .catch(() => {
            dataStatus.delete = CRUD.STATUS.PREPARED
            callVmHook(crud, CRUD.HOOK.afterDelError)
          })
      } else {
        return crud.crudMethod
          .delBatch(ids)
          .then(() => {
            if (batchDel) {
              crud.batchDelLoading = false
            } else dataStatus.delete = CRUD.STATUS.PREPARED
            crud.dleChangePage(1)
            crud.delSuccessNotify()
            callVmHook(crud, CRUD.HOOK.afterDelete, data)
            crud.refresh()
          })
          .catch(() => {
            if (batchDel) {
              crud.batchDelLoading = false
            } else dataStatus.delete = CRUD.STATUS.PREPARED
            callVmHook(crud, CRUD.HOOK.afterDelError)
          })
      }
    },
    /**
     * 执行导出
     */
    doExport() {
      crud.exportLoading = true
      const ids = []
      if (crud.selections instanceof Array) {
        crud.selections.forEach(val => {
          ids.push(crud.getDataId(val))
        })
      }

      if (ids.length > 0) {
        // 如果有选中记录，则导出选中的数据
        crud.crudMethod
          .exportList(ids)
          .then(result => {
            downloadFile(result, crud.title + '数据', 'xlsx')
            crud.exportLoading = false
          })
          .catch(() => {
            crud.exportLoading = false
          })
      } else {
        // 如果没有选中记录，则导出分页查询匹配的记录
        crud.crudMethod
          .exportPage(crud.getQueryParams())
          .then(result => {
            downloadFile(result, crud.title + '数据', 'xlsx')
            crud.exportLoading = false
          })
          .catch(() => {
            crud.exportLoading = false
          })
      }
    },
    /**
     * 获取查询参数
     */
    getQueryParams: function() {
      // 清除参数无值的情况
      Object.keys(crud.query).length !== 0 &&
        Object.keys(crud.query).forEach(item => {
          if (crud.query[item] === null || crud.query[item] === '') crud.query[item] = undefined
        })
      Object.keys(crud.params).length !== 0 &&
        Object.keys(crud.params).forEach(item => {
          if (crud.params[item] === null || crud.params[item] === '') crud.params[item] = undefined
        })

      if (this.tableType === 'page') {
        return {
          page: crud.page.page - 1,
          size: crud.page.size,
          sort: crud.sort,
          ...crud.query,
          ...crud.params
        }
      } else {
        return {
          ...crud.query,
          ...crud.params
        }
      }
    },
    // 当前页改变
    pageChangeHandler(val) {
      crud.page.page = val
      crud.refresh()
    },
    // 每页条数改变
    sizeChangeHandler(val) {
      crud.page.size = val
      crud.page.page = 1
      crud.refresh()
    },
    // 预防删除第二页最后一条数据时，或者多选删除第二页的数据时，页码错误导致请求无数据
    dleChangePage(size) {
      if (crud.data.length === size && crud.page.page !== 1) {
        crud.page.page -= 1
      }
    },
    // 选择改变
    selectionChangeHandler(val) {
      crud.selections = val
    },
    /**
     * 重置查询参数
     * @param {Boolean} toQuery 重置后进行查询操作
     */
    resetQuery(toQuery = true) {
      const defaultQuery = JSON.parse(JSON.stringify(crud.defaultQuery))
      const query = crud.query
      Object.keys(query).forEach(key => {
        query[key] = defaultQuery[key]
      })
      // 重置参数
      this.params = {}
      if (toQuery) {
        crud.toQuery()
      }
    },
    /**
     * 重置表单
     * @param {Array} data 数据
     */
    resetForm(data) {
      const form =
        data ||
        (typeof crud.defaultForm === 'object'
          ? JSON.parse(JSON.stringify(crud.defaultForm))
          : crud.defaultForm.apply(crud.findVM('form')))
      const crudFrom = crud.form
      for (const key in form) {
        if (crudFrom.hasOwnProperty(key)) {
          crudFrom[key] = form[key]
        } else {
          Vue.set(crudFrom, key, form[key])
        }
      }
      // add by ghl 2020-10-04  页面重复添加信息时，下拉框的校验会存在，需要找工取消
      if (crud.findVM('form').$refs['form']) {
        crud.findVM('form').$refs['form'].clearValidate()
      }
    },
    /**
     * 重置数据状态
     */
    resetDataStatus() {
      const dataStatus = {}

      function resetStatus(data) {
        data.forEach(e => {
          dataStatus[crud.getDataId(e)] = {
            delete: 0,
            edit: 0
          }
          if (e.children) {
            resetStatus(e.children)
          }
        })
      }

      resetStatus(crud.data)
      crud.dataStatus = dataStatus
    },
    /**
     * 获取数据状态
     * @param {Number | String} id 数据项id
     */
    getDataStatus(id) {
      return crud.dataStatus[id]
    },
    /**
     * 用于树形表格多选, 选中所有
     * @param selection
     */
    selectAllChange(selection) {
      // 如果选中的数目与请求到的数目相同就选中子节点，否则就清空选中
      if (selection && selection.length === crud.data.length) {
        selection.forEach(val => {
          crud.selectChange(selection, val)
        })
      } else {
        crud.getTable().clearSelection()
      }
    },
    /**
     * 用于树形表格多选，单选的封装
     * @param selection
     * @param row
     */
    selectChange(selection, row) {
      // 如果selection中存在row代表是选中，否则是取消选中
      if (
        selection.find(val => {
          return crud.getDataId(val) === crud.getDataId(row)
        })
      ) {
        if (row.children) {
          row.children.forEach(val => {
            crud.getTable().toggleRowSelection(val, true)
            selection.push(val)
            if (val.children) {
              crud.selectChange(selection, val)
            }
          })
        }
      } else {
        crud.toggleRowSelection(selection, row)
      }
    },
    /**
     * 切换选中状态
     * @param selection
     * @param data
     */
    toggleRowSelection(selection, data) {
      if (data.children) {
        data.children.forEach(val => {
          crud.getTable().toggleRowSelection(val, false)
          if (val.children) {
            crud.toggleRowSelection(selection, val)
          }
        })
      }
    },
    findVM(type) {
      return crud.vms.find(vm => vm && vm.type === type).vm
    },
    /**
     * 显示全局的通知提醒消息
     * @param type
     * @param title
     * @param message
     */
    notify(type = CRUD.NOTIFICATION_TYPE.INFO, title, message) {
      crud.vms[0].vm.$notify({ type, title, message, duration: 2500 })
    },
    /**
     * 显示全局的通知消息
     * @param type
     * @param title
     * @param message
     */
    message(type = CRUD.NOTIFICATION_TYPE.INFO, message) {
      crud.vms[0].vm.$message({ type, message, duration: 2500 })
    },
    updateProp(name, value) {
      Vue.set(crud.props, name, value)
    },
    getDataId(data) {
      return data[this.idField]
    },
    getTable() {
      return this.findVM('presenter').$refs.table
    },
    attachTable() {
      const table = this.getTable()
      this.updateProp('table', table)
      const that = this
      table.$on('expand-change', (row, expanded) => {
        if (!expanded) {
          return
        }
        const lazyTreeNodeMap = table.store.states.lazyTreeNodeMap
        row.children = lazyTreeNodeMap[crud.getDataId(row)]
        if (row.children) {
          row.children.forEach(ele => {
            const id = crud.getDataId(ele)
            if (that.dataStatus[id] === undefined) {
              that.dataStatus[id] = {
                delete: 0,
                edit: 0
              }
            }
          })
        }
      })
    }
  }
  const crud = Object.assign({}, data)
  // 可观测化
  Vue.observable(crud)
  // 附加方法
  Object.assign(crud, methods)
  // 记录初始默认的查询参数，后续重置查询时使用
  Object.assign(crud, {
    defaultQuery: JSON.parse(JSON.stringify(data.query)),
    // 预留4位存储：组件 主页、头部、分页、表单，调试查看也方便找
    vms: Array(4),
    /**
     * 注册组件实例
     * @param {String} type 类型
     * @param {*} vm 组件实例
     * @param {Number} index 该参数内部使用
     */
    registerVM(type, vm, index = -1) {
      const vmObj = {
        type,
        vm: vm
      }
      if (index < 0) {
        this.vms.push(vmObj)
        return
      }
      if (index < 4) {
        // 内置预留vm数
        this.vms[index] = vmObj
        return
      }
      this.vms.length = Math.max(this.vms.length, index)
      this.vms.splice(index, 1, vmObj)
    },
    /**
     * 取消注册组件实例
     * @param {*} vm 组件实例
     */
    unregisterVM(type, vm) {
      for (let i = this.vms.length - 1; i >= 0; i--) {
        if (this.vms[i] === undefined) {
          continue
        }
        if (this.vms[i].type === type && this.vms[i].vm === vm) {
          if (i < 4) {
            // 内置预留vm数
            this.vms[i] = undefined
          } else {
            this.vms.splice(i, 1)
          }
          break
        }
      }
    }
  })
  // 冻结处理，需要扩展数据的话，使用crud.updateProp(name, value)，以crud.props.name形式访问，这个是响应式的，可以做数据绑定
  Object.freeze(crud)
  return crud
}

// hook VM
function callVmHook(crud, hook) {
  if (crud.debug) {
    console.log('callVmHook: ' + hook)
  }
  const tagHook = crud.tag ? hook + '$' + crud.tag : null
  let ret = true
  const nargs = [crud]
  for (let i = 2; i < arguments.length; ++i) {
    nargs.push(arguments[i])
  }
  // 有些组件扮演了多个角色，调用钩子时，需要去重
  const vmSet = new Set()
  crud.vms.forEach(vm => vm && vmSet.add(vm.vm))
  vmSet.forEach(vm => {
    if (vm[hook]) {
      ret = vm[hook].apply(vm, nargs) !== false && ret
    }
    if (tagHook && vm[tagHook]) {
      ret = vm[tagHook].apply(vm, nargs) !== false && ret
    }
  })
  return ret
}

function mergeOptions(src, opts) {
  const optsRet = {
    ...src
  }
  for (const key in src) {
    if (opts.hasOwnProperty(key)) {
      optsRet[key] = opts[key]
    }
  }
  return optsRet
}

/**
 * 查找crud
 * @param {*} vm
 * @param {string} tag
 */
function lookupCrud(vm, tag) {
  tag = tag || vm.$attrs['crud-tag'] || 'default'
  // function lookupCrud(vm, tag) {
  if (vm.$crud) {
    const ret = vm.$crud[tag]
    if (ret) {
      return ret
    }
  }
  return vm.$parent ? lookupCrud(vm.$parent, tag) : undefined
}

/**
 * crud主页
 */
function presenter(crud) {
  if (crud) {
    console.warn('[CRUD warn]: ' + 'please use $options.cruds() { return CRUD(...) or [CRUD(...), ...] }')
  }
  return {
    data() {
      // 在data中返回crud，是为了将crud与当前实例关联，组件观测crud相关属性变化
      return {
        crud: this.crud
      }
    },
    beforeCreate() {
      this.$crud = this.$crud || {}
      let cruds = this.$options.cruds instanceof Function ? this.$options.cruds() : crud
      if (!(cruds instanceof Array)) {
        cruds = [cruds]
      }
      cruds.forEach(ele => {
        if (this.$crud[ele.tag]) {
          console.error('[CRUD error]: ' + 'crud with tag [' + ele.tag + ' is already exist')
        }
        this.$crud[ele.tag] = ele
        ele.registerVM('presenter', this, 0)
      })
      this.crud = this.$crud['defalut'] || cruds[0]
    },
    methods: {
      parseTime
    },
    created() {
      for (const k in this.$crud) {
        if (this.$crud[k].queryOnPresenterCreated) {
          this.$crud[k].toQuery()
        }
      }
    },
    destroyed() {
      for (const k in this.$crud) {
        this.$crud[k].unregisterVM('presenter', this)
      }
    },
    mounted() {
      // 如果table未实例化（例如使用了v-if），请稍后在适当时机crud.attachTable刷新table信息
      if (this.$refs.table !== undefined) {
        this.crud.attachTable()
      }
    }
  }
}

/**
 * 头部
 */
function header() {
  return {
    data() {
      return {
        crud: this.crud,
        query: this.crud.query
      }
    },
    beforeCreate() {
      this.crud = lookupCrud(this)
      this.crud.registerVM('header', this, 1)
    },
    destroyed() {
      this.crud.unregisterVM('header', this)
    }
  }
}

/**
 * 分页
 */
function Pagination() {
  return {
    data() {
      return {
        crud: this.crud,
        page: this.crud.page
      }
    },
    beforeCreate() {
      this.crud = lookupCrud(this)
      this.crud.registerVM('Pagination', this, 2)
    },
    destroyed() {
      this.crud.unregisterVM('Pagination', this)
    }
  }
}

/**
 * 表单
 */
function form(defaultForm) {
  return {
    data() {
      return {
        crud: this.crud,
        form: this.crud.form
      }
    },
    beforeCreate() {
      this.crud = lookupCrud(this)
      this.crud.registerVM('form', this, 3)
    },
    created() {
      this.crud.defaultForm = defaultForm
      this.crud.resetForm()
    },
    destroyed() {
      this.crud.unregisterVM('form', this)
    }
  }
}

/**
 * crud
 */
function crud(options = {}) {
  const defaultOptions = {
    type: undefined
  }
  options = mergeOptions(defaultOptions, options)
  return {
    data() {
      return {
        crud: this.crud
      }
    },
    beforeCreate() {
      this.crud = lookupCrud(this)
      this.crud.registerVM(options.type, this)
    },
    destroyed() {
      this.crud.unregisterVM(options.type, this)
    }
  }
}

/**
 * CRUD钩子
 */
CRUD.HOOK = {
  /** 刷新 - 之前 */
  beforeRefresh: 'beforeCrudRefresh',
  /** 刷新 - 之后 */
  afterRefresh: 'afterCrudRefresh',
  /** 删除 - 之前 */
  beforeDelete: 'beforeCrudDelete',
  /** 删除 - 之后 */
  afterDelete: 'afterCrudDelete',
  /** 删除取消 - 之前 */
  beforeDeleteCancel: 'beforeCrudDeleteCancel',
  /** 删除取消 - 之后 */
  afterDeleteCancel: 'afterCrudDeleteCancel',
  /** 新建 - 之前 */
  beforeToAdd: 'beforeCrudToAdd',
  /** 新建 - 之后 */
  afterToAdd: 'afterCrudToAdd',
  /** 编辑 - 之前 */
  beforeToEdit: 'beforeCrudToEdit',
  /** 编辑 - 之后 */
  afterToEdit: 'afterCrudToEdit',
  /** 开始 "新建/编辑" - 之前 */
  beforeToCU: 'beforeCrudToCU',
  /** 开始 "新建/编辑" - 之后 */
  afterToCU: 'afterCrudToCU',
  /** "新建/编辑" 验证 - 之前 */
  beforeValidateCU: 'beforeCrudValidateCU',
  /** "新建/编辑" 验证 - 之后 */
  afterValidateCU: 'afterCrudValidateCU',
  /** 添加取消 - 之前 */
  beforeAddCancel: 'beforeCrudAddCancel',
  /** 添加取消 - 之后 */
  afterAddCancel: 'afterCrudAddCancel',
  /** 编辑取消 - 之前 */
  beforeEditCancel: 'beforeCrudEditCancel',
  /** 编辑取消 - 之后 */
  afterEditCancel: 'afterCrudEditCancel',
  /** 提交 - 之前 */
  beforeSubmit: 'beforeCrudSubmitCU',
  /** 提交 - 之后 */
  afterSubmit: 'afterCrudSubmitCU',
  /** 添加错误 - 之后 */
  afterAddError: 'afterCrudAddError',
  /** 编辑错误 - 之后 */
  afterEditError: 'afterCrudEditError',
  /** 删除错误 - 之后 */
  afterDelError: 'afterCrudDelError'
}

/**
 * CRUD状态
 */
CRUD.STATUS = {
  NORMAL: 0,
  PREPARED: 1,
  PROCESSING: 2
}

/**
 * CRUD通知类型
 */
CRUD.NOTIFICATION_TYPE = {
  SUCCESS: 'success',
  WARNING: 'warning',
  INFO: 'info',
  ERROR: 'error'
}

export default CRUD

export { presenter, header, form, Pagination, crud }
