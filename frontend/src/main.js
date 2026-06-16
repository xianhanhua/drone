// 文件说明：Vue 前端启动入口，把 App.vue 挂载到 index.html 的 #app 上。
import { createApp } from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import './styles.css'
import App from './App.vue'

// Vue 项目的入口文件。
// createApp(App) 表示把 App.vue 作为根组件，
// mount('#app') 表示把整个 Vue 页面挂载到 index.html 里的 id="app" 节点上。
createApp(App).mount('#app')
