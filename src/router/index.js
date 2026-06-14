/**
 * Vue Router 路由配置
 * 
 * 架构层级：前端路由层（Router Layer）
 * 
 * 路由映射表：
 * - / → 重定向到 /drones
 * - /drones → 无人机列表页
 * - /drones/create → 录入无人机页
 * - /drones/:id/edit → 编辑无人机页
 */
import { createRouter, createWebHistory } from 'vue-router'
import DefaultLayout from '@/components/layout/DefaultLayout.vue'

// 创建路由实例
const router = createRouter({
  // 使用 HTML5 History 模式
  history: createWebHistory(),
  routes: [
    // 默认重定向到无人机列表
    {
      path: '/',
      redirect: '/drones',
    },
    // 无人机管理模块（嵌套路由）
    {
      path: '/drones',
      component: DefaultLayout,
      children: [
        // 无人机列表页
        {
          path: '',
          name: 'DroneList',
          component: () => import('@/views/drone/DroneListView.vue'),
          meta: { title: '无人机列表' },
        },
        // 录入无人机页
        {
          path: 'create',
          name: 'DroneCreate',
          component: () => import('@/views/drone/DroneCreateView.vue'),
          meta: { title: '录入无人机' },
        },
        // 编辑无人机页
        {
          path: ':id/edit',
          name: 'DroneEdit',
          component: () => import('@/views/drone/DroneEditView.vue'),
          meta: { title: '编辑无人机' },
        },
      ],
    },
  ],
})

export default router
