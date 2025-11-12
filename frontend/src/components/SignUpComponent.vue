<template>
  <div class="signup-modal">
        <SpinnerComponent :visible="loading" />
    <div class="signup-card">
      <!-- Header -->
      <div class="header">
        <h2>Create SK Club Profile</h2>
        <button class="close-btn" @click="$emit('close')">
          <i class="fa-solid fa-xmark"></i>
        </button>
      </div>
      <hr />

      <!-- Form -->
      <form class="signup-form" @submit.prevent="submitForm">
        <!-- First + Last Name -->
        <label>
          First Name*
          <input v-model="form.firstName" type="text" required />
        </label>
        <label>
          Last Name*
          <input v-model="form.lastName" type="text" required />
        </label>

        <!-- Email -->
        <label>
          Email*
          <input v-model="form.email" type="email" required />
        </label>

        <!-- Passwords -->
        <label>
          Password*
          <div class="password-wrapper">
            <input
              :type="showPassword ? 'text' : 'password'"
              v-model="form.password"
              required
            />
            <i
              :class="showPassword ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'"
              @click="togglePassword"
            ></i>
          </div>
        </label>

        <label>
          Confirm Password*
          <div class="password-wrapper">
            <input
              :type="showConfirm ? 'text' : 'password'"
              v-model="form.confirmPassword"
              required
            />
            <i
              :class="showConfirm ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'"
              @click="toggleConfirm"
            ></i>
          </div>
        </label>

        <!-- Gender -->
        <label>
          Gender*
          <select v-model="form.gender" required>
            <option disabled value="">Select Gender</option>
            <option>Male</option>
            <option>Female</option>
            <option>Other</option>
          </select>
        </label>

        <!-- Phone -->
        <label>
          Phone*
          <input v-model="form.phone" type="tel" required />
        </label>

        <!-- Birthdate -->
        <label>
          Birthdate*
          <input v-model="form.birthdate" type="date" required />
        </label>

  
        <label class="checkbox">
          <input type="checkbox" v-model="form.agree" required />
          By creating an SK profile and joining SK Club, you agree to our
          <a href="#">Ts & Cs</a> and <a href="#">SK Club Ts & Cs</a>
        </label>

        <!-- Submit -->
        <button type="submit" class="submit-btn">Create SK Club Profile</button>

        <!-- Footer -->
        <p class="footer">
          Already have an SK profile?
          <a href="#" @click.prevent="$emit('switch-to-signin')">sign in ></a>
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>

import { ref, defineEmits } from "vue";
import { useRouter } from 'vue-router';
import { userApi } from "@/api/user.js";
import { wishlistApi } from "@/api/wishlist.js";
import SpinnerComponent from "@/components/SpinnerComponent.vue";


const router = useRouter();
const emit = defineEmits(['close', 'switch-to-signin']);

const form = ref({
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  confirmPassword: "",
  gender: "",
  phone: "",
  birthdate: "",
  permission: false,
  agree: false,
});

const showPassword = ref(false);
const loading = ref(false);
const showConfirm = ref(false);

function togglePassword() {
  showPassword.value = !showPassword.value;
}

function toggleConfirm() {
  showConfirm.value = !showConfirm.value;
}

async function submitForm() {
  if (form.value.password !== form.value.confirmPassword) {
    alert("Passwords do not match!");
    return;
  }
  if (!form.value.agree) {
    alert("You must agree to the terms and conditions.");
    return;
  }

  // 🔹 Transform form into Customer structure
  const customerPayload = {
    id: null, // backend will generate
    name: {
      firstName: form.value.firstName,
      middleName: "", // optional
      lastName: form.value.lastName,
    },
    photo: null,
    gender: {
      id: form.value.gender === "Male" ? 1 : form.value.gender === "Female" ? 2 : 3,
      name: form.value.gender,
    },
    email: form.value.email,
    password: form.value.password,
    phoneNumber: form.value.phone,
    dateOfBirth: form.value.birthdate,
    roles: [
      {
        id: 1,
        name: "Customer",
      },
    ],
  };
  // 🔹 Call API to register
  try {
        loading.value = true; 
    console.log("Registering customer:", customerPayload);
    
    const response = await userApi.createCustomer(customerPayload);
    const customerId = response.data.id;  

    const responseWishlist = await wishlistApi.assignWishlistToCustomer(customerId);
    console.log("Wishlist assigned:", responseWishlist.data);
    
    localStorage.setItem("customerId", customerId);

    console.log("Customer registered:", response.data);
    alert("Profile created successfully!");

    emit('close');
     emit('signed-in', customerId); // 🔑 tell parent to reload NavBar
    router.push('/'); // Redirect to home or desired page

  } catch (err) {
    console.error(err);
    alert("Registration failed!");
  }
  finally {
    loading.value = false; 
  }
}
</script>

<style scoped>
.signup-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.6);
  backdrop-filter: blur(6px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}
.signup-card {
  background: white;
  padding: 24px;
  border-radius: 16px;
  width: 100%;
  max-width: 420px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.25);
  animation: fadeIn 0.3s ease;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  scrollbar-gutter: stable; /* keeps layout from shifting */
}

/* Smooth, minimal scrollbar */
.signup-card::-webkit-scrollbar {
  width: 8px;
}

.signup-card::-webkit-scrollbar-track {
  background: transparent;
  border-radius: 10px;
}

.signup-card::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 10px;
}

.signup-card::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.35);
}

/* Firefox */
.signup-card {
  scrollbar-width: thin;
  scrollbar-color: rgba(0, 0, 0, 0.2) transparent;
}


.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.header h2 {
  font-size: 18px;
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-top: 12px;
  width: 100%;
  box-sizing: border-box;
}

.signup-form label {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-size: 14px;
  color: #222;
  width: 100%;
  margin-bottom: 0;
  gap: 4px;
}

input,
select {
  padding: 10px;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 14px;
  width: 100%;
  box-sizing: border-box;
}

.password-wrapper {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
}

.password-wrapper input {
  padding-right: 38px;
}

.password-wrapper i {
  position: absolute;
  top: 50%;
  right: 12px;
  transform: translateY(-50%);
  cursor: pointer;
  color: gray;
}

.checkbox {
  font-size: 12px;
  line-height: 1.4;
  align-items: flex-start;
  gap: 0;
}

.checkbox a {
  color: var(--primary-blue);
  text-decoration: none;
}

.submit-btn {
  background: var(--primary-blue);
  color: white;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 15px;
  font-weight: bold;
  cursor: pointer;
  width: 100%;
}

.footer {
  font-size: 13px;
  text-align: center;
  margin-top: 8px;
  width: 100%;
}

.footer a {
  color: var(--primary-blue);
  text-decoration: none;
  font-weight: bold;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>
