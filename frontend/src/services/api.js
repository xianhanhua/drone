// 文件说明：Vue 前端接口封装文件，统一调用登录和无人机管理 API。
import axios from 'axios'

// 创建 axios 实例，统一设置后端接口地址、是否携带 cookie、请求超时时间。
const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8091/api',
  withCredentials: true,
  timeout: 12000
})

// 请求拦截器：每次请求发出去之前，自动把登录 token 放到请求头里。
http.interceptors.request.use(config => {
  const token = localStorage.getItem('drone-management-auth-token')
  if (token) {
    config.headers['X-Auth-Token'] = token
  }
  return config
})

// 响应拦截器：成功时只返回后端 data，失败时把错误信息整理成 Error。
http.interceptors.response.use(
  response => response.data,
  error => {
    const message = error.response?.data?.message || error.message || '璇锋眰澶辫触'
    return Promise.reject(new Error(message))
  }
)

// 登录相关接口封装。
export const authApi = {
  me: () => http.get('/auth/me'),
  login: data => http.post('/auth/login', data),
  logout: () => http.post('/auth/logout')
}

// 无人机管理相关接口封装。
export const droneApi = {
  list: params => http.get('/drones', { params }),
  detail: id => http.get(`/drones/${id}`),
  create: data => http.post('/drones', data),
  update: (id, data) => http.put(`/drones/${id}`, data),
  remove: id => http.delete(`/drones/${id}`),
  aiPreview: name => http.get('/drones/ai-preview', { params: { name } })
}
