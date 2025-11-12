<template>
  <div  v-if="customer">
     <SpinnerComponent :visible="loading" />
    
    <form class="form" @submit.prevent="onSubmit">
      <label>First Name</label>
      <input type="text" v-model="customer.name.firstName" />

      <label>Last Name</label>
      <input type="text" v-model="customer.name.lastName" />

      <label>Email</label>
      <input type="email" v-model="customer.email" />

      <label>Cellphone</label>
      <input type="text" v-model="customer.phoneNumber" />

      <button type="submit" class="btn">UPDATE PROFILE</button>
      <p v-if="message" class="message">{{ message }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useCustomerProfile } from "@/composables/useCustomerProfile.js"
import SpinnerComponent from "./SpinnerComponent.vue";
const loading = ref(false);
const { customer, updateCustomer } = useCustomerProfile()
const message = ref("")

const onSubmit = async () => {
  try {
    loading.value = true
    await updateCustomer()
    message.value = "✅ Profile updated successfully."
    setTimeout(() => (message.value = ""), 3500)
  } catch (err) {
    console.error("❌ Failed to update profile:", err)
    message.value = "❌ Failed to update profile."
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.form {
  display: grid;
  gap: 15px;
  max-width: 500px;
}

input, select {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.checkbox {
  display: flex;
  align-items: center;
  gap: 8px;
}

.btn {
  background: #0b84d0;
  border: none;
  padding: 10px 20px;
  border-radius: 6px;
  color: white;
  cursor: pointer;
}

.message {
  color: #0b84d0;
  margin-top: 10px;
}
</style>
