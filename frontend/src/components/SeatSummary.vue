<template>
  <div class="summary-card">
    <h2 class="title">Your Tickets</h2>

    <!-- 🎬 Movie / Show Details -->
    <div class="show-details">
      <h3 class="movie-title">{{ show?.movie?.title || "Movie Title" }}</h3>
      <p class="show-info">
        {{ show?.hall.branch.name || "Cinema Name" }} •
        {{ show?.screenType || "Standard" }}
      </p>
      <p class="show-time">
        {{ formatDate(show?.startTime) }} to {{ formatDate(show?.endTime) }}
      </p>
    </div>

    <hr class="divider" />

    <!-- 🪑 Selected Seats -->
    <div v-for="seat in selectedSeats" :key="seat.id" class="ticket-box">
      <p class="ticket-type">
        Seat {{ seat.seatNumber }}
      </p>
      <p class="ticket-price">R {{ show?.price?.toFixed(2) }}</p>
    </div>

    <!-- 💰 Total -->
    <div class="total-box">
      <span>Total</span>
      <span class="total-price">R {{ total.toFixed(2) }}</span>
    </div>


    <!-- existing markup -->
    <button
      v-if="selectedSeats.length > 0"
      class="checkout-btn"
      @click="handleCheckout"
    >
      Check Out
    </button>

<!-- Disabled state when no seats selected -->
<button 
  v-else 
  class="checkout-btn disabled" 
  disabled
>
  Select a Seat to Continue
</button>
  </div>
</template>

<script setup>
/* eslint-disable */
import { defineProps } from "vue"
import { useRouter } from "vue-router"
import { useBookingStore } from "@/stores/bookingStore.js"

const router = useRouter()
const bookingStore = useBookingStore()

const { selectedSeats, show, total } = defineProps({
  selectedSeats: Array,
  show: Object,
  total: Number,
})

function handleCheckout() {
  bookingStore.setBookingData(show, selectedSeats, total)
  router.push({ name: "Payment" })
}
function formatDate(dateStr) {
  if (!dateStr) return "Unknown Date";
  const date = new Date(dateStr);
  return date.toLocaleDateString("en-ZA", {
    weekday: "short",
    year: "numeric",
    month: "short",
    day: "numeric",
  });
}
</script>

<style scoped>
.summary-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 16px;
}

/* 🎬 Movie / Show Details */
.show-details {
  margin-bottom: 16px;
}

.movie-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 4px;
}
.checkout-btn.disabled {
  background: #ccc;
  cursor: not-allowed;
}

.show-info,
.show-time {
  color: #666;
  font-size: 0.9rem;
}

.divider {
  border: none;
  border-top: 1px solid #ddd;
  margin: 12px 0;
}

.ticket-box {
  display: flex;
  justify-content: space-between;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin-bottom: 12px;
}

.ticket-type {
  font-weight: 500;
}

.ticket-price {
  font-weight: 600;
}

.total-box {
  display: flex;
  justify-content: space-between;
  font-size: 1.1rem;
  font-weight: 600;
  margin-top: 16px;
  margin-bottom: 16px;
}

.checkout-btn {
  width: 100%;
  padding: 12px;
  background: #f58220;
  color: #fff;
  font-weight: 600;
  font-size: 1rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.checkout-btn:hover {
  background: #e57210;
}
</style>
