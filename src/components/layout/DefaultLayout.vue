<template>
  <AppLayout :title="pageTitle">
    <RouterView />
  </AppLayout>
</template>

<script setup>
import { computed } from 'vue'
import { RouterView, useRoute } from 'vue-router'
import AppLayout from './AppLayout.vue'

const route = useRoute()
const pageTitle = computed(() => route.meta.title || '无人机管理系统')
</script>
