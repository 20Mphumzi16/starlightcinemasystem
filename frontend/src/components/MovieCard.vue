<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  movie: { type: Object, required: true },
  inWishlist: { type: Boolean, default: false }
})

const emit = defineEmits(['toggle-wishlist'])
</script>

<template>
  <div class="card">

<router-link
  :to="{
    name: 'Showtimes',
    params: { id: movie.id },

  }"
>
  <img :src="movie.photo" :alt="movie.title" />
</router-link>
    <div class="badge">{{ props.movie.rating }}</div>

    <h3>{{ props.movie.title }}</h3>
    <div class="actions">
      <button class="blue">Buy Tickets</button>
<button class="heart" @click.stop="emit('toggle-wishlist', props.movie.id)">
  <i
    :class="[
      'fa-heart',
      props.inWishlist ? 'fa-solid text-red-600' : 'fa-regular text-gray-400'
    ]"
  ></i>
</button>
    </div>
  </div>
</template>

<style scoped>
.card {
  background: #fff;
  border: 1px solid #eee;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
  display: flex;
  flex-direction: column;
  transition: transform 0.2s ease;
}

.card:hover {
  transform: translateY(-4px);
}

.card img {
  width: 100%;
  height: 320px;
  object-fit: cover;
}

.badge {
  background: crimson;
  color: #fff;
  font-size: 12px;
  padding: 4px 8px;
  position: absolute;
  margin: 8px;
  border-radius: 4px;
}

h3 {
  margin: 12px;
  font-size: 16px;
  font-weight: 600;
  color: #222;
}

.actions {
  margin: auto 12px 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

button.blue {
  background: var(--primary-blue, #007bff);
  border: none;
  color: white;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

button.blue:hover {
  background: #0056b3;
}

button.heart {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 18px;
  color: crimson;
}
</style>

