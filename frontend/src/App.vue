<!-- 文件说明：Vue 版本主组件，包含登录、无人机列表和编辑表单逻辑。 -->
<template>
  <main class="app-shell">
    <section v-if="authChecked && !currentUser.authenticated" class="login-panel">
      <div class="brand-block">
        <div class="brand-mark">DM</div>
        <h1>无人机信息管理</h1>
      </div>
      <form class="login-form" @submit.prevent="login">
        <label>
          用户名
          <input v-model.trim="loginForm.username" class="form-control" autocomplete="username" />
        </label>
        <label>
          密码
          <input v-model="loginForm.password" class="form-control" type="password" autocomplete="current-password" />
        </label>
        <button class="btn btn-primary btn-block" type="submit" :disabled="loading">
          登录
        </button>
        <p v-if="message" class="feedback error">{{ message }}</p>
      </form>
    </section>

    <section v-else class="workspace">
      <header class="topbar">
        <div>
          <h1>无人机信息管理</h1>
          <span>{{ currentUser.username }}</span>
        </div>
        <button class="btn icon-button" title="退出登录" @click="logout">
          <LogOut :size="18" />
        </button>
      </header>

      <section class="toolbar">
        <div class="search-group">
          <Search :size="17" />
          <input v-model.trim="filters.keyword" class="form-control" placeholder="名称、型号、厂商、序列号" @keyup.enter="fetchDrones" />
        </div>
        <select v-model="filters.status" class="form-control status-select" @change="fetchDrones">
          <option value="">全部状态</option>
          <option v-for="status in statuses" :key="status" :value="status">{{ status }}</option>
        </select>
        <button class="btn icon-text-button" @click="fetchDrones">
          <RefreshCw :size="17" />
          查询
        </button>
        <button class="btn btn-primary icon-text-button" @click="startCreate">
          <Plus :size="17" />
          新增
        </button>
      </section>

      <p v-if="message" :class="['feedback', messageType]">{{ message }}</p>

      <section class="content-grid">
        <div class="table-wrap">
          <table class="table table-hover mb-0">
            <thead>
              <tr>
                <th>名称</th>
                <th>型号</th>
                <th>类型</th>
                <th>续航</th>
                <th>速度</th>
                <th>载重</th>
                <th>状态</th>
                <th class="action-col">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-if="loading">
                <td colspan="8" class="empty-cell">加载中</td>
              </tr>
              <tr v-else-if="drones.length === 0">
                <td colspan="8" class="empty-cell">暂无数据</td>
              </tr>
              <tr v-for="drone in drones" :key="drone.id">
                <td>
                  <strong>{{ drone.name }}</strong>
                  <small>{{ drone.serialNumber }}</small>
                </td>
                <td>{{ drone.model }}</td>
                <td>{{ drone.droneType }}</td>
                <td>{{ drone.maxFlightTimeMinutes }} 分钟</td>
                <td>{{ drone.maxSpeedKmh }} km/h</td>
                <td>{{ drone.payloadCapacityKg }} kg</td>
                <td><span class="status-badge">{{ drone.status }}</span></td>
                <td class="actions">
                  <button class="btn icon-button" title="编辑" @click="editDrone(drone)">
                    <Pencil :size="17" />
                  </button>
                  <button class="btn icon-button danger" title="删除" @click="deleteDrone(drone)">
                    <Trash2 :size="17" />
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <aside class="editor-panel" v-if="editor.visible">
          <div class="editor-head">
            <h2>{{ editor.mode === 'create' ? '录入无人机' : '修改无人机' }}</h2>
            <button class="btn icon-button" title="关闭" @click="closeEditor">
              <X :size="18" />
            </button>
          </div>

          <form @submit.prevent="saveDrone">
            <label>
              名称
              <input v-model.trim="form.name" class="form-control" />
            </label>

            <div class="inline-actions" v-if="editor.mode === 'create'">
              <button class="btn icon-text-button" type="button" @click="generatePreview">
                <Sparkles :size="17" />
                AI生成属性
              </button>
            </div>

            <div class="form-row">
              <label>
                型号
                <input v-model.trim="form.model" class="form-control" :readonly="editor.mode === 'create'" />
              </label>
              <label>
                厂商
                <input v-model.trim="form.manufacturer" class="form-control" :readonly="editor.mode === 'create'" />
              </label>
            </div>

            <label>
              序列号
              <input v-model.trim="form.serialNumber" class="form-control" :readonly="editor.mode === 'create'" />
            </label>

            <div class="form-row">
              <label>
                类型
                <input v-model.trim="form.droneType" class="form-control" :readonly="editor.mode === 'create'" />
              </label>
              <label>
                状态
                <select v-model="form.status" class="form-control">
                  <option v-for="status in statuses" :key="status" :value="status">{{ status }}</option>
                </select>
              </label>
            </div>

            <div class="metric-grid">
              <label>
                续航(分钟)
                <input v-model.number="form.maxFlightTimeMinutes" class="form-control" type="number" :readonly="editor.mode === 'create'" />
              </label>
              <label>
                速度(km/h)
                <input v-model.number="form.maxSpeedKmh" class="form-control" type="number" :readonly="editor.mode === 'create'" />
              </label>
              <label>
                高度(m)
                <input v-model.number="form.maxAltitudeMeters" class="form-control" type="number" :readonly="editor.mode === 'create'" />
              </label>
              <label>
                载重(kg)
                <input v-model.number="form.payloadCapacityKg" class="form-control" type="number" step="0.1" :readonly="editor.mode === 'create'" />
              </label>
              <label>
                电池(mAh)
                <input v-model.number="form.batteryCapacityMah" class="form-control" type="number" :readonly="editor.mode === 'create'" />
              </label>
            </div>

            <label>
              AI画像
              <textarea v-model.trim="form.aiProfile" class="form-control" rows="3" :readonly="editor.mode === 'create'"></textarea>
            </label>

            <div class="editor-actions">
              <button class="btn btn-primary icon-text-button" type="submit">
                <Save :size="17" />
                保存
              </button>
              <button class="btn icon-text-button" type="button" @click="closeEditor">
                取消
              </button>
            </div>
          </form>
        </aside>
      </section>
    </section>
  </main>
