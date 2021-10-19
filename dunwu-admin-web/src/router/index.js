/**
 * 全局路由入口
 * @see 【vue-router 官方文档】动态路由匹配：https://router.vuejs.org/zh/guide/essentials/dynamic-matching.html
 * @see 【vue-router 官方文档】导航守卫：https://router.vuejs.org/zh/guide/advanced/navigation-guards.html
 */
import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import store from '@/store'
import Config from '@/settings'
import constantRouter from './constantRouter'
import { getToken } from '@/utils/auth' // getToken from cookie
import { buildMenus } from '@/api/cas/menu'
import { filterAsyncRouter } from '@/store/modules/permission'

Vue.use(Router)

/**
 * NProgress Configuration
 */
NProgress.configure({ showSpinner: false })

// 获取白名单路由的地址列表
const whiteListPaths = ['/login', '/404']

/**
 * 实例化全局路由
 */
const router = new Router({
  // mode: 'hash',
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouter
})

/**
 * 导航守卫-全局前置守卫
 */
router.beforeEach((to, from, next) => {
  if (to.meta.name) {
    document.title = to.meta.name + ' - ' + Config.title
  }

  // console.info('白名单', whiteListPaths)

  if (store.state.settings.enableAuth) {
    doDispatchInAuth(to, from, next)
  } else {
    doDispatchInNoAuth(to, from, next)
  }
})

/**
 * 认证模式下的路由分发
 * @param to
 * @param from
 * @param next
 */
const doDispatchInAuth = (to, from, next) => {
  NProgress.start()

  if (getToken()) {
    // 已登录且要跳转的页面是登录页
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      loadMenusInCondition(next, to)
    }
  } else {
    if (whiteListPaths.indexOf(to.path) !== -1 || to.path.match('tools/code/*')) {
      // 在免登录白名单，直接进入
      next()
    } else {
      // 否则全部重定向到登录页
      next(`/login?redirect=${to.fullPath}`)
      NProgress.done()
    }
  }
}

/**
 * 非认证模式下的路由分发
 * @param to
 * @param from
 * @param next
 */
const doDispatchInNoAuth = (to, from, next) => {
  console.info('非认证模式')
  console.info('path', to.path)

  NProgress.start()

  // 非认证模式下，不跳转到登录页面
  if (whiteListPaths.indexOf(to.path) !== -1 || to.path.match('tools/code/*')) {
    console.info('允许访问')
    // 在免登录白名单，直接进入
    next()
  } else {
    next(`/404`)
    NProgress.done()
  }
}

/**
 * 根据不同条件加载菜单
 * @param next
 * @param to
 */
const loadMenusInCondition = (next, to) => {
  if (store.getters.roles.length === 0) {
    // 判断当前用户是否已拉取完user_info信息
    store
      .dispatch('GetInfo')
      .then(() => {
        // 拉取user_info
        // 动态路由，拉取菜单
        loadMenus(next, to)
      })
      .catch(() => {
        store.dispatch('LogOut').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      })
    // 登录时未拉取 菜单，在此处拉取
  } else if (store.getters.loadMenus) {
    // 修改成false，防止死循环
    store.dispatch('updateLoadMenus')
    loadMenus(next, to)
  } else {
    next()
  }
}

/**
 * 加载菜单
 * @param next
 * @param to
 */
export const loadMenus = (next, to) => {
  buildMenus().then(res => {
    const sdata = JSON.parse(JSON.stringify(res))
    const rdata = JSON.parse(JSON.stringify(res))
    const sidebarRoutes = filterAsyncRouter(sdata)
    const rewriteRoutes = filterAsyncRouter(rdata, true)
    rewriteRoutes.push({ path: '*', redirect: '/404', hidden: true })

    store.dispatch('GenerateRoutes', rewriteRoutes).then(() => {
      // 存储路由
      rewriteRoutes.forEach(r => {
        router.addRoute(r) // 动态添加可访问路由表
      })
      next({ ...to, replace: true })
    })
    store.dispatch('SetSidebarRouters', sidebarRoutes)
  })
}

/**
 * 导航守卫-全局后置钩子
 */
router.afterEach(() => {
  NProgress.done() // finish progress bar
})

export default router
