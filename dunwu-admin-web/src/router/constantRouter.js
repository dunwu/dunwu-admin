import _ from 'lodash'
import Layout from '../layout/index'
import whiteListRouter from './whiteListRouter'

/**
 * 默认路由
 * @type {any}
 */
const defaultRouter = [
  {
    path: '/login',
    meta: { title: '登录', noCache: true },
    component: resolve => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: resolve => require(['@/views/features/401'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: resolve => require(['@/views/features/404'], resolve),
    hidden: true
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: resolve => require(['@/views/features/redirect'], resolve)
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/home'),
        name: 'Dashboard',
        meta: { title: '首页', icon: 'index', affix: true, noCache: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'center',
        component: () => import('@/views/cas/user/center'),
        name: '个人中心',
        meta: { title: '个人中心' }
      }
    ]
  }
]

/**
 * 常量路由
 * @type {any}
 */
const constantRouter = _.concat(defaultRouter, whiteListRouter)
export default constantRouter
