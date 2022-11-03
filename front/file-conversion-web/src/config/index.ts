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
  baseUrl: '/dev', 
  baseApi: "http://127.0.0.1:8080", 
  APPID: "17970",
  APPSECRET: "zuiyu"
}

const prod: IConfig = {
  env: "production",
  mock: false,
  title: "生产",
  baseUrl: "/prod",
  baseApi: "http://127.0.0.1:8081", 
  APPID: "17970",
  APPSECRET: "zuiyu"
}
export const config: IConfig = import.meta.env.MODE == 'development' ? dev : prod