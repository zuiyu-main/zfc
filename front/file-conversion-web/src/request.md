# vue3集成axios
## 配置基础环境
* 安装axios
```text
npm install axios
```
* 编写dev与prod环境文件
dev
```text
NODE_ENV='development'
```
prod
```text
NODE_ENV='production'
```
* 动态配置环境信息
```text
export interface IConfig {
  env: string // 开发环境
  mock?: boolean // mock数据
  title: string // 项目title
  baseUrl?: string // 项目地址
  baseApi?: string // api请求地址
  APPID?: string // 公众号appId  一般放在服务器端
  APPSECRET?: string // 公众号appScript 一般放在服务器端
}

const dev: IConfig = {
  env: "development",
  mock: false,
  title: "开发",
  baseUrl: '/api', 
  baseApi: "http://127.0.0.1:8080", 
  APPID: "17970",
  APPSECRET: "zuiyu"
}

const prod: IConfig = {
  env: "production",
  mock: false,
  title: "生产",
  baseUrl: "/api",
  baseApi: "http://localhost:8080", 
  APPID: "17970",
  APPSECRET: "zuiyu"
}
export const config: IConfig = import.meta.env.MODE == 'development' ? dev : prod
```
* 打包修改配置
package.json,修改`build-only`如下，主要增加 `--mode production`
```text
  "scripts": {
    "dev": "vite",
    "build": "run-p type-check build-only ",
    "build-only": "vite build --mode production"
    }
```
* 封装request.js
```text


import axios from "axios";
// import router from  "@/router";
import  {config}  from "@/config/index.ts";
const service = axios.create({
  // baseURL 需要设置为反向代理前缀，不能设置绝对路径URL
  baseURL: config.baseUrl, 
  timeout: 5000,
  withCredentials: false, 
  headers: {'X-Custom-Header': 'zuiyu'}
})
service.defaults.headers.common['Authorization'] = "AUTH_TOKEN";
service.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
service.defaults.headers.get['Content-Type']='application/x-www-form-urlencoded'
// 添加请求拦截器
service.interceptors.request.use(function (req) {
  // 在发送请求之前做些什么
  console.log('请求前拦截器1:',config)
  console.log('请求前拦截器2:',req)
  // if (config.loading) {

  // }
  return req;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});
//添加响应拦截器
service.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  const res = response.data;
    if (res.code !== 200) {
      // token 过期
      if (res.code === 401)
        // 警告提示窗
        return;
      if (res.code == 403) {
        return;
      }
      // 若后台返回错误值，此处返回对应错误对象，下面 error 就会接收
      return Promise.reject(new Error(res.msg || "Error"));
    }
    return response;
}, function (error) {
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
    if (error.message == "Network Error") error.message == "网络异常，请检查后重试！"
    error.message = "连接到服务器失败，请联系管理员"
  }
  return Promise.reject(error);
});

export default service

```
* 配置反向代理地址
根目录 vite.config.ts
```text
import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    cors: true, // 默认启用并允许任何源
    open: true, // 在服务器启动时自动在浏览器中打开应用程序
    port: 5173,
    proxy: {
      '^/api': {
        target: 'http://127.0.0.1:8080/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
        
      },
    }
  }
})

```
## 调取后台接口
* 编写测试api.js
```text
import service from '@/util/request.js'
export const testGetMethod = () => service.get('/test/get')
export const testGetHaveParamsMethod =
    (params) => service.get('/test/getHaveParams',{params:params})

export const testPost = (params) =>
    service.post('/test/post',
        params,
        {
            headers: {"Content-Type": "application/x-www-form-urlencoded"}
        }
)
export const testPostJson =
    (params) => service.post('/test/postJson',params)

export const testPostFile =
    (params) => service.post('/test/postFile',params,{
        headers: {"Content-Type": "application/x-www-form-urlencoded"}
    })
```
* 接口调用
  * 组件中引入
  ```text
  import {testPostFile} from "@/api/api";
  // 或者引入全部使用
  import TestApi from "@/api/api";
  ```
  * 调用
  ```text
  const uploadFile = (fileReqOpt) => {
  let fd = new FormData()
  fd.append("file",fileReqOpt.file)
  fd.append("params","自定义参数")
  testPostFile(fd)
  }
  // 或者
    async function test() {
    let p ={
      params: "参数1",
      file: "file"
    }
    let result = await testPostFile(p);
  }
  ```
