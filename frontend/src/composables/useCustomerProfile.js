// src/composables/useCustomerProfile.js
import { ref, onMounted } from "vue"
import { userApi } from "@/api/user.js"


export function useCustomerProfile() {
  const customer = ref(null)

  const loadCustomer = async () => {
    const customerId = localStorage.getItem("customerId")
    if (!customerId) return

    try {
      const { data } = await userApi.getCustomer(customerId)
      customer.value = data
    } catch (err) {
      console.error("Failed to load customer:", err)
    }
  }

  const updateCustomer = async () => {
    if (!customer.value) return false
    try {
      await userApi.updateCustomer(customer.value)
      return true
    } catch (err) {
      console.error("Failed to update customer:", err)
      return false
    }
  }

  onMounted(loadCustomer)

  return {
    customer,
    updateCustomer,
  }
}
