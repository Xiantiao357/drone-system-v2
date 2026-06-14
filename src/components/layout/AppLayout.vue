<template>
  <div class="app-layout">
    <!-- 侧边栏导航 -->
    <AppSidebar />
    <!-- 主内容区域 -->
    <div class="app-main">
      <!-- 顶部导航栏 -->
      <AppHeader :title="title" />
      <!-- 页面内容区（通过 slot 注入子组件） -->
      <main class="app-content">
        <slot />
      </main>
    </div>
  </div>
</template>

<script setup>
/**
 * 应用主布局组件
 * 
 * 架构层级：前端布局层（Layout Layer）
 * 
 * 布局结构：
 * ┌──────────────────────────────────────┐
 * │ AppSidebar (侧边导航) │ AppMain      │
 * │                      │ ┌──────────┐ │
 * │                      │ │ AppHeader│ │
 * │                      │ └──────────┘ │
 * │                      │ ┌──────────┐ │
 * │                      │ │  slot    │ │
 * │                      │ │ (页面内容)│ │
 * │                      │ └──────────┘ │
 * └──────────────────────────────────────┘
 */
import AppHeader from './AppHeader.vue'
import AppSidebar from './AppSidebar.vue'

// 定义组件属性
defineProps({
  title: {
    type: String,
    default: '',
    description: '页面标题，显示在顶部导航栏',
  },
})
</script>
