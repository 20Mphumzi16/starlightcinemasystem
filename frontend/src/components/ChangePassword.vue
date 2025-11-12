<template>
  <div class="change-password">
    <SpinnerComponent :visible="isSubmitting || loading" />
    <h2>Change Password</h2>

    <form class="form" @submit.prevent="onSubmit">
      <!-- Old password -->
      <div class="row">
        <label for="old">Current Password</label>
        <div class="input-wrap">
          <input
            id="old"
            :type="showOld ? 'text' : 'password'"
            v-model="oldPass"
            autocomplete="current-password"
            placeholder="Enter current password"
          />
          <button type="button" class="icon-btn" @click="showOld = !showOld">
            <i :class="showOld ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'"></i>
          </button>
        </div>
      </div>

      <!-- New password -->
      <div class="row">
        <label for="new">New Password</label>
        <div class="input-wrap">
          <input
            id="new"
            :type="showNew ? 'text' : 'password'"
            v-model="newPass"
            autocomplete="new-password"
            placeholder="At least 8 characters"
          />
          <button type="button" class="icon-btn" @click="showNew = !showNew">
            <i :class="showNew ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'"></i>
          </button>
        </div>
      </div>

      <!-- Confirm password -->
      <div class="row">
        <label for="confirm">Confirm New Password</label>
        <div class="input-wrap">
          <input
            id="confirm"
            :type="showConfirm ? 'text' : 'password'"
            v-model="confirmPass"
            autocomplete="new-password"
            placeholder="Re-enter new password"
          />
          <button type="button" class="icon-btn" @click="showConfirm = !showConfirm">
            <i :class="showConfirm ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'"></i>
          </button>
        </div>
      </div>

      <div class="row full">
        <p class="hint">Password must be at least 8 characters long.</p>
        <p v-if="error" class="error">{{ error }}</p>
        <p v-if="success" class="success">{{ success }}</p>
      </div>

      <div class="actions">
        <button type="button" class="btn secondary" @click="onReset">Reset</button>
        <button type="submit" class="btn primary" :disabled="!isValid || isSubmitting">
          Change Password
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, computed } from "vue"
import { useCustomerProfile } from "@/composables/useCustomerProfile.js"
import SpinnerComponent from "./SpinnerComponent.vue"

const { customer, updateCustomer } = useCustomerProfile()

const loading = ref(false)

const oldPass = ref("")
const newPass = ref("")
const confirmPass = ref("")

const showOld = ref(false)
const showNew = ref(false)
const showConfirm = ref(false)

const error = ref("")
const success = ref("")
const isSubmitting = ref(false)

const isValid = computed(() => {
  return (
    newPass.value.length >= 8 &&
    newPass.value === confirmPass.value &&
    oldPass.value.length > 0
  )
})

async function onSubmit() {
  error.value = ""
  success.value = ""

  if (!isValid.value) {
    error.value = "Please check your inputs."
    return
  }

  // Update the shared customer object with the new password
  customer.value.password = newPass.value
  loading.value = true
  isSubmitting.value = true
  const ok = await updateCustomer()
  isSubmitting.value = false
  loading.value = false

  if (ok) {
    success.value = "✅ Password changed successfully."
    onReset()
  } else {
    error.value = "❌ Failed to change password."
  }
}

function onReset() {
  oldPass.value = ""
  newPass.value = ""
  confirmPass.value = ""
  showOld.value = showNew.value = showConfirm.value = false
  error.value = ""
}
</script>


<style scoped>
.change-password {
  max-width: 700px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.06);
}

h2 {
  margin: 0 0 12px 0;
  color: #333;
}

.form {
  display: grid;
  grid-template-columns: 1fr;
  gap: 12px;
}

.row {
  display: flex;
  flex-direction: column;
}

.row.full {
  margin-top: 6px;
}

label {
  font-size: 13px;
  color: #666;
  margin-bottom: 6px;
}

.input-wrap {
  position: relative;
  display: flex;
  align-items: center;
}

input[type="password"],
input[type="text"] {
  padding: 10px 40px 10px 10px;
  border: 1px solid #e1e1e1;
  border-radius: 8px;
  background: #fafafa;
  width: 100%;
  font-size: 14px;
}

.icon-btn {
  position: absolute;
  right: 6px;
  top: 50%;
  transform: translateY(-50%);
  background: transparent;
  border: none;
  cursor: pointer;
  font-size: 14px;
  color: #666;
  padding: 6px;
}

.hint {
  color: #888;
  font-size: 13px;
}

.error {
  color: #b33;
  font-size: 14px;
  margin-top: 6px;
}

.success {
  color: #0b84d0;
  font-size: 14px;
  margin-top: 6px;
}

.actions {
  display: flex;
  gap: 12px;
  justify-content: flex-end;
  margin-top: 6px;
}

.btn {
  padding: 10px 16px;
  border-radius: 22px;
  border: none;
  cursor: pointer;
  font-weight: 600;
}

.btn.primary {
  background: #0b84d0;
  color: white;
}

.btn.secondary {
  background: #f1f1f1;
  color: #333;
}

.btn[disabled] {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
