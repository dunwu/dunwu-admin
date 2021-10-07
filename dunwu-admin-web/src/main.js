import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css'

import Element from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
// global css
import './assets/styles/index.scss'
import './assets/styles/element-variables.scss'

//
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// 数据字典
import dict from './components/Dict'
// 权限指令
import checkPer from '@/utils/permission'
import permission from './components/Permission'

// 代码高亮
import VueHighlightJS from 'vue-highlightjs'
import 'highlight.js/styles/atom-one-dark.css'

import App from './App'
import store from './store'
import router from './router'

import './assets/icons' // icon
import './router/index' // permission control
import 'echarts-gl'

Vue.use(checkPer)
Vue.use(VueHighlightJS)
Vue.use(mavonEditor)
Vue.use(permission)
Vue.use(dict)
Vue.use(Element, {
  size: Cookies.get('size') || 'small' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
