<template>
  <div class="confirmation-page">
    <!-- 🎉 Success Animation -->
    <transition name="fade-scale" appear>
      <div v-if="showSuccess" class="success-container">
        <div class="checkmark">
          <svg viewBox="0 0 52 52">
            <circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none" />
            <path class="checkmark__check" fill="none" d="M14 27l7 7 16-16" />
          </svg>
          <h2>Payment Successful!</h2>
          <p>Your booking is confirmed</p>
        </div>
      </div>
    </transition>

    <!-- 🎟️ Ticket Section -->
    <transition name="fade-slide" appear>
      <div v-if="showTicket">
        <div v-if="show && show.movie" class="ticket-wrapper">
          <h1 class="movie-title">{{ show.movie.title }}</h1>

          <div class="cinema-info">
            <p>
              {{ show.hall?.branch?.name || "Unknown Cinema" }} |
              {{ show.hall?.name || "Cinema 1" }}
            </p>
            <p class="rating">{{ show.movie.rating.rating || "PG" }}</p>
            <p class="datetime">{{ formatDate(show.startTime) }}</p>
          </div>

          <hr />

          <div class="ticket-details">
            <p><strong>Confirmation Code:</strong></p>
            <p class="code">{{ confirmationCode }}</p>

            <p><strong>Purchased Ticket:</strong></p>
            <p>{{ selectedSeats.length }} tickets × R{{ show.price?.toFixed(2) || "0.00" }} each</p>

            <p><strong>Web Admin Fee:</strong></p>
            <p>R 25.00</p>

            <div class="total">
              <span>Total:</span>
              <span class="price">R {{ total.toFixed(2) }}</span>
            </div>
          </div>

          <hr />

          <div class="seats">
            <p>
              <span v-for="seat in selectedSeats" :key="seat.id" class="seat-tag">
                {{ seat.seatNumber }}
              </span>
            </p>
          </div>

          <div class="barcode">
            <img src="@/assets/barcode.jpg" alt="Barcode" />
            <p class="code-text">{{ confirmationCode }}</p>
          </div>

          <button class="home-btn" @click="goHome">Back to Home</button>
        </div>

        <!-- 🧭 fallback if show is missing -->
        <div v-else class="no-data">
          <p>No booking details available. Please return to the homepage.</p>
          <button class="home-btn" @click="goHome">Go Home</button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import { useRouter } from "vue-router"
import { useBookingStore } from "@/stores/bookingStore.js"
import { storeToRefs } from "pinia"

const router = useRouter()
const bookingStore = useBookingStore()
const { show, selectedSeats, total } = storeToRefs(bookingStore)

const confirmationCode = "TRIC" + Math.floor(Math.random() * 900000000 + 100000000) + "C"

// ✨ animation state
const showSuccess = ref(true)
const showTicket = ref(false)

onMounted(() => {
  // show success animation first, then reveal ticket
  setTimeout(() => {
    showSuccess.value = false
    showTicket.value = true
  }, 2000) // 2s success animation
})

function goHome() {
  router.push("/")
}

function formatDate(dateStr) {
  if (!dateStr) return "Unknown Date"
  const date = new Date(dateStr)
  return date.toLocaleString("en-ZA", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  })
}
</script>

<style scoped>
.confirmation-page {
  background: #f5f5f5;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 30px;
  font-family: "Poppins", sans-serif;
}

.ticket-wrapper {
  background: white;
  width: 420px;
  border-radius: 8px;
  padding: 24px 28px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  text-align: center;
  animation: fadeIn 0.6s ease-in-out;
}

.movie-title {
  font-size: 1.4rem;
  font-weight: 700;
  margin-bottom: 8px;
  text-transform: uppercase;
}

.cinema-info {
  font-size: 0.9rem;
  color: #555;
  line-height: 1.4;
  margin-bottom: 12px;
}

.rating {
  font-weight: 600;
}

.datetime {
  font-size: 0.85rem;
  color: #777;
}

.ticket-details {
  text-align: left;
  margin-top: 16px;
  line-height: 1.7;
}

.code {
  font-weight: 600;
  font-size: 0.95rem;
  letter-spacing: 0.5px;
}

.total {
  display: flex;
  justify-content: space-between;
  margin-top: 12px;
  font-weight: 600;
  border-top: 1px solid #eee;
  padding-top: 10px;
}

.price {
  color: #222;
}

.seats {
  margin-top: 18px;
}

.seat-tag {
  background: #111;
  color: white;
  padding: 6px 10px;
  border-radius: 6px;
  margin: 0 4px;
  font-size: 0.85rem;
  font-weight: 600;
}

.barcode {
  margin-top: 24px;
}

.barcode img {
  width: 80%;
  margin-bottom: 6px;
}

.code-text {
  font-size: 0.9rem;
  letter-spacing: 1px;
  color: #444;
}

.home-btn {
  margin-top: 24px;
  width: 100%;
  padding: 12px;
  background: #f58220;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.home-btn:hover {
  background: #e57210;
}

/* ✨ Animations */
.fade-slide-enter-active {
  transition: all 0.6s ease;
}
.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(30px);
}
.fade-slide-enter-to {
  opacity: 1;
  transform: translateY(0);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.96);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

/* ✅ Success Animation */
.success-container {
  text-align: center;
  color: #333;
}
.checkmark {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: inline-block;
  stroke-width: 2;
  stroke: #4caf50;
  stroke-miterlimit: 10;
  box-shadow: inset 0px 0px 0px #4caf50;
  animation: fill 0.4s ease-in-out 0.4s forwards, scale 0.3s ease-in-out 0.9s both;
  position: relative;
}
.checkmark__circle {
  stroke-dasharray: 166;
  stroke-dashoffset: 166;
  stroke-width: 2;
  stroke-miterlimit: 10;
  stroke: #4caf50;
  fill: none;
  animation: stroke 0.6s cubic-bezier(0.65, 0, 0.45, 1) forwards;
}
.checkmark__check {
  transform-origin: 50% 50%;
  stroke-dasharray: 48;
  stroke-dashoffset: 48;
  animation: stroke 0.3s cubic-bezier(0.65, 0, 0.45, 1) 0.6s forwards;
}

@keyframes stroke {
  100% {
    stroke-dashoffset: 0;
  }
}
@keyframes scale {
  0%, 100% {
    transform: none;
  }
  50% {
    transform: scale3d(1.1, 1.1, 1);
  }
}
@keyframes fill {
  100% {
    box-shadow: inset 0px 0px 0px 40px #4caf50;
  }
}
</style>
