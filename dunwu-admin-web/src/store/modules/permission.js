import constantRouter from '@/router/constantRouter'
import Layout from '@/layout/index'
import ParentView from '@/components/ParentView'

const permission = {
  state: {
    routers: constantRouter,
    addRouters: [],
    sidebarRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouter.concat(routers)
    },
    SET_SIDEBAR_ROUTERS: (state, routers) => {
      state.sidebarRouters = constantRouter.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, asyncRouter) {
      commit('SET_ROUTERS', asyncRouter)
    },
    SetSidebarRouters({ commit }, sidebarRouter) {
      commit('SET_SIDEBAR_ROUTERS', sidebarRouter)
    }
  }
}

export const filterAsyncRouter = (routers, isRewrite = false) => {
  // 遍历后台传来的路由字符串，转换为组件对象
  return routers.filter(router => {
    if (isRewrite && router.children) {
      router.children = filterChildren(router.children)
    }
    if (router.component) {
      if (router.component === 'Layout') {
        // Layout组件特殊处理
        router.component = Layout
      } else if (router.component === 'ParentView') {
        router.component = ParentView
      } else {
        const component = router.component
        router.component = loadView(component)
      }
    }
    if (router.children && router.children.length) {
      router.children = filterAsyncRouter(router.children, router, isRewrite)
    }
    return true
  })
}

function filterChildren(childrenMap) {
  let children = []
  childrenMap.forEach((el, index) => {
    // console.info('路由信息', el)
    if (el.children && el.children.length) {
      if (el.component === 'ParentView') {
        el.children.forEach(c => {
          c.path = el.path + '/' + c.path
          if (c.children && c.children.length) {
            children = children.concat(filterChildren(c.children, c))
            return
          }
          children.push(c)
        })
        return
      }
    }
    children = children.concat(el)
  })
  return children
}

export const loadView = view => {
  return resolve => require([`@/views/${view}`], resolve)
}

export default permission
