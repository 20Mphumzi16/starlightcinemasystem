<template>
  <div class="search-overlay">
    <button class="close" @click="$emit('close')">✖</button>
    

    <div class="search-box">
      <input 
        type="text" 
        v-model="query" 
        placeholder="Search movies..."
      />  
    </div>

    <div class="results">
      <div 
        v-for="movie in moviesToShow" 
        :key="movie.id" 
        class="movie-card"
        @click="goToShowtimes(movie.id)"
      >
        <img 
          :src="`data:image/jpeg;base64,${movie.photo}`" 
          :alt="movie.title" 
        />
        <h3>{{ movie.title }}</h3>
        <span class="badge">{{ movie.rating?.rating }}</span>
      </div>

      <p v-if="loading" class="loading">Loading...</p>
      <p v-else-if="!moviesToShow.length" class="no-results">
        No movies found.
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from "vue"
import axios from "axios"
import { useRouter } from "vue-router"

const router = useRouter()

const defaultMovies = ref([])

const searchedMovies = ref([])

const query = ref("")

const loading = ref(false)

let debounceTimer = null

onMounted(async () => {
  try {
    const res = await axios.get("http://localhost:8080/starlight/movie/get-all")
    defaultMovies.value = res.data
  } catch (err) {
    console.error("❌ Error loading default movies:", err)
  }
})

const searchMovies = async () => {
  if (!query.value) {
    searchedMovies.value = []
    return
  }

  loading.value = true
  try {
    const res = await axios.get(
      `http://localhost:8080/starlight/movie/search/${encodeURIComponent(query.value)}`
    )
    searchedMovies.value = res.data 
  } catch (err) {
    console.error("Search error:", err)
    searchedMovies.value = []
  } finally {
    loading.value = false
  }
}
// Debounce the search to avoid too many requests
watch(query, () => {
  clearTimeout(debounceTimer)
  debounceTimer = setTimeout(() => {
    searchMovies()
  }, 300)
})


const moviesToShow = computed(() => {
  return query.value ? searchedMovies.value : defaultMovies.value
})

const goToShowtimes = (id) => {
  router.push(`/showtimes/${id}`)
}
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
  cursor: pointer;
  transition: transform 0.1s ease;
}

.movie-card:hover {
  transform: scale(1.03);
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
