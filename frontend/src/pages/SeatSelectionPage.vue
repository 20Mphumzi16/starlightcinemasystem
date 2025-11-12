<template>
  <div class="seat-selection-page">
    <!-- Seat Selection Page Root + Blur toggle -->
    <div :class="['seat-selection-page-root', { 'modal-open': showModal }]">
      <NavBar
        @sign-in="showModal = true; activeModal = 'signin'"
        @sign-up="showModal = true; activeModal = 'signup'"
        @open-search="showSearch = true"
      />

      <!-- Grid Layout -->
      <div class="page-grid">
        <!-- 🎟 Left Column: Seat layout -->
        <div class="left-col">
          <SeatSelection
            :seats="seats"
            :show="show"
            :loading="loading"
            @toggle="toggleSeat"
          />
          <QuickBook />
        </div>

        <!-- 🔍 Search Overlay -->
        <SearchOverlay v-if="showSearch" @close="showSearch = false" />

        <!-- 💳 Right Column: Summary card -->
        <div class="right-col">
          <SeatSummary
            :selected-seats="selectedSeats"
            :show="show"
            :total="total"
          />
        </div>
      </div>
    </div>

    <!-- Overlay Modal Wrapper -->
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
import { ref, computed, onMounted, defineProps } from "vue";
import axios from "axios";

import NavBar from "@/components/NavBar.vue";
import QuickBook from "@/components/QuickBook.vue";
import SignInComponent from "@/components/SignInComponent.vue";
import SignUpComponent from "@/components/SignUpComponent.vue";
import SeatSelection from "@/components/SeatSelection.vue";
import SeatSummary from "@/components/SeatSummary.vue";
import SearchOverlay from "@/components/SearchOverlay.vue";

const props = defineProps({
  showId: {
    type: String,
    required: true,
  },
});

const showModal = ref(false);
const activeModal = ref("signin");
const showSearch = ref(false);

const show = ref(null);
const seats = ref([]);
const loading = ref(true);

// 🧾 Fetch show + seats
async function fetchShowAndSeats() {
  loading.value = true;
  try {
    const [showRes, seatsRes] = await Promise.all([
      axios.get(`http://localhost:8080/starlight/show/read/${props.showId}`),
      axios.get(`http://localhost:8080/starlight/seat/seats/${props.showId}`),
    ]);

    show.value = showRes.data;
    seats.value = seatsRes.data.map((seat) => ({
      ...seat,
      selected: false,
    }));
  } catch (err) {
    console.error("Error fetching show or seats:", err);
  } finally {
    loading.value = false;
  }
}

// 🟦 Computed selected seats + total
const selectedSeats = computed(() =>
  seats.value.filter((s) => s.selected)
);

const total = computed(() =>
  show.value ? selectedSeats.value.length * show.value.price : 0
);

// ✅ Toggle seat selection (emitted from SeatSelection)
function toggleSeat(seatId) {
  const seat = seats.value.find((s) => s.id === seatId);
  if (seat && !seat.occupied) {
    seat.selected = !seat.selected;
  }
}

onMounted(fetchShowAndSeats);
</script>

<style scoped>
.page-grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 24px;
  padding: 20px;
}

.left-col {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.right-col {
  position: sticky;
  top: 20px;
  align-self: start;
}

.seat-selection-page {
  min-height: 100vh;
  background: var(--bg);
  position: relative;
}

.seat-selection-page-root {
  transition: filter 0.3s ease, opacity 0.3s ease;
}

.seat-selection-page-root.modal-open {
  filter: blur(5px) brightness(0.6);
  pointer-events: none;
}

.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.fade-scale-enter-active,
.fade-scale-leave-active {
  transition: all 0.3s ease;
}

.fade-scale-enter-from {
  opacity: 0;
  transform: scale(0.9) translateY(20px);
}

.fade-scale-leave-to {
  opacity: 0;
  transform: scale(0.9) translateY(-20px);
}
</style>
