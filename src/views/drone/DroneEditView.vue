<template>
  <div class="page-card">
    <!-- 页面标题 -->
    <h2 class="page-title">编辑无人机</h2>

    <!-- 加载状态 -->
    <div v-if="loading" class="loading-overlay">
      <span class="loading-spinner"></span>
    </div>

    <!-- 表单 -->
    <form v-else @submit.prevent="handleSubmit">
      <!-- 型号字段 -->
      <FormField
        id="model"
        label="型号"
        v-model="form.model"
        maxlength="100"
        placeholder="请输入无人机型号"
        :error="errors.model"
      />
      
      <!-- 厂商字段 -->
      <FormField
        id="manufacturer"
        label="厂商"
        v-model="form.manufacturer"
        maxlength="100"
        placeholder="请输入厂商名称"
        :error="errors.manufacturer"
      />
      
      <!-- 扩展属性字段（两行两列布局） -->
      <div class="form-row">
        <div class="form-group col-md-6">
          <label for="maxFlightTime">最大飞行时间 (分钟)</label>
          <input
            id="maxFlightTime"
            v-model.number="form.maxFlightTime"
            type="number"
            min="0"
            class="form-control"
            :class="{ error: errors.maxFlightTime }"
          />
          <span v-if="errors.maxFlightTime" class="form-error">{{ errors.maxFlightTime }}</span>
        </div>
        <div class="form-group col-md-6">
          <label for="maxRange">最大航程 (米)</label>
          <input
            id="maxRange"
            v-model.number="form.maxRange"
            type="number"
            min="0"
            class="form-control"
            :class="{ error: errors.maxRange }"
          />
          <span v-if="errors.maxRange" class="form-error">{{ errors.maxRange }}</span>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group col-md-6">
          <label for="weight">重量 (kg)</label>
          <input
            id="weight"
            v-model.number="form.weight"
            type="number"
            min="0"
            step="0.01"
            class="form-control"
            :class="{ error: errors.weight }"
          />
          <span v-if="errors.weight" class="form-error">{{ errors.weight }}</span>
        </div>
        <div class="form-group col-md-6">
          <label for="payloadCapacity">载重 (kg)</label>
          <input
            id="payloadCapacity"
            v-model.number="form.payloadCapacity"
            type="number"
            min="0"
            step="0.01"
            class="form-control"
            :class="{ error: errors.payloadCapacity }"
          />
          <span v-if="errors.payloadCapacity" class="form-error">{{ errors.payloadCapacity }}</span>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="d-flex">
        <button type="submit" class="btn btn-primary mr-2" :disabled="submitting">
          <span v-if="submitting" class="loading-spinner mr-2"></span>
          {{ submitting ? '保存中' : '保存' }}
        </button>
        <RouterLink to="/drones" class="btn btn-outline-secondary">取消</RouterLink>
      </div>
    </form>
  </div>
</template>

<script setup>
/**
 * 编辑无人机页面组件
 * 
 * 架构层级：前端视图层（View Layer）
 * 
 * 功能：
 * 1. 根据路由参数加载无人机详情
 * 2. 展示完整的无人机信息编辑表单
 * 3. 支持修改所有字段（型号、厂商、扩展属性）
 * 4. 保存后跳转到列表页
 * 
 * API 调用：
 * - fetchDroneById: 获取无人机详情
 * - updateDrone: 更新无人机信息
 */
import { onMounted, reactive, ref } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { fetchDroneById, updateDrone } from '@/api/drone'
import { toast } from '@/utils/toast'
import FormField from '@/components/FormField.vue'

// 路由实例
const route = useRoute()  // 获取路由参数
const router = useRouter()

// 响应式数据
const loading = ref(true)     // 加载状态
const submitting = ref(false) // 提交状态

// 表单数据
const form = reactive({
  model: '',                 // 型号
  manufacturer: '',          // 厂商
  maxFlightTime: null,       // 最大飞行时间（分钟）
  maxRange: null,            // 最大航程（米）
  weight: null,              // 重量（kg）
  payloadCapacity: null,     // 载重（kg）
})

// 表单验证错误
const errors = reactive({
  model: '',
  manufacturer: '',
  maxFlightTime: '',
  maxRange: '',
  weight: '',
  payloadCapacity: ''
})

/**
 * 加载无人机详情
 */
async function loadDrone() {
  loading.value = true
  try {
    // 根据 ID 获取无人机详情
    const res = await fetchDroneById(route.params.id)
    const drone = res.data
    // 填充表单
    form.model = drone.model ?? ''
    form.manufacturer = drone.manufacturer ?? ''
    form.maxFlightTime = drone.maxFlightTime ?? null
    form.maxRange = drone.maxRange ?? null
    form.weight = drone.weight ?? null
    form.payloadCapacity = drone.payloadCapacity ?? null
  } catch (e) {
    toast.error(e.message)
  } finally {
    loading.value = false
  }
}

/**
 * 验证表单
 * @returns {boolean} 是否验证通过
 */
function validateForm() {
  // 重置错误
  Object.keys(errors).forEach(key => errors[key] = '')
  
  let isValid = true
  
  if (form.model && form.model.length > 100) {
    errors.model = '型号长度不能超过100个字符'
    isValid = false
  }
  
  if (form.manufacturer && form.manufacturer.length > 100) {
    errors.manufacturer = '厂商长度不能超过100个字符'
    isValid = false
  }
  
  if (form.maxFlightTime !== null && form.maxFlightTime < 0) {
    errors.maxFlightTime = '飞行时间不能为负数'
    isValid = false
  }
  
  if (form.maxRange !== null && form.maxRange < 0) {
    errors.maxRange = '航程不能为负数'
    isValid = false
  }
  
  if (form.weight !== null && form.weight < 0) {
    errors.weight = '重量不能为负数'
    isValid = false
  }
  
  if (form.payloadCapacity !== null && form.payloadCapacity < 0) {
    errors.payloadCapacity = '载重不能为负数'
    isValid = false
  }
  
  return isValid
}

/**
 * 处理表单提交
 */
async function handleSubmit() {
  // 前端验证
  if (!validateForm()) {
    toast.warning('请检查输入内容')
    return
  }
  
  submitting.value = true
  try {
    // 调用 API 更新无人机
    await updateDrone(route.params.id, { ...form })
    toast.success('保存成功')
    // 成功后跳转到列表页
    router.push('/drones')
  } catch (e) {
    toast.error(e.message)
  } finally {
    submitting.value = false
  }
}

// 组件挂载时加载数据
onMounted(loadDrone)
</script>
