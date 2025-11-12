<template>
  <div class="admin-section">
        <SpinnerComponent :visible="loading" />
    
    <div class="header-bar">
      <h2>🎬 Manage Movies</h2>
      <button class="add-btn" @click="showForm = true">
        + Add Movie
      </button>
    </div>

    <!-- 🔍 Filter + Search Bar -->
    <div class="filter-bar">
      <div class="filter-group search">
        <i class="fa fa-search"></i>
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search movies..."
          class="search-input"
        />
      </div>

      <!-- 🎭 Genre Filter -->
      <div class="filter-group genres">
        <label class="filter-label">Genres:</label>
        <div class="genre-tags">
          <span
            v-for="genre in allGenres"
            :key="genre.id"
            :class="['tag', { active: selectedGenres.includes(genre.name) }]"
            @click="toggleGenre(genre.name)"
          >
            {{ genre.name }}
          </span>
        </div>
      </div>

      <!-- 📅 Release Date Filter -->
      <div class="filter-group">
        <label class="filter-label">Release Date:</label>
        <input
          type="date"
          v-model="releaseDate"
          class="date-picker"
        />
      </div>

      <button class="clear-btn" @click="clearFilters">Clear Filters</button>
    </div>

    <!-- 🎬 Table -->
    <div class="table-wrapper">
      <AdminTable
        :columns="['Title', 'Genres', 'Rating', 'Duration']"
        :data="formattedMovies"
        @edit="editMovie"
        @delete="deleteMovie"
      />
    </div>

    <!-- 🧾 Form Modal -->
    <MovieForm v-if="showForm" @close="showForm = false" @save="saveMovie" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import AdminTable from '@/components/AdminTable.vue'
import MovieForm from '@/components/MovieForm.vue'
import SpinnerComponent from './SpinnerComponent.vue'

const showForm = ref(false)
const loading = ref(false);
const searchQuery = ref('')
const selectedGenres = ref([])
const releaseDate = ref('')

const movies = ref([])
const allGenres = ref([])

// ✅ Fetch movies from backend
async function fetchMovies() {
  try {
    loading.value = true;
    const response = await axios.get('http://localhost:8080/starlight/movie/get-all')
    console.log('✅ Movies fetched:', response.data)
    movies.value = response.data
  } catch (error) {
    console.error('❌ Failed to fetch movies:', error)
  }
  finally {
    loading.value = false;
  }
}

// ✅ Fetch genres from backend
async function fetchGenres() {
  try {
    const response = await axios.get('http://localhost:8080/starlight/genres/get-all')
    console.log('✅ Genres fetched:', response.data)
    allGenres.value = response.data
  } catch (error) {
    console.error('❌ Failed to fetch genres:', error)
  }
}

onMounted(() => {
  fetchMovies()
  fetchGenres()
})

// 🔍 Computed filtering
const filteredMovies = computed(() => {
  return movies.value.filter(movie => {
    const matchesSearch = movie.title?.toLowerCase().includes(searchQuery.value.toLowerCase())
    const movieGenreNames = movie.genres?.map(g => g.name) || []
    const matchesGenre =
      selectedGenres.value.length === 0 ||
      selectedGenres.value.some(g => movieGenreNames.includes(g))
    const matchesDate = !releaseDate.value || movie.releaseDate === releaseDate.value

    return matchesSearch && matchesGenre && matchesDate
  })
})

// 🧩 Format for table
const formattedMovies = computed(() => {
  return filteredMovies.value.map(movie => ({
    id: movie.id,
    title: movie.title,
    genres: movie.genres?.map(g => g.name).join(', ') || '—',
    rating: movie.rating?.rating || '—',
    duration: movie.durationMinutes ? `${movie.durationMinutes} min` : '—',
  }))
})

// 🎭 Genre toggling
function toggleGenre(genre) {
  if (selectedGenres.value.includes(genre)) {
    selectedGenres.value = selectedGenres.value.filter(g => g !== genre)
  } else {
    selectedGenres.value.push(genre)
  }
}

// 🧹 Clear filters
function clearFilters() {
  searchQuery.value = ''
  selectedGenres.value = []
  releaseDate.value = ''
}

// ✏️ Edit movie placeholder
function editMovie(movie) {
  alert(`Editing movie: ${movie.title}`)
}

// 🗑️ Delete movie
async function deleteMovie(movie) {
  const confirmed = confirm(`Are you sure you want to delete "${movie.title}"?`)
  if (!confirmed) return

  try {
    loading.value = true;
    await axios.delete(`http://localhost:8080/starlight/movie/delete/${movie.id}`)
    console.log(`✅ Movie "${movie.title}" deleted.`)

    // Refresh movies from backend
    await fetchMovies()
  } catch (error) {
    console.error('❌ Failed to delete movie:', error)
    alert('Failed to delete movie. Please try again.')
  } finally {
    loading.value = false;
  }
}

// 💾 Save new movie (mock)
async function saveMovie(newMovie) {
 try {
    loading.value = true;
    await axios.post('http://localhost:8080/starlight/movie/create', newMovie)
    console.log(`✅ Movie "${newMovie.title}" created.`)

    // Refresh movies from backend
    await fetchMovies()
  } catch (error) {
    console.error('❌ Failed to create movie:', error)
    alert('Failed to create movie. Please try again.')
  } finally {
    loading.value = false;
  }
  showForm.value = false
}
</script>

<style scoped>
/* 🌟 Layout */
.admin-section {
  padding: 2rem;
  background: #f8fafc;
  min-height: 100vh;
}

.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.2rem;
}

.header-bar h2 {
  font-size: 1.8rem;
  font-weight: 600;
  color: #1e293b;
}

/* 🎚 Filter Bar */
.filter-bar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 1rem;
  background: #ffffff;
  padding: 1rem;
  border-radius: 12px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.05);
  margin-bottom: 1.5rem;
}

.filter-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.filter-group.search {
  flex: 1;
}

.search-input {
  flex: 1;
  padding: 0.6rem 0.8rem;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  outline: none;
}

.search-input:focus {
  border-color: #2563eb;
  box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
}

.filter-label {
  font-weight: 500;
  color: #334155;
}

/* 🎭 Genre tags */
.genre-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
}

.tag {
  padding: 6px 10px;
  border: 1px solid #cbd5e1;
  border-radius: 20px;
  cursor: pointer;
  background: #f1f5f9;
  transition: 0.2s ease;
  font-size: 14px;
  user-select: none;
}

.tag.active {
  background: #2563eb;
  color: white;
  border-color: #2563eb;
}

/* 📅 Date picker */
.date-picker {
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  padding: 0.4rem 0.6rem;
  background: #f9fafb;
}

/* 🧹 Clear Button */
.clear-btn {
  background: #64748b;
  color: white;
  border: none;
  padding: 0.5rem 0.9rem;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.clear-btn:hover {
  background: #475569;
}

/* ➕ Add Movie */
.add-btn {
  background: #2563eb;
  color: white;
  padding: 0.7rem 1.2rem;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(37, 99, 235, 0.25);
  transition: 0.25s ease;
}

.add-btn:hover {
  background: #1d4ed8;
  transform: translateY(-1px);
}

/* 📊 Table Wrapper */
.table-wrapper {
  background: #fff;
  padding: 1rem;
  border-radius: 12px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.06);
}
</style>
