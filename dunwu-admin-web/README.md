# dunwu-admin-web

> dunwu-admin-web 是 dunwu-admin 的前端项目。

## 核心技术点

| 技术点                                         | 版本     | 说明                   |
| ---------------------------------------------- | -------- | ---------------------- |
| [Node.js](https://nodejs.org/zh-cn/)           | 12.13.0+ | JavaScript 运行时      |
| [Vue.js](https://cn.vuejs.org/index.html)      | 2.6.10   | JavaScript 框架        |
| [Vue Router](https://router.vuejs.org/zh/)     | 3.0.2    | Vue 官方的路由管理器   |
| [Vuex](https://vuex.vuejs.org/zh/)             | 3.1.0    | Vue 官方的状态管理器   |
| [Element-UI](https://element.eleme.cn/#/zh-CN) | 2.13.2   | Vue 2.0 的桌面端组件库 |
| [Axios](https://github.com/axios/axios)        | 0.18.1   | Http Client 工具       |


## 项目结构

```bash
.
|-- src							# 代码目录
|   |-- api						# Http Api 目录
|   |-- assets					# 资源目录（图片、图标、css 等）
|   |-- components				# 组件目录
|   |-- layout					# 布局目录
|   |-- mixins					# 混入对象目录（复用）
|   |-- router					# 路由目录
|   |-- store					# 状态存储目录
|   |-- utils					# 工具集目录
|   |-- views					# 视图目录
|   |-- App.vue					# 系统主模板
|   |-- main.js					# 系统程序入口
|   `-- settings.js				# 系统级配置
|-- .editorconfig				# 基础代码规范（参考：https://editorconfig.org）
|-- .env.development			# 环境变量（开发环境）
|-- .env.production				# 环境变量（生产环境）
|-- .eslintignore				# eslint 检查忽略文件
|-- .eslintrc.js				# eslint 检查规则
|-- babel.config.js				# babel 预发编译器配置（参考：https://www.babeljs.cn）
|-- jest.config.js				# jest 测试框架配置（参考：https://www.jestjs.cn）
|-- package.json				# Nodejs 项目顶级配置（参考：https://javascript.ruanyifeng.com/nodejs/packagejson.html）
|-- postcss.config.js			# postcss 样式渲染配置
|-- prettier.config.js			# prettier 代码格式化配置
`-- vue.config.js				# vue 项目顶级配置
```

## 快速入门

### 环境要求

Nodejs 11.0+

### 启动/构建

安装项目的依赖：`npm install`

开发环境启动：`npm run dev`

生产环境构建：`npm run build`

## 技术点

