<template>
  <div class="page-card">
    <!-- 页面标题 -->
    <h2 class="page-title">录入无人机</h2>
    <!-- 提示信息 -->
    <p class="text-muted small mb-4">
      提交基础信息后，系统将自动生成飞行时间、航程、重量等扩展属性。
    </p>

    <!-- 表单 -->
    <form @submit.prevent="handleSubmit">
      <!-- 型号字段 -->
      <div class="form-group">
        <label for="model">型号 <span class="text-danger">*</span></label>
        <input
          id="model"
          v-model="form.model"
          type="text"
          class="form-control"
          :class="{ error: errors.model }"
          maxlength="100"
          placeholder="请输入无人机型号"
        />
        <span v-if="errors.model" class="form-error">{{ errors.model }}</span>
      </div>
      
      <!-- 厂商字段 -->
      <div class="form-group">
        <label for="manufacturer">厂商 <span class="text-danger">*</span></label>
        <input
          id="manufacturer"
          v-model="form.manufacturer"
          type="text"
          class="form-control"
          :class="{ error: errors.manufacturer }"
          maxlength="100"
          placeholder="请输入厂商名称"
        />
        <span v-if="errors.manufacturer" class="form-error">{{ errors.manufacturer }}</span>
      </div>

      <!-- 操作按钮 -->
      <div class="d-flex">
        <button type="submit" class="btn btn-primary mr-2" :disabled="submitting">
          <span v-if="submitting" class="loading-spinner mr-2"></span>
          {{ submitting ? '提交中' : '提交' }}
        </button>
        <RouterLink to="/drones" class="btn btn-outline-secondary">取消</RouterLink>
      </div>
    </form>
  </div>
</template>

<script setup>
/**
 * 录入无人机页面组件
 * 
 * 架构层级：前端视图层（View Layer）
 * 
 * 功能：
 * 1. 提供无人机录入表单
 * 2. 收集型号和厂商信息
 * 3. 提交后系统自动生成扩展属性（AI 属性生成）
 * 4. 成功后跳转到列表页
 * 
 * API 调用：
 * - createDrone: 创建无人机
 */
import { reactive, ref } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import { createDrone } from '@/api/drone'
import { toast } from '@/utils/toast'

// 路由实例
const router = useRouter()

// 响应式数据
const submitting = ref(false)   // 提交状态

// 表单数据
const form = reactive({
  model: '',           // 型号
  manufacturer: '',    // 厂商
})

// 表单验证错误
const errors = reactive({
  model: '',
  manufacturer: ''
})

/**
 * 验证表单
 * @returns {boolean} 是否验证通过
 */
function validateForm() {
  // 重置错误
  errors.model = ''
  errors.manufacturer = ''
  
  let isValid = true
  
  if (!form.model.trim()) {
    errors.model = '请输入型号'
    isValid = false
  } else if (form.model.length > 100) {
    errors.model = '型号长度不能超过100个字符'
    isValid = false
  }
  
  if (!form.manufacturer.trim()) {
    errors.manufacturer = '请输入厂商'
    isValid = false
  } else if (form.manufacturer.length > 100) {
    errors.manufacturer = '厂商长度不能超过100个字符'
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
    toast.warning('请填写必要信息')
    return
  }
  
  submitting.value = true
  
  try {
    // 调用 API 创建无人机
    await createDrone({ ...form })
    toast.success('录入成功')
    // 成功后跳转到列表页
    router.push('/drones')
  } catch (e) {
    toast.error(e.message)
  } finally {
    submitting.value = false
  }
}
</script>
