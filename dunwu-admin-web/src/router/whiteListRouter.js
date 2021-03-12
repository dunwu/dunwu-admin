/**
 * 白名单路由（不用认证即可访问）
 */
const whiteListRouter = [
  {
    path: '/demo/hello',
    meta: { title: '测试页面', noCache: true },
    component: resolve => require(['@/views/demo/HelloList'], resolve),
    hidden: true
  }
]
export default whiteListRouter
