<template>
  <div class="booking-table">
    <h2 class="title">🎟 Your Bookings</h2>

    <div v-if="bookings && bookings.length" class="table-container">
      <table>
        <thead>
          <tr>
            <th>Movie</th>
            <th>Cinema / Hall</th>
            <th>Date & Time</th>
            <th>Seats</th>
            <th>Total</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(b, index) in bookings" :key="index">
            <td class="movie-cell">
                <img
                :src="getMovieImage(b.show.movie?.photo)"
                alt="Movie Poster"
              />
              <div>
                <p class="movie-title">{{ b.show.movie?.title }}</p>
              </div>
            </td>

            <td>
              {{ b.show.hall?.branch?.name || 'Unknown Cinema' }} <br />
              <small>{{ b.show.hall?.name || 'Hall 1' }}</small>
            </td>

            <td>{{ formatDate(b.show.startTime) }}</td>

            <td>
              <span
                v-for="seat in b.seats"
                :key="seat.id"
                class="seat-tag"
              >
                {{ seat.seatNumber }}
              </span>
            </td>

            <td>R {{ b.totalPrice.toFixed(2) }}</td>

            <td>
              <span :class="['status', b.status.toLowerCase()]">{{ b.status }}</span>
            </td>

            <td>
              <button class="view-btn" @click="$emit('view', b)">
                View
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-else class="no-bookings">
      <p>No bookings found.</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'

defineProps({
  bookings: {
    type: Array,
    default: () => [],
  },
})
function getMovieImage(photoData) {
  if (!photoData) {
    return require('@/assets/default_avatar.png')
  }
  
  // Check if the photo is already a base64 string
  if (photoData.startsWith('data:image')) {
    return photoData
  }
  
  // If it's base64 data without the prefix, add it
  if (photoData.match(/^[A-Za-z0-9+/=]+$/)) {
    return `data:image/jpeg;base64,${photoData}`
  }
  
  // If it's a URL or other format, return default image
  return require('@/assets/default_avatar.png')
}

function formatDate(dateStr) {
  if (!dateStr) return 'N/A'
  const date = new Date(dateStr)
  return date.toLocaleString('en-ZA', {
    weekday: 'short',
    year: 'numeric',
    month: 'short',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  })
}
</script>

<style scoped>
.booking-table {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 1.4rem;
  font-weight: 600;
  margin-bottom: 16px;
}

.table-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  font-size: 0.95rem;
}

thead {
  background: #f6f6f6;
}

th,
td {
  padding: 12px 14px;
  border-bottom: 1px solid #eee;
}

.movie-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}

.movie-cell img {
  width: 45px;
  height: 65px;
  border-radius: 4px;
  object-fit: cover;
}

.movie-title {
  font-weight: 600;
}

.seat-tag {
  background: #222;
  color: #fff;
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 0.8rem;
  margin: 2px;
  display: inline-block;
}

.status {
  font-weight: 600;
  padding: 5px 10px;
  border-radius: 6px;
  text-transform: capitalize;
  font-size: 0.85rem;
}

.status.confirmed {
  background: #eafbea;
  color: #2b7a2b;
}

.status.cancelled {
  background: #fdeaea;
  color: #c62828;
}

.status.expired {
  background: #fff4e5;
  color: #e65100;
}

.view-btn {
  background: #f58220;
  color: #fff;
  border: none;
  padding: 8px 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: 0.2s ease;
  font-weight: 600;
}

.view-btn:hover {
  background: #e57210;
}

.no-bookings {
  text-align: center;
  padding: 40px 0;
  color: #777;
}
</style>
