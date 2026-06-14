<template>
  <div class="page-card">
    <!-- 页面头部：标题和操作按钮 -->
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
      <div>
        <h2 class="page-title mb-0">无人机列表</h2>
        <p class="text-muted small mt-1">共 {{ total }} 架无人机</p>
      </div>
      <RouterLink to="/drones/create" class="btn btn-primary btn-sm">
        录入无人机
      </RouterLink>
    </div>

    <!-- 搜索表单 -->
    <form class="form-inline mb-3" @submit.prevent="handleSearch">
      <input
        v-model="keyword"
        type="text"
        class="form-control form-control-sm mr-2 mb-2"
        placeholder="搜索型号或厂商"
      />
      <button type="submit" class="btn btn-outline-secondary btn-sm mb-2 mr-2">
        搜索
      </button>
      <button
        type="button"
        class="btn btn-link btn-sm mb-2 p-0"
        @click="handleReset"
      >
        重置
      </button>
    </form>

    <!-- 错误提示和加载状态 -->
    <div v-if="loading" class="loading-overlay">
      <span class="loading-spinner"></span>
    </div>

    <!-- 数据表格或空状态 -->
    <div v-else>
      <div v-if="items.length === 0" class="table-responsive">
        <EmptyState 
          title="暂无无人机" 
          description="还没有录入任何无人机数据"
        >
          <template #actions>
            <RouterLink to="/drones/create" class="btn btn-primary">
              录入无人机
            </RouterLink>
          </template>
        </EmptyState>
      </div>
      <div v-else class="table-responsive">
        <table class="table table-hover table-sm mb-0">
          <thead class="thead-light">
            <tr>
              <th>ID</th>
              <th>型号</th>
              <th>厂商</th>
              <th>最大飞行时间(min)</th>
              <th>最大航程(m)</th>
              <th>重量(kg)</th>
              <th>载重(kg)</th>
              <th>创建时间</th>
              <th class="text-right">操作</th>
            </tr>
          </thead>
          <tbody>
            <!-- 数据行 -->
            <tr v-for="drone in items" :key="drone.id">
              <td>{{ drone.id }}</td>
              <td>{{ drone.model }}</td>
              <td>{{ drone.manufacturer }}</td>
              <td>{{ drone.maxFlightTime ?? '—' }}</td>
              <td>{{ drone.maxRange ?? '—' }}</td>
              <td>{{ drone.weight ?? '—' }}</td>
              <td>{{ drone.payloadCapacity ?? '—' }}</td>
              <td>{{ formatDateTime(drone.createdAt) }}</td>
              <td class="text-right text-nowrap">
                <RouterLink
                  :to="`/drones/${drone.id}/edit`"
                  class="btn btn-outline-primary btn-sm mr-1"
                >
                  编辑
                </RouterLink>
                <button
                  type="button"
                  class="btn btn-outline-danger btn-sm"
                  @click="handleDelete(drone)"
                >
                  删除
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
/**
 * 无人机列表页面组件
 * 
 * 架构层级：前端视图层（View Layer）
 * 
 * 功能：
 * 1. 展示所有无人机列表数据（不分页）
 * 2. 支持关键字搜索（型号/厂商）
 * 3. 提供编辑和删除操作
 * 
 * API 调用：
 * - fetchDroneList: 获取无人机列表
 * - deleteDrone: 删除无人机
 */
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'
import { deleteDrone, fetchDroneList } from '@/api/drone'
import { toast } from '@/utils/toast'
import EmptyState from '@/components/EmptyState.vue'

// 响应式数据
const items = ref([])        // 无人机列表数据
const total = ref(0)         // 总记录数
const keyword = ref('')      // 搜索关键字
const loading = ref(false)   // 加载状态

/**
 * 格式化日期时间
 * @param {string} datetime - 日期时间字符串
 * @returns {string} 格式化后的日期时间
 */
function formatDateTime(datetime) {
  if (!datetime) return '—'
  try {
    const date = new Date(datetime)
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch {
    return datetime
  }
}

/**
 * 加载无人机列表
 */
async function loadList() {
  loading.value = true
  try {
    const res = await fetchDroneList({
      keyword: keyword.value.trim() || undefined,
    })
    // 处理两种响应格式：
    // 1. 分页模式：{ items: [...], total: n }
    // 2. 不分页模式：直接返回数组
    if (Array.isArray(res.data)) {
      // 不分页模式
      items.value = res.data
      total.value = res.data.length
    } else {
      // 分页模式
      items.value = res.data?.items ?? []
      total.value = res.data?.total ?? 0
    }
  } catch (e) {
    toast.error(e.message)
    items.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

/**
 * 处理搜索
 */
function handleSearch() {
  loadList()
}

/**
 * 重置搜索条件
 */
function handleReset() {
  keyword.value = ''
  loadList()
}

/**
 * 处理删除操作
 * @param {Object} drone - 无人机数据
 */
async function handleDelete(drone) {
  if (!window.confirm(`确定删除「${drone.model}」吗？`)) return
  try {
    await deleteDrone(drone.id)
    toast.success('删除成功')
    await loadList()
  } catch (e) {
    toast.error(e.message)
  }
}

// 组件挂载时加载数据
onMounted(loadList)
</script>
