<template>
  <div class="payment-page">
    <!-- Navbar -->
    <NavBar
      @sign-in="showModal = true; activeModal = 'signin'"
      @sign-up="showModal = true; activeModal = 'signup'"
      @open-search="showSearch = true"
    />

    <SpinnerComponent :visible="loading" />
    <!-- Search Overlay -->
    <SearchOverlay v-if="showSearch" @close="showSearch = false" />

    <!-- Page grid -->
   <div class="payment-grid" v-if="bookingStore.show">
      <!-- LEFT: Order Summary -->
      <div class="left-col">
        <div class="order-summary">
          <h2 class="section-title">Review your order</h2>
          <p class="timer">Time remaining: <span>12m 37s</span></p>

          <!-- 🎬 Show Details -->
          <div class="movie-info">
            <img
              :src="show.movie?.posterUrl || require('@/assets/movie1.jpg')"
              alt="Movie Poster"
              class="poster"
            />
            <div>
              <h3 class="movie-title">{{ show.movie?.title }}</h3>
       <p v-if="show?.hall?.branch">Cinema: {{ show.hall.branch.name }}</p>
<p v-else>Cinema: Unknown Cinema</p>
              <p>{{ formatDate(show.startTime) }} – {{ formatDate(show.endTime) }}</p>
            </div>
          </div>

          <!-- 🪑 Selected Seats -->
          <div v-for="seat in selectedSeats" :key="seat.id" class="ticket-details">
            <p>1× {{ show.screenType }} ticket – Seat {{ seat.seatNumber }}</p>
            <p class="price">R {{ show.price.toFixed(2) }}</p>
          </div>

          <div class="total">
            <span>Total:</span>
            <span class="price">R {{ total.toFixed(2) }}</span>
          </div>

          <router-link to="/seats">
            <button class="change-seat">Change Your Seat</button>
          </router-link>

          <!-- 🍿 Extras -->
          <div class="extras">
            <h3>Add Extras?</h3>
            <div class="extras-grid">
              <div class="extra-item" v-for="combo in combos" :key="combo.name">
                <img :src="combo.image" alt="Combo" />
                <p>{{ combo.name }}</p>
                <p>R {{ combo.price }}</p>
                <button @click="addExtra(combo)">+ Add To Basket</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- RIGHT: Payment Form -->
      <div class="right-col">
        <div class="payment-box">
          <h2>Please provide details</h2>
          <input v-model="user.name" type="text" placeholder="Name" />
          <input v-model="user.phone" type="text" placeholder="Phone number" />
          <input v-model="user.email" type="email" placeholder="Email address" />

          <div class="club-points">
            <input type="text" placeholder="Scene Club card number" />
            <button>Earn</button>
          </div>

          <label class="newsletter">
            <input type="checkbox" v-model="user.subscribe" />
            Sign up for the Starlight newsletter
          </label>
        </div>

        <div class="payment-box">
          <h2>Credit / debit card</h2>
          <div class="card-grid">
            <input type="text" placeholder="Cardholder First Name" />
            <input type="text" placeholder="Cardholder Last Name" />
          </div>
          <input type="text" placeholder="Card number" />

          <div class="card-grid">
            <select>
              <option>Expiry Date: Month</option>
            </select>
            <select>
              <option>Expiry Date: Year</option>
            </select>
          </div>

          <input type="text" placeholder="CVV Number" />

          <button class="pay-btn" @click="handlePayment">Pay</button>

          <div class="accepted-cards">
            <img :src="require('@/assets/amex.png')" alt="Amex" />
            <img :src="require('@/assets/mastercard.png')" alt="MasterCard" />
            <img :src="require('@/assets/visa.png')" alt="Visa" />
          </div>
        </div>
      </div>
    </div>

    <!-- Fallback -->
    <div v-else class="no-data">
      <p>No booking data found. Please go back and select your seats.</p>
      <router-link to="/shows">
        <button class="change-seat">Return to Shows</button>
      </router-link>
    </div>

    <!-- Auth Modal -->
    <div v-if="showModal" class="overlay">
      <transition name="fade-scale" mode="out-in">
        <component
          :is="activeModal === 'signin' ? SignInComponent : SignUpComponent"
          :key="activeModal"
          @close="showModal = false"
          @switch-to-signup="activeModal = 'signup'"
          @switch-to-signin="activeModal = 'signin'"
        />
      </transition>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import axios from "axios"
import { useBookingStore } from "@/stores/bookingStore.js"
import { storeToRefs } from "pinia"
import { userApi } from "@/api/user.js"

import NavBar from "@/components/NavBar.vue"
import SignInComponent from "@/components/SignInComponent.vue"
import SignUpComponent from "@/components/SignUpComponent.vue"
import SearchOverlay from "@/components/SearchOverlay.vue"
import SpinnerComponent from "@/components/SpinnerComponent.vue"

const router = useRouter()
const bookingStore = useBookingStore()
const { show, selectedSeats, total } = storeToRefs(bookingStore)

