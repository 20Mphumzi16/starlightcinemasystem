<script setup>
import { defineProps, defineEmits } from 'vue'
import MovieCard from './MovieCard.vue'

const props = defineProps({
  movies: { type: Array, required: true },
  wishlist: { type: Array, required: true }
})

const emit = defineEmits(['toggle-wishlist'])

function toggleWishlist(id) {
  emit('toggle-wishlist', id)
}
</script>

<template>
  <div class="grid">
    <MovieCard
        v-for="movie in movies"
        :key="movie.id"
        :movie="movie"
        :inWishlist="props.wishlist.includes(movie.id)"
        @toggle-wishlist="toggleWishlist"
    />
  </div>
</template>
<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 16px;
}
</style>
