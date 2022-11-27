# file-conversion-web

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin) to make the TypeScript language service aware of `.vue` types.

If the standalone TypeScript plugin doesn't feel fast enough to you, Volar has also implemented a [Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669) that is more performant. You can enable it by the following steps:

1. Disable the built-in TypeScript Extension
    1) Run `Extensions: Show Built-in Extensions` from VSCode's command palette
    2) Find `TypeScript and JavaScript Language Features`, right click and select `Disable (Workspace)`
2. Reload the VSCode window by running `Developer: Reload Window` from the command palette.

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

## elementUI

```text
https://element-plus.gitee.io/zh-CN/component/button.html
```
## node
```text
v16.18.0
```
## vuejs
```text
https://cn.vuejs.org/guide/scaling-up/sfc.html#what-about-separation-of-concerns
```
## vue-request
```text
https://next.attojs.com/guide/introduction.html
```
## vant
```text
http://vant3.uihtm.com/#/en-US
```

## vite config
```text
https://vitejs.dev/guide/api-plugin.html#configureserver
```
### 开发问题
#### script setup 无法使用emit
```text
<script setup>
import {defineProps, defineEmits} from 'vue';
import {toRefs} from 'vue';
const props = defineProps({
  item: {
    type: Object,
    default() {
      return {
        title: '',
        items: [],
      };
    },
  },
});

const {item} = toRefs(props);

const emit = defineEmits(['change', 'delete','等处理方法'])
// setup code
</script>
```
####  变量值定义

```text
import { ref } from 'vue'
const input = ref('')
```
####  表单值定义
```text
  import { reactive } from 'vue'
  const pageData = reactive({
    title:'专注文件转换'
  })
```

####  解决打包最大500kb问题
```text
build: {
        chunkSizeWarningLimit:1500,
        rollupOptions: {
            output:{
                manualChunks(id) {
                    if (id.includes('node_modules')) {
                        return id.toString().split('node_modules/')[1].split('/')[0].toString();
                    }
                }
            }
        }
    }
```
####  解决打包js文件无法引入，类型错误
error TS7006: Parameter 'v' implicitly has an 'any' type.
主要增加
```text
    "noImplicitAny": false,
    "allowJs": true
```
完整配置如下
```text
{
  "extends": "@vue/tsconfig/tsconfig.web.json",
  "include": ["env.d.ts", "src/**/*", "src/**/*.vue"],
  "compilerOptions": {
    "baseUrl": ".",
    "paths": {
      "@/*": ["./src/*"]
    },
    "noImplicitAny": false,
    "allowJs": true
  },

  "references": [
    {
      "path": "./tsconfig.config.json"
    }
  ]
}

```
####  解决打包之后，部署nginx，访问空白页面
修改router中createWebHistory为createWebHashHistory
```text
import { createRouter, createWebHistory,createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('../views/Index.vue')
    }
  ]
})

export default router

```
#### 打包部署nginx 配置
```text

user  nginx;
worker_processes  1;

error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
}


http {
    add_header Access-Control-Allow-Methods *;
    add_header Access-Control-Allow-Origin $http_origin;
    add_header Access-Control-Allow-Credentials true;
    add_header Access-Control-Allow-Headers *;
    add_header testname zuiyu;
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;

    proxy_buffer_size 16k;
    proxy_buffers 4 32k;
    proxy_busy_buffers_size 96k;
    proxy_cache_path /tmp/cache levels=1:2 keys_zone=cache_one:100m inactive=1d max_size=10g;
    proxy_connect_timeout 10;
    proxy_read_timeout 180;
    proxy_send_timeout 5;
    proxy_temp_file_write_size 96k;
    proxy_temp_path /tmp/temp_dir;

    server {
        listen              9002;
        server_name         127.0.0.1;
    location /zfc {
          add_header zuiyu_args1 "test hello";
          add_header zuiyu_args $args;
          add_header Nginx-Cache     "$upstream_cache_status";
          try_files $uri $uri/ /index.html;
          alias /usr/share/nginx/html/zfc;
          index index.html;

    } 
    location /assets {
          add_header zuiyu_args1 "test hello";
          add_header zuiyu_args $args;
          add_header Nginx-Cache     "$upstream_cache_status";
          alias /usr/share/nginx/html/zfc/assets;
          index index.html;

    }
    location ^~/prod {
          proxy_pass http://host:port/;

    } 
 
}

```
#### axios element-ui-plus el-upload
* 使用http-request 覆盖默认请求
```text
<el-upload
        :http-request="uploadFile"
    >
    </el-upload>
```
* 编写上传方法
```text

const uploadFile = (fileReqOpt) => {
  let fd = new FormData()
  fd.append("file",fileReqOpt.file)
  fd.append("params","file.file")
  testPostFile(fd)
}
```

#### nextTick 无法使用
```text
import {  nextTick } from 'vue'
```

#### setup 函数中使用router
```text
import { useRouter } from "vue-router";
const thisUsrRouter = useRouter()
const goBack = ()=>{
   thisUsrRouter.push("/")
}
```

#### el-upload 文件删除
* 定义refs
```text
import { getCurrentInstance } from 'vue'
const {proxy} = getCurrentInstance()
```
* 定义ref
```text
 <el-upload
        ref="upload"
        :on-remove="handleRemove"
    >
```
* 指定删除方法
```text
   proxy.$refs.upload.handleRemove(要删除的文件)
```

#### 使用stores计算属性绑定组件值
```text
import { reactive } from 'vue'
export const convertPageData = reactive({
  title: "专注文件转换",
  accept:".txt",
  setTitle(v) {
    this.title = v
  },
  setAccept(v) {
    this.accept = v
  }
})
```

* 使用
```text
    convertPageData.setTitle("")
    convertPageData.setAccept("")
    //
    convertPageData.title
    convertPageData.accept
```