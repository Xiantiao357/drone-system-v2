<template>
  <header class="app-header">
    <!-- 页面标题 -->
    <h1 class="h5 mb-0 text-dark">{{ title }}</h1>
  </header>
</template>

<script setup>
/**
 * 顶部导航栏组件
 * 
 * 架构层级：前端布局层（Layout Layer）
 * 
 * 功能：显示当前页面标题，作为页面头部导航区域
 */
defineProps({
  title: {
    type: String,
    required: true,
    description: '页面标题文本',
  },
})
</script>
