<template>
  <div class="form-group">
    <label :for="id">{{ label }} <span v-if="required" class="text-danger">*</span></label>
    <input
      :id="id"
      :value="modelValue"
      :type="type"
      :class="inputClass"
      :maxlength="maxlength"
      :min="min"
      :max="max"
      :step="step"
      :placeholder="placeholder"
      :disabled="disabled"
      @blur="handleBlur"
      @input="handleInput"
    />
    <span v-if="error" class="form-error">{{ error }}</span>
  </div>
</template>

<script setup>
/**
 * 表单字段组件
 * 
 * 架构层级：前端组件层（Component Layer）
 * 
 * 功能：封装表单输入字段，提供统一的验证和样式
 * 
 * 属性：
 * - id: 字段ID
 * - label: 标签文本
 * - modelValue: 绑定的值（v-model）
 * - type: 输入类型（text, number, email等）
 * - required: 是否必填
 * - maxlength: 最大长度
 * - min: 最小值（数字类型）
 * - max: 最大值（数字类型）
 * - step: 数字步长
 * - placeholder: 占位符
 * - disabled: 是否禁用
 * - error: 错误信息
 */
import { computed } from 'vue'

const props = defineProps({
  id: {
    type: String,
    required: true
  },
  label: {
    type: String,
    required: true
  },
  modelValue: {
    type: [String, Number],
    default: ''
  },
  type: {
    type: String,
    default: 'text'
  },
  required: {
    type: Boolean,
    default: false
  },
  maxlength: {
    type: Number,
    default: null
  },
  min: {
    type: Number,
    default: null
  },
  max: {
    type: Number,
    default: null
  },
  step: {
    type: [Number, String],
    default: null
  },
  placeholder: {
    type: String,
    default: ''
  },
  disabled: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue', 'blur', 'input'])

const inputClass = computed(() => {
  return [
    'form-control',
    {
      'error': props.error,
      'disabled': props.disabled
    }
  ]
})

function handleBlur() {
  emit('blur')
}

function handleInput(event) {
  emit('update:modelValue', event.target.value)
  emit('input')
}
</script>
