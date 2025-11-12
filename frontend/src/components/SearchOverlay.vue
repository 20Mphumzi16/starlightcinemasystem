<template>
  <div class="search-overlay">
    <button class="close" @click="$emit('close')">✖</button>
    
    <!-- 🔍 Search Input -->
    <div class="search-box">
      <input 
        type="text" 
        v-model="query" 
        placeholder="Search movies..."
      />
    </div>

    <!-- 🎬 Results -->
    <div class="results">
      <div 
        v-for="movie in filteredMovies" 
        :key="movie.id" 
        class="movie-card"
      >
     <img 
          :src="`data:image/jpeg;base64,${movie.photo}`" 
          :alt="movie.title" 
        />
        <h3>{{ movie.title }}</h3>
     <span class="badge">{{ movie.rating?.rating }}</span>

      </div>

      <!-- Loading and Empty States -->
      <p v-if="loading" class="loading">Loading movies...</p>
      <p v-else-if="!filteredMovies.length" class="no-results">
        No movies found.
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue"
import axios from "axios"

const movies = ref([])
const query = ref("")
const loading = ref(true)

// 🎥 Fetch movies from backend
const fetchMovies = async () => {
  try {
    const response = await axios.get("http://localhost:8080/starlight/movie/get-all")
    movies.value = response.data
    console.log("✅ Movies fetched:", movies.value)
  } catch (error) {
    console.error("❌ Error fetching movies:", error)
  } finally {
    loading.value = false
  }
}

// Auto-load when component mounts
onMounted(() => {
  fetchMovies()
})

// 🔍 Computed search filter
const filteredMovies = computed(() => {
  if (!query.value) return movies.value
  return movies.value.filter(movie =>
    movie.title.toLowerCase().includes(query.value.toLowerCase())
  )
})
</script>

<style scoped>
.search-overlay {
  position: fixed;
  inset: 0;
  background: black;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 80px 20px;
  z-index: 3000;
  overflow-y: auto;
}

.close {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 24px;
  background: none;
  border: none;
  color: white;
  cursor: pointer;
}

.search-box {
  width: 100%;
  max-width: 600px;
  margin-bottom: 30px;
}

.search-box input {
  width: 100%;
  padding: 12px 16px;
  font-size: 18px;
  border-radius: 6px;
  border: none;
}

.results {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 20px;
  width: 100%;
  max-width: 1200px;
}

.movie-card {
  background: #111;
  border-radius: 8px;
  padding: 10px;
  text-align: center;
}

.movie-card img {
  width: 100%;
  border-radius: 6px;
}

.badge {
  display: inline-block;
  margin-top: 6px;
  padding: 4px 8px;
  background: var(--primary-blue, #007bff);
  border-radius: 4px;
  font-size: 12px;
}

.loading,
.no-results {
  margin-top: 20px;
  font-size: 18px;
  color: #ccc;
}
</style>
