<template>
  <div class="detailed">
    <SpinnerComponent :visible="loading" />
    <h2>Detailed Info</h2>

    <form class="form" @submit.prevent="onSubmit">
      <!-- Date of Birth -->
      <div class="row">
        <label for="dob">Date of Birth</label>
        <input id="dob" type="date" v-model="dob" />
      </div>

      <!-- Gender -->
      <div class="row">
        <label for="gender">Gender</label>
        <select id="gender" v-model="gender">
          <option value="" disabled>Select</option>
          <option>Female</option>
          <option>Male</option>
          <option>Other</option>
        </select>
      </div>

      <!-- Actions -->
      <div class="actions">
        <button type="button" class="btn secondary" @click="onReset">Reset</button>
        <button type="submit" class="btn primary">Save Detailed Info</button>
      </div>

      <p v-if="message" class="message">{{ message }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref, computed } from "vue"
import { useCustomerProfile } from "@/composables/useCustomerProfile.js"
import SpinnerComponent from "./SpinnerComponent.vue";

const { customer, updateCustomer } = useCustomerProfile()
const message = ref("")
const loading = ref(false);

// Bind customer fields via computed properties
const dob = computed({
  get: () => customer.value?.dateOfBirth || "",
  set: val => (customer.value.dateOfBirth = val),
})

const gender = computed({
  get: () => customer.value?.gender?.name || "",
  set: val => {
    customer.value.gender = {
      id: val === "Male" ? 1 : val === "Female" ? 2 : 3,
      name: val,
    }
  },
})

// Submit changes
const onSubmit = async () => {
  loading.value = true
  const ok = await updateCustomer()
  message.value = ok
    ? "✅ Detailed information saved successfully."
    : "❌ Failed to save info."
  setTimeout(() => (message.value = ""), 3500)
  loading.value = false
}

// Reset to current customer values
const onReset = () => {
  dob.value = customer.value?.dateOfBirth || ""
  gender.value = customer.value?.gender?.name || ""

}
</script>

<style scoped>
.detailed {
  max-width: 900px;
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
  grid-template-columns: repeat(2, 1fr);
  gap: 12px 45px;
  align-items: center;
}

.row {
  display: flex;
  flex-direction: column;
}

.row.full {
  grid-column: 1 / -1;
}

label {
  font-size: 13px;
  color: #666;
  margin-bottom: 6px;
}

input[type="text"],
input[type="date"],
select,
textarea {
  padding: 8px 10px;
  border: 1px solid #e1e1e1;
  border-radius: 8px;
  background: #fafafa;
  outline: none;
  font-size: 14px;
}

.checkbox-row {
  align-items: center;
  display: flex;
  gap: 8px;
}

.actions {
  grid-column: 1 / -1;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 8px;
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

.message {
  grid-column: 1 / -1;
  color: #2b7a2b;
  margin-top: 10px;
}

@media (max-width: 760px) {
  .form {
    grid-template-columns: 1fr;
  }
  .actions {
    justify-content: stretch;
  }
}
</style>