const loading = ref(false)
const showModal = ref(false)
const activeModal = ref("signin")
const showSearch = ref(false)

// ✅ define user for form binding
const user = ref({
  name: "",
  phone: "",
  email: "",
  subscribe: false,
})

const customer = ref(null)

async function loadUser() {
  const id = localStorage.getItem("customerId")
  if (id) {
    const res = await userApi.getCustomer(id)
    customer.value = res.data
  } else {
    customer.value = null
  }
}

onMounted(() => {
  console.log("Booking store show data:", show.value)
  loadUser()
})

const combos = [
  { name: "Large Combo with Slush", price: 97, image: require("@/assets/combo1.jpeg") },
  { name: "Feature Combo MEDIUM", price: 110, image: require("@/assets/combo2.jpeg") },
  { name: "Feature Combo LARGE", price: 120, image: require("@/assets/combo3.jpeg") }
]

function addExtra(combo) {
  alert(`${combo.name} added to basket!`)
}

function formatDate(dateStr) {
  if (!dateStr) return "Unknown Date"
  const date = new Date(dateStr)
  return date.toLocaleString("en-ZA", {
    weekday: "short",
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  })
}

async function handlePayment() {
  try {
    loading.value = true
    await loadUser()

    if (!show.value || selectedSeats.value.length === 0) {
      alert("No seats selected or show data missing.")
      return
    }

    const bookingResponse = await axios.post("http://localhost:8080/starlight/booking/create", {
      show: show.value,
      customer: customer.value || null,
      status: "Confirmed",
      totalPrice: total.value,
      createdAt: new Date().toISOString(),
    })

    const booking = bookingResponse.data
    const seatSelectedList = []

    for (const seat of selectedSeats.value) {
      const seatResponse = await axios.get(`http://localhost:8080/starlight/seat/read/${seat.id}`)
      const seatData = seatResponse.data
      if (seatData) seatSelectedList.push(seatData)
    }

    for (const seat of seatSelectedList) {
      await axios.post("http://localhost:8080/starlight/seat-reservation/create", {
        seat: seat,
        booking: booking,
        status: "RESERVED",
      })
    }
bookingStore.setBookingData(show.value, selectedSeats.value, total.value)

alert("🎉 Payment successful! Booking confirmed.")
router.push("/confirmation")

  } catch (error) {
    console.error("Payment failed:", error)
    alert("Something went wrong while processing your booking.")
  } finally {
    loading.value = false
  }
}
</script>



<style scoped>
.payment-page {
  background: var(--bg);
  min-height: 100vh;
  padding: 20px;
}

.payment-grid {
  display: grid;
  grid-template-columns: 2fr 1.2fr;
  gap: 32px;
  margin-top: 20px;
}

/* Order summary */
.order-summary {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 8px;
}

.timer {
  font-size: 0.9rem;
  color: #d9534f;
  margin-bottom: 16px;
}

.movie-info {
  display: flex;
  gap: 16px;
  margin-bottom: 16px;
}

.poster {
  width: 100px;
  border-radius: 8px;
}

.movie-title {
  font-weight: 600;
}

.ticket-details,
.total {
  display: flex;
  justify-content: space-between;
  margin: 12px 0;
}

.price {
  font-weight: 600;
}

.change-seat {
  width: 100%;
  padding: 12px;
  background: #f58220;
  color: #fff;
  border: none;
  border-radius: 8px;
  margin: 16px 0;
  cursor: pointer;
  font-weight: 600;
}

.change-seat:hover {
  background: #e57210;
}

/* Extras */
.extras h3 {
  margin-bottom: 12px;
  font-weight: 600;
}

.extras-grid {
  display: flex;
  gap: 16px;
}

.extra-item {
  text-align: center;
  background: #f9f9f9;
  padding: 12px;
  border-radius: 8px;
  flex: 1;
}

.extra-item img {
  width: 80px;
  height: auto;
  margin-bottom: 8px;
}

.extra-item button {
  margin-top: 8px;
  background: none;
  border: none;
  color: #f58220;
  font-weight: 600;
  cursor: pointer;
}

/* Payment form */
.payment-box {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.payment-box h2 {
  margin-bottom: 16px;
  font-size: 1.1rem;
  font-weight: 600;
}

.payment-box input,
.payment-box select {
  width: 100%;
  padding: 10px;
  margin-bottom: 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
}

.club-points {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.newsletter {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 0.9rem;
}

.card-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.pay-btn {
  width: 100%;
  padding: 12px;
  background: #f58220;
  color: #fff;
  border: none;
  border-radius: 8px;
  margin: 12px 0;
  cursor: pointer;
  font-weight: 600;
}

.pay-btn:hover {
  background: #e57210;
}

.accepted-cards {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

.accepted-cards img {
  width: 40px;
  height: auto;
}

.no-data {
  text-align: center;
  margin-top: 100px;
}
</style>
