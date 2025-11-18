<template>
  <div class="admin-sidebar">
    <h2 class="logo">🎬 Starlight Admin</h2>

    <nav>
      <RouterLink to="/admin/dashboard" class="link" active-class="active">Dashboard</RouterLink>
      <RouterLink to="/admin/movies" class="link" active-class="active">Movies</RouterLink>
      <RouterLink to="/admin/shows" class="link" active-class="active">Shows</RouterLink>
      <RouterLink to="/admin/genres" class="link" active-class="active">Genres</RouterLink>
    </nav>

    <!-- Logout Section -->
    <div class="logout-section">
      <button class="logout-btn" @click="logoutAdmin">Log Out</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { onMounted } from 'vue'

const router = useRouter()

// ✅ Check if admin is logged in when component mounts
onMounted(() => {
  const adminId = localStorage.getItem('adminId')
  if (!adminId) {
    router.push('/admin/login')
  }
})

function logoutAdmin() {
  if (confirm('Are you sure you want to log out?')) {
    localStorage.removeItem('adminId')
    localStorage.removeItem('adminEmail')
    localStorage.removeItem('adminToken')
    alert('You have been logged out.')
    router.push('/admin/login')
  }
}
</script>

<style scoped>
.admin-sidebar {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.logo {
  text-align: center;
  font-size: 1.4rem;
  margin-bottom: 1.5rem;
  font-weight: bold;
}

nav {
  display: flex;
  flex-direction: column;
}

.link {
  padding: 12px 20px;
  color: #ddd;
  text-decoration: none;
  font-weight: 500;
  transition: 0.2s;
}

.link:hover {
  background: #222;
}

.active {
  background: #0d6efd;
  color: #fff;
}

.logout-section {
  margin-top: auto;
  padding: 20px;
  border-top: 1px solid #333;
}

.logout-btn {
  width: 100%;
  background: #d9534f;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 10px 0;
  font-weight: bold;
  cursor: pointer;
  transition: 0.2s;
}

.logout-btn:hover {
  background: #c9302c;
}
</style>
