// 文件说明：Vue/Vite 开发服务器配置文件，设置 Vue 插件和前端端口。
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// Vite 配置文件：用于 Vue 版本前端的开发服务器。
export default defineConfig({
  // 启用 Vue 插件，让 Vite 能识别 .vue 单文件组件。
  plugins: [vue()],
  server: {
    // Vue 开发服务器端口。如果使用 npm run dev，浏览器访问 http://localhost:5173。
    port: 5173
  }
})
