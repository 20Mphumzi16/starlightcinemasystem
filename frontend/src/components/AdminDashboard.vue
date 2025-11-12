<template>
  <div class="dashboard">
    <SpinnerComponent :visible="loading" />
    <h1>Admin Dashboard</h1>

    <div class="cards">
      <div class="card">
        <h3>Total Movies</h3>
        <p>{{ stats.movies }}</p>
      </div>
      <div class="card">
        <h3>Total Shows</h3>
        <p>{{ stats.shows }}</p>
      </div>
      <div class="card">
        <h3>Total Genres</h3>
        <p>{{ stats.genres }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';   
import { onMounted, reactive, ref } from 'vue'; 
import SpinnerComponent from './SpinnerComponent.vue';

const loading = ref(false);
const API_URL = 'http://localhost:8080/starlight';

// Make stats reactive
const stats = reactive({
  movies: 0,
  shows: 0,
  genres: 0
})

onMounted(async () => {

  try {
      loading.value = true;
    const [moviesRes, genresRes, showRes] = await Promise.all([
      axios.get(`${API_URL}/movie/total-movies`),
      axios.get(`${API_URL}/show/get-show-count`),
      axios.get(`${API_URL}/genres/total-genres`)
    ]);
    stats.movies = moviesRes.data;
    stats.shows = showRes.data;
    stats.genres = genresRes.data;
  } catch (error) {
    console.error('Error fetching stats:', error);
  } finally {
    loading.value = false;
  }
});
</script>


<style scoped>
.dashboard {
  padding: 20px;
}

.cards {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.card {
  background: white;
  flex: 1;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.card h3 {
  color: #666;
  font-weight: 600;
}
</style>
