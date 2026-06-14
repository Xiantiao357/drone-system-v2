<template>
  <aside class="app-sidebar">
    <!-- 系统 Logo 和名称 -->
    <div class="p-3 border-bottom border-secondary">
      <div class="text-white font-weight-bold">无人机管理系统</div>
      <small class="text-muted">Drone Management</small>
    </div>
    <!-- 导航菜单 -->
    <nav class="nav flex-column py-3">
      <RouterLink
        v-for="item in navItems"
        :key="item.to"
        :to="item.to"
        class="nav-link px-3 py-2"
        active-class="active"
      >
        {{ item.label }}
      </RouterLink>
    </nav>
  </aside>
</template>

<script setup>
/**
 * 侧边栏导航组件
 * 
 * 架构层级：前端布局层（Layout Layer）
 * 
 * 功能：
 * 1. 显示系统 Logo 和名称
 * 2. 提供页面导航菜单
 * 3. 支持路由链接激活状态高亮
 * 
 * 导航项：
 * - 无人机列表：/drones
 * - 录入无人机：/drones/create
 */
import { RouterLink } from 'vue-router'

// 导航菜单配置
const navItems = [
  { to: '/drones', label: '无人机列表' },
  { to: '/drones/create', label: '录入无人机' },
]
</script>

<style scoped>
.nav-link {
  color: var(--sidebar-text);
  border-left: 3px solid transparent;
}

.nav-link:hover {
  color: #fff;
  background: var(--sidebar-active);
}

.nav-link.active {
  color: #fff;
  background: var(--sidebar-active);
  border-left-color: var(--primary-color);
}
</style>
