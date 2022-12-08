import axios from "axios";
// import router from  "@/router";
import {config} from "@/config/index.ts";
import {ElLoading, ElMessage} from 'element-plus'
import {nextTick} from 'vue'

const service = axios.create({
  // baseURL 需要设置为反向代理前缀，不能设置绝对路径URL
  baseURL: config.baseUrl, 
  timeout: 5000,
  withCredentials: false, 
  headers: {'X-Custom-Header': 'zuiyu'}
})
service.defaults.headers.common['Authorization'] = "AUTH_TOKEN";
//service.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
service.defaults.headers.get['Content-Type']='application/x-www-form-urlencoded'
// 添加请求拦截器
let loadingInstance;
service.interceptors.request.use(function (req) {
  loadingInstance = ElLoading.service({fullscreen:false,text:"正在加载..."})
  return req;
}, function (error) {
  nextTick(() => {
    loadingInstance.close()
  })
  return Promise.reject(error);
});
//添加响应拦截器
service.interceptors.response.use(function (response) {
  nextTick(() => {
    loadingInstance.close()
  })
  console.log('成功响应：',JSON.stringify(response))
  const res = response.data;
    if (res.code !== 200) {
      // token 过期
      if (res.code === 401){
        // 警告提示窗
        ElMessage.error('授权已过期')
        return;
      }
      ElMessage.error(res.msg)
      // return Promise.resolve(res);
      return response;
      // return Promise.reject(new Error(res.msg || "Error"));
    }

  return response;

}, function (error) {
  nextTick(() => {
    loadingInstance.close()
  })
  console.log('失败响应：',JSON.stringify(error))
  // 对响应错误做点什么
  if (error && error.response) {
    switch (error.response.status) {
      case 400:
        error.message = "请求错误(400)"
        break
      case 401:
        error.message = "未授权,请登录(401)"
        break
      case 403:
        error.message = "拒绝访问(403)"
        break
      case 404:
        error.message = `请求地址出错: ${error.response.config.url}`
        break
      case 405:
        error.message = "请求方法未允许(405)"
        break
      case 408:
        error.message = "请求超时(408)"
        break
      case 500:
        error.message = "服务器内部错误(500)"
        break
      case 501:
        error.message = "服务未实现(501)"
        break
      case 502:
        error.message = "网络错误(502)"
        break
      case 503:
        error.message = "服务不可用(503)"
        break
      case 504:
        error.message = "网络超时(504)"
        break
      case 505:
        error.message = "HTTP版本不受支持(505)"
        break
      default:
        error.message = `连接错误: ${error.message}`
    }
  } else {
    if (error.code === "ERR_NETWORK") {
      error.message = "网络异常，请检查后重试！"
    }else{
      error.message = "服务器链接失败，请联系管理员!"
    }
  }
  ElMessage.error(error.message)
  return Promise.reject(error);
});

export default service
