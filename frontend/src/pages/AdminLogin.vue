<template>
  <div class="admin-login-page">
    <div class="login-box">
      <SpinnerComponent :visible="loading" />

      <!-- Logo -->
      <img :src="logo" alt="Admin Logo" class="admin-logo" />

      <h2 class="title">Admin Login</h2>

      <!-- Email -->
      <input
        v-model="form.email"
        type="email"
        placeholder="Admin Email"
        class="input-field"
      />

      <!-- Password -->
      <div class="password-wrapper">
        <input
          v-model="form.password"
          :type="showPassword ? 'text' : 'password'"
          placeholder="Password"
          class="input-field"
        />
        <span class="eye-icon" @click="toggleShowPassword">
          <svg
            v-if="showPassword"
            xmlns="http://www.w3.org/2000/svg"
            width="22"
            height="22"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              stroke="#888"
              stroke-width="2"
              d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12Z"
            />
            <circle cx="12" cy="12" r="3.5" stroke="#888" stroke-width="2" />
          </svg>
          <svg
            v-else
            xmlns="http://www.w3.org/2000/svg"
            width="22"
            height="22"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              stroke="#888"
              stroke-width="2"
              d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12Z"
            />
            <circle cx="12" cy="12" r="3.5" stroke="#888" stroke-width="2" />
            <line
              x1="4"
              y1="20"
              x2="20"
              y2="4"
              stroke="#888"
              stroke-width="2"
            />
          </svg>
        </span>
      </div>

      <button class="login-btn" @click="loginAdmin">Login</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import SpinnerComponent from '@/components/SpinnerComponent.vue'
import axios from 'axios'

const router = useRouter()

const logo = require('@/assets/logo.png') // can reuse customer logo
const loading = ref(false)
const showPassword = ref(false)
const form = ref({
  email: '',
  password: '',
})

function toggleShowPassword() {
  showPassword.value = !showPassword.value
}

async function loginAdmin() {
  if (!form.value.email || !form.value.password) {
    alert('Please enter your admin email and password.')
    return
  }

  try {
    loading.value = true
    const response = await axios.get(
      `http://localhost:8080/starlight/admin/login/${form.value.email}/${form.value.password}`
    )

    console.log('Admin login response:', response.data)

    if (!response.data || !response.data.id) {
      throw new Error('Invalid login response')
    }

    // ✅ Ensure the user has at least one admin role
    const roles = response.data.roles

    // Handle Set or Array (depending on backend structure)
    const roleList = Array.isArray(roles)
      ? roles
      : Array.from(roles || []) // convert Set to Array if needed

    const isAdmin = roleList.some(
      (r) =>
        (r.id && r.id === 2) ||
        (r.name && r.name.toLowerCase() === 'admin')
    )

    if (!isAdmin) {
      alert('Access denied: you are not an admin.')
      return
    }

    // ✅ Save admin info
    localStorage.setItem('adminId', response.data.id)
    localStorage.setItem('adminEmail', response.data.email)

    alert('Admin logged in successfully!')
    router.push('/admin')
  } catch (err) {
    console.error('Admin login failed:', err)
    alert('Invalid admin credentials.')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.admin-login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(145deg, #111, #222);
  color: #fff;
  font-family: 'Inter', sans-serif;
}

.login-box {
  background: #1c1c1c;
  padding: 40px;
  border-radius: 10px;
  width: 400px;
  text-align: center;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.4);
  position: relative;
}

.admin-logo {
  width: 90px;
  margin-bottom: 16px;
}

.title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 20px;
}

.input-field {
  width: 100%;
  padding: 12px;
  margin: 8px 0;
  border: 1px solid #444;
  border-radius: 6px;
  background: #2a2a2a;
  color: #fff;
  font-size: 15px;
}

.password-wrapper {
  position: relative;
}

.eye-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}

.login-btn {
  width: 100%;
  padding: 12px;
  margin-top: 12px;
  background-color: #0070c9;
  border: none;
  border-radius: 6px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

.login-btn:hover {
  background-color: #005ea8;
}
</style>
