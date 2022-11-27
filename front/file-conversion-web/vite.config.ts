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
      '^/dev': {
        target: 'http://127.0.0.1:8761/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/dev/, '')
      },
      '^/prod': {
        target: 'http://127.0.0.1:8081/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/prod/, '')
      }
    }
  },
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
})
