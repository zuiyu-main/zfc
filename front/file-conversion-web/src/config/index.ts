export interface IConfig {
  env: string
  mock?: boolean
  title: string
  baseUrl?: string
  baseApi?: string
  APPID?: string
  APPSECRET?: string
}

const dev: IConfig = {
  env: "development",
  mock: false,
  title: "开发",
  baseUrl: '/dev', 
  baseApi: "http://127.0.0.1:8761",
  APPID: "17970",
  APPSECRET: "zuiyu"
}

const prod: IConfig = {
  env: "production",
  mock: false,
  title: "生产",
  baseUrl: "/prod",
  baseApi: "http://127.0.0.1:8761",
  APPID: "17970",
  APPSECRET: "zuiyu"
}
const boot: IConfig = {
  env: "boot",
  mock: false,
  title: "boot 打包",
  baseUrl: "",
  baseApi: "http://127.0.0.1:8761",
  APPID: "17970",
  APPSECRET: "zuiyu"
}
const serverMode = import.meta.env.MODE
export const config: IConfig =
    serverMode === 'development' ? dev: import.meta.env.MODE == 'boot'?boot:prod