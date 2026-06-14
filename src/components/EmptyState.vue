<template>
  <div class="empty-state">
    <div class="empty-state-icon">{{ icon }}</div>
    <h3 class="empty-state-title">{{ title }}</h3>
    <p class="empty-state-description">{{ description }}</p>
    <slot name="actions"></slot>
  </div>
</template>

<script setup>
/**
 * 空数据状态组件
 * 
 * 架构层级：前端组件层（Component Layer）
 * 
 * 功能：当列表或页面无数据时展示友好的空状态提示
 * 
 * 属性：
 * - icon: 图标（默认 📦）
 * - title: 标题
 * - description: 描述文字
 * 
 * 使用示例：
 * <EmptyState 
 *   title="暂无数据" 
 *   description="当前没有找到相关记录"
 * >
 *   <template #actions>
 *     <button class="btn btn-primary">添加数据</button>
 *   </template>
 * </EmptyState>
 */
import { computed } from 'vue'

const props = defineProps({
  icon: {
    type: String,
    default: '📦'
  },
  title: {
    type: String,
    default: '暂无数据'
  },
  description: {
    type: String,
    default: '当前没有找到相关记录'
  }
})
</script>
