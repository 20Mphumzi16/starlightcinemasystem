<template>
  <div class="wishlist-container">
    <h2>My Wishlist</h2>

    <table class="wishlist-table">
      <thead>
        <tr>
          <th></th>
          <th>Movie</th>
          <th>Genre</th>
          <th>Rating</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="movie in wishlistMovies" :key="movie.id">
          <td class="remove-cell" @click="$emit('remove', movie.id)">×</td>
          <td class="movie-cell">
            <img :src="movie.photo" alt="" class="movie-img" />
            <span>{{ movie.title }}</span>
          </td>
          <td>{{ movie.genres.map(genre => genre.name).join(", ") }}</td>
          <td>{{ movie.rating.rating }} - {{ movie.rating.description }}</td>
          <td></td>
        </tr>
      </tbody>
    </table>

    <p v-if="wishlistMovies.length === 0" class="empty-text">
      Your wishlist is empty.
    </p>
  </div>
</template>

<script setup>
import {defineProps,defineEmits} from 'vue'
defineProps({
  wishlistMovies: Array,
})
defineEmits(["remove"])
</script>

<style scoped>
.wishlist-container {
  background: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.1);
}

.wishlist-table {
  width: 100%;
  border-collapse: collapse;
}

.wishlist-table th,
.wishlist-table td {
  padding: 12px;
  border-bottom: 1px solid #eee;
  text-align: left;
}

.movie-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.movie-img {
  width: 60px;
  height: 80px;
  object-fit: cover;
  border-radius: 6px;
}

.remove-cell {
  color: #d33;
  cursor: pointer;
  font-size: 18px;
  width: 20px;
  text-align: center;
}

.remove-cell:hover {
  color: #a00;
}

.empty-text {
  text-align: center;
  color: #777;
  margin-top: 20px;
}
</style>
