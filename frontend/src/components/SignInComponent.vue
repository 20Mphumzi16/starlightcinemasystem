<template>
  <div class="login-modal">
    <SpinnerComponent :visible="loading" />
    <div class="login-card">
      <!-- Company Logo -->
      <img class="company-logo" :src="logo" alt="Company Logo" />
      <!-- Close Button -->
      <button class="close-btn" @click="$emit('close')">&times;</button>

      <!-- Title -->
      <h2 class="title">Sign in</h2>

      <!-- Email Input -->
      <input
        v-model="form.email"
        type="email"
        placeholder="Email address"
        class="input-field"
      />

      <!-- Password Input with Eye Icon -->
      <div class="password-field-wrapper">
        <input
          v-model="form.password"
          :type="showPassword ? 'text' : 'password'"
          placeholder="Password"
          class="input-field password-input"
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

      <!-- Sign In Button -->
      <button class="sign-in-btn" @click="signIn">Sign In</button>

      <!-- Links -->
      <div class="links">
        <div class="link-block">
          <p>Don’t have an SK Club profile yet?</p>
          <!-- 🔹 Switch to signup modal -->
          <a href="#" @click.prevent="$emit('switch-to-signup')">
            Create one now &gt;
          </a>
        </div>
        <a href="#" class="forgot-password">Forgot password &gt;</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from "vue";
import { useRouter } from "vue-router";
import { userApi } from "@/api/user.js";
import SpinnerComponent from "@/components/SpinnerComponent.vue";

const emit = defineEmits(["close", "switch-to-signup"]);
const router = useRouter();

const form = ref({
  email: "",
  password: "",
});

const showPassword = ref(false);
const loading = ref(false);

const logo = require("@/assets/logo.png");

function toggleShowPassword() {
  showPassword.value = !showPassword.value;
}

async function signIn() {
  if (!form.value.email || !form.value.password) {
    alert("Please enter both email and password.");
    return;
  }

  try {
    loading.value = true;

    // 🔹 Call login API
    const response = await userApi.login(form.value.email, form.value.password);
    console.log("Login response:", response.data);
   
    if (!response.data || !response.data.id) {
      throw new Error("Invalid login response");
    }


    const customerId = response.data.id;

    // 🔹 Save customerId
    localStorage.setItem("customerId", customerId);

    alert("Signed in successfully!");

    emit("signed-in", customerId);

    emit("close");
    router.push("/"); // redirect after login
  } catch (err) {
    console.error("Login failed:", err);
    alert("Invalid email or password.");
  } finally {
    loading.value = false;
  }
}
</script>

<style scoped>
.login-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(6px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.login-card {
  background: #fff;
  padding: 20px;
  width: 400px;
  border-radius: 8px;
  position: relative;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  box-sizing: border-box;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.company-logo {
  width: 100px;
  margin-bottom: 16px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 12px;
  border: none;
  background: none;
  font-size: 20px;
  cursor: pointer;
  z-index: 2;
}

.title {
  margin-bottom: 20px;
  font-size: 22px;
  font-weight: bold;
  color: #222;
  width: 100%;
  z-index: 1;
}

.input-field {
  width: 100%;
  padding: 12px;
  margin: 8px 0;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 16px;
  box-sizing: border-box;
}

.sign-in-btn {
  width: 100%;
  padding: 12px;
  margin-top: 10px;
  background-color: #0070c9;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 15px;
  font-weight: bold;
}

.sign-in-btn:hover {
  background-color: #005ea8;
}

.links {
  margin-top: 15px;
  font-size: 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  gap: 6px;
}

.link-block {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.links p {
  margin: 0;
  padding: 0;
  color: #222;
}

.links a {
  color: #0070c9;
  text-decoration: none;
  display: block;
  margin-top: 4px;
}

.forgot-password {
  display: block;
  margin-top: 8px;
}

.password-field-wrapper {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
}

.password-input {
  padding-right: 38px;
}

.eye-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  display: flex;
  align-items: center;
  height: 22px;
  color: #888;
  z-index: 1;
}
</style>