</template>

<script setup>
// script setup 是 Vue 3 的写法，里面定义的数据和方法可以直接在模板中使用。
import { onMounted, reactive, ref } from 'vue'
import { LogOut, Pencil, Plus, RefreshCw, Save, Search, Sparkles, Trash2, X } from 'lucide-vue-next'
import { authApi, droneApi } from './services/api'

const statuses = ['待检', '在役', '维修', '退役']
// authChecked 表示是否已经检查完登录状态；loading 表示当前是否正在请求后端。
const authChecked = ref(false)
const loading = ref(false)
// 页面提示信息。
const message = ref('')
const messageType = ref('error')
// 无人机列表数据。
const drones = ref([])

// 当前登录用户信息。
const currentUser = reactive({
  authenticated: false,
  username: ''
})

// 登录表单默认值。
const loginForm = reactive({
  username: 'admin',
  password: 'admin123'
})

// 查询条件。
const filters = reactive({
  keyword: '',
  status: ''
})

// 编辑器状态：是否显示、当前是新增还是编辑、正在编辑哪条 id。
const editor = reactive({
  visible: false,
  mode: 'create',
  id: null
})

// 表单数据。
const form = reactive(emptyForm())

onMounted(async () => {
  // 页面加载后先检查登录状态，已登录就自动加载无人机列表。
  await checkMe()
  if (currentUser.authenticated) {
    await fetchDrones()
  }
})

async function checkMe() {
  // 调用后端 /auth/me 判断当前是否已登录。
  try {
    const result = await authApi.me()
    Object.assign(currentUser, result.data)
  } catch {
    currentUser.authenticated = false
    currentUser.username = ''
  } finally {
    authChecked.value = true
  }
}

async function login() {
  // 登录成功后保存 token，并加载无人机列表。
  await run(async () => {
    const result = await authApi.login(loginForm)
    if (result.data?.token) {
      localStorage.setItem('drone-management-auth-token', result.data.token)
    }
    Object.assign(currentUser, result.data)
    await fetchDrones()
  }, '登录成功')
}

async function logout() {
  // 退出登录后清空 token、用户信息和列表。
  await authApi.logout()
  localStorage.removeItem('drone-management-auth-token')
  currentUser.authenticated = false
  currentUser.username = ''
  drones.value = []
}

async function fetchDrones() {
  // 根据 filters 查询无人机列表。
  await run(async () => {
    const result = await droneApi.list(filters)
    drones.value = result.data || []
  })
}

function startCreate() {
  // 打开新增表单。
  Object.assign(form, emptyForm())
  editor.visible = true
  editor.mode = 'create'
  editor.id = null
  message.value = ''
}

function editDrone(drone) {
  // 把当前行数据复制到表单里，进入编辑模式。
  Object.assign(form, drone)
  editor.visible = true
  editor.mode = 'edit'
  editor.id = drone.id
  message.value = ''
}

async function generatePreview() {
  // 新增时根据名称生成 AI 属性预览。
  if (!form.name) {
    showMessage('请先填写名称', 'error')
    return
  }
  await run(async () => {
    const result = await droneApi.aiPreview(form.name)
    Object.assign(form, result.data)
  }, '属性已生成')
}

async function saveDrone() {
  // 保存时根据 mode 判断是新增还是修改。
  await run(async () => {
    if (editor.mode === 'create') {
      await droneApi.create({ name: form.name, status: form.status })
    } else {
      await droneApi.update(editor.id, form)
    }
    closeEditor()
    await fetchDrones()
  }, '保存成功')
}

async function deleteDrone(drone) {
  // 删除前弹窗确认，防止误删。
  const confirmed = window.confirm(`确认删除 ${drone.name}？`)
  if (!confirmed) {
    return
  }
  await run(async () => {
    await droneApi.remove(drone.id)
    await fetchDrones()
  }, '删除成功')
}

function closeEditor() {
  // 关闭右侧表单。
  editor.visible = false
}

async function run(task, successMessage) {
  // 统一处理 loading、成功提示和错误提示。
  loading.value = true
  message.value = ''
  try {
    await task()
    if (successMessage) {
      showMessage(successMessage, 'success')
    }
  } catch (error) {
    showMessage(error.message, 'error')
  } finally {
    loading.value = false
  }
}

function showMessage(text, type) {
  // 显示页面提示。
  message.value = text
  messageType.value = type
}

function emptyForm() {
  // 返回一份空表单，新增或重置时使用。
  return {
    name: '',
    model: '',
    manufacturer: '',
    serialNumber: '',
    droneType: '',
    maxFlightTimeMinutes: '',
    maxSpeedKmh: '',
    maxAltitudeMeters: '',
    payloadCapacityKg: '',
    batteryCapacityMah: '',
    status: '待检',
    aiProfile: ''
  }
}
</script>
