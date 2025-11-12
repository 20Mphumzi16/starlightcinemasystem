<template>
  <div class="admin-layout">
    <AdminSideBar class="sidebar" />

    <div class="main-content">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import AdminSideBar from '@/components/AdminSideBar.vue'
</script>

<style scoped>
.admin-layout {
  display: flex;
  height: 100vh;
  background: #f8f9fa;
  color: #333;
  font-family: 'Inter', sans-serif;
}

.sidebar {
  width: 240px;
  background: #111;
  color: #fff;
  padding: 20px 0;
}

.main-content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}
</style>
