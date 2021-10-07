// 适配 Nginx 反向代理
const baseUrl = process.env.VUE_APP_BASE_API === '/' ? '' : process.env.VUE_APP_BASE_API
const api = {
  state: {
    // 部署包上传
    deployUploadApi: baseUrl + '/deploy/upload',
    // SQL脚本上传
    databaseUploadApi: baseUrl + '/database/upload',
    // 实时控制台
    socketApi: baseUrl + '/websocket?token=kl',
    // 图片上传
    imagesUploadApi: baseUrl + '/localStorage/pictures',
    // 修改头像
    updateAvatarApi: baseUrl + '/users/updateAvatar',
    // 上传文件到七牛云
    qiNiuUploadApi: baseUrl + '/qiNiuContent',
    // Sql 监控
    sqlApi: baseUrl + '/druid/index.html',
    // swagger
    swaggerApi: baseUrl + '/swagger-ui.html',
    // 文件上传
    fileUploadApi: baseUrl + '/tool/storage/upload',
    // baseUrl，
    baseApi: baseUrl
  }
}

export default api
