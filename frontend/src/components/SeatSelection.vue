<template>
  <div class="movie-container">
    <h1>Select Your Seats</h1>

    <!-- Show loading state -->
    <div v-if="loading" class="loading">Loading seats...</div>

    <!-- Cinema layout -->
    <div v-else class="container">
      <div class="screen"></div>

      <!-- Render rows dynamically -->
      <div
        class="row"
        v-for="(row, rowIndex) in groupedSeats"
        :key="rowIndex"
      >
        <div
          v-for="seat in row"
          :key="seat.id"
          class="seat"
          :class="{ selected: seat.selected, occupied: seat.occupied }"
          @click="handleToggle(seat)"
        >
          <small>{{ seat.seatNumber }}</small>
        </div>
      </div>

      <!-- Showcase legend -->
      <ul class="showcase">
        <li>
          <div class="seat"></div>
          <small>N/A</small>
        </li>
        <li>
          <div class="seat selected"></div>
          <small>Selected</small>
        </li>
        <li>
          <div class="seat occupied"></div>
          <small>Occupied</small>
        </li>
      </ul>

      <!-- Summary -->
      <p class="text">
        You have selected
        <span>{{ selectedCount }}</span>
        seats for the total price of Rs.
        <span>{{ total }}</span>
      </p>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, computed } from "vue";

const props = defineProps({
  seats: {
    type: Array,
    required: true,
  },
  show: {
    type: Object,
    required: false,
  },
  loading: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["toggle"]);

// Group seats into rows (e.g., A1–A8)
const groupedSeats = computed(() => {
  const grouped = {};
  for (const seat of props.seats) {
    const rowLetter = seat.seatNumber.charAt(0);
    if (!grouped[rowLetter]) grouped[rowLetter] = [];
    grouped[rowLetter].push(seat);
  }

  // Sort within each row (A1, A2, etc.)
  Object.keys(grouped).forEach((key) => {
    grouped[key].sort((a, b) =>
      a.seatNumber.localeCompare(b.seatNumber, undefined, { numeric: true })
    );
  });

  return Object.values(grouped);
});

const selectedCount = computed(() =>
  props.seats.filter((s) => s.selected).length
);

const total = computed(() =>
  props.show ? selectedCount.value * props.show.price : 0
);

function handleToggle(seat) {
  if (!seat.occupied) {
    emit("toggle", seat.id);
  }
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Montserrat&display=swap");

.movie-container {
  font-family: "Montserrat", sans-serif;
  color: #fff;
  background-color: #242333;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px;
  border-radius: 8px;
}

.container {
  perspective: 1000px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.screen {
  background: #fff;
  height: 70px;
  width: 70%;
  margin: 15px 0;
  transform: rotateX(-45deg);
  box-shadow: 0 3px 10px rgba(255, 255, 255, 0.7);
}

.row {
  display: flex;
  justify-content: center;
}

.seat {
  background-color: #444451;
  height: 30px;
  width: 30px;
  margin: 3px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  transition: transform 0.2s;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 10px;
}

.seat.selected {
  background-color: #0081cb;
}

.seat.occupied {
  background-color: #fff;
}

.seat:not(.occupied):hover {
  cursor: pointer;
  transform: scale(1.2);
}

.showcase {
  display: flex;
  justify-content: space-between;
  list-style-type: none;
  background: rgba(0, 0, 0, 0.1);
  padding: 5px 10px;
  border-radius: 5px;
  margin-top: 30px;
  color: #777;
}

.showcase li {
  display: flex;
  align-items: center;
  margin: 0 10px;
}

.showcase .seat {
  margin-right: 5px;
}

.text {
  margin: 30px 0;
  font-size: 16px;
}

.text span {
  color: #0081cb;
  font-weight: 600;
}

.loading {
  font-size: 1.2rem;
  color: #ccc;
  margin-top: 40px;
}
</style>
