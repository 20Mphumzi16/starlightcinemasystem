<template>
  <div class="homepage">
    <SpinnerComponent :visible="loading" />
    <!-- Homepage Root + Blur toggle -->
    <div :class="['homepage-root', { 'modal-open': showModal }]">
      <NavBar
        ref="navBar"
        @sign-in="showModal = true; activeModal = 'signin'"
        @sign-up="showModal = true; activeModal = 'signup'"
        @open-search="showSearch = true"
      />

      <HeroCarousel />

      <!-- 🔽 Filters (with tab + genre filter) -->
      <FiltersBar
        v-model:tab="selectedTab"
        @filter-change="applyFilters"
      />

      <!-- 🎬 Movie Grid -->
      <MovieGrid
        :movies="filteredMovies"
        :wishlist="wishlist"
        @toggle-wishlist="toggleWishlist"
      />

      <QuickBook />
    </div>

    <!-- 🔍 Search Overlay -->
    <SearchOverlay 
      v-if="showSearch" 
      @close="showSearch = false" 
    />

    <!-- 🔐 Sign-in / Sign-up Modal -->
    <div v-if="showModal" class="overlay">
      <transition name="fade-scale" mode="out-in">
        <component
          :is="activeModal === 'signin' ? SignInComponent : SignUpComponent"
          :key="activeModal"
          @close="showModal = false"
          @switch-to-signup="activeModal = 'signup'"
          @switch-to-signin="activeModal = 'signin'"
          @signed-in="handleSignedIn"  
        />
      </transition>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue"
import axios from "axios"

import NavBar from "@/components/NavBar.vue"
import HeroCarousel from "@/components/HeroCarousel.vue"
import FiltersBar from "@/components/FiltersBar.vue"
import MovieGrid from "@/components/MovieGrid.vue"
import QuickBook from "@/components/QuickBook.vue"
import SignInComponent from "@/components/SignInComponent.vue"
import SignUpComponent from "@/components/SignUpComponent.vue"
import SearchOverlay from "@/components/SearchOverlay.vue"
import SpinnerComponent from "@/components/SpinnerComponent.vue"

// ✅ Reactive state
const movies = ref([])
const filteredMovies = ref([])
const showModal = ref(false)
const activeModal = ref("signin")
const showSearch = ref(false)
const selectedTab = ref("now")
const wishlist = ref([])
const wishlistId = ref([]) // For quick lookup
const navBar = ref(null)
const loading = ref(false)
// 🎞 Filter criteria
const filters = ref({
  sortBy: "popular",
  genre: "",
  // branch: "" // 👈 Commented out for future use
})

// 🎥 Fetch movies from backend
const fetchMovies = async () => {
  try {
    const response = await axios.get("http://localhost:8080/starlight/movie/get-all")

    movies.value = response.data.map(movie => ({
      ...movie,
      photo: movie.photo ? `data:image/jpeg;base64,${movie.photo}` : "",
      rating: movie.rating?.rating || "N/A"
    }))

    filteredMovies.value = [...movies.value]
    console.log("✅ Movies fetched:", movies.value)
  } catch (error) {
    console.error("❌ Error fetching movies:", error)
  }
}

// 🎛 Apply filters locally
function applyFilters(newFilters) {
  filters.value = { ...filters.value, ...newFilters }

  let temp = [...movies.value]

  // 🔹 Filter by Genre (handles multi-genre arrays)
  if (filters.value.genre) {
    const genreName =
      typeof filters.value.genre === "string"
        ? filters.value.genre
        : filters.value.genre.name

    if (genreName) {
      temp = temp.filter(movie => {
        const movieGenres = movie.genres?.map(g => g.name.toLowerCase()) || []
        return movieGenres.includes(genreName.toLowerCase())
      })
    }
  }

  // 🔹 (Future) Filter by Branch
  // if (filters.value.branch) {
  //   temp = temp.filter(m =>
  //     m.branch?.toLowerCase().includes(filters.value.branch.toLowerCase())
  //   )
  // }

  // 🔹 Sort Movies
  if (filters.value.sortBy === "latest") {
    temp.sort((a, b) => new Date(b.releaseDate) - new Date(a.releaseDate))
  } else if (filters.value.sortBy === "popular") {
    temp.sort((a, b) => (b.rating || 0) - (a.rating || 0))
  }

  filteredMovies.value = temp
}

// 🧠 Lifecycle: load movies on mount
onMounted(() => {
  fetchMovies()
  fetchWishlist()
})

// 💖 Wishlist toggle
async function toggleWishlist(id) {
  loading.value = true
  console.log("Toggling wishlist for movie ID:", id)
  
  if (wishlist.value.includes(id)) {
    wishlist.value = wishlist.value.filter(m => m !== id)
  } else {
    try {
      const response = await axios.post(`http://localhost:8080/starlight/wishlist/add-to-wishlist/${wishlistId.value}/${id}`)
      if (response.status === 200) {
        alert("✅ Added to wishlist:")
      }

    } catch (error) {
      console.error("❌ Error adding to wishlist:", error)
      alert("❌ Failed to add to wishlist. Please try again.")
  }
  finally   {
    loading.value = false
    fetchWishlist()
  }
}
}

async function fetchWishlist() {
  // Placeholder: Fetch wishlist from backend or localStorage
const customerId = localStorage.getItem("customerId")
  if (!customerId) {
    console.warn("⚠️ No customer ID found in localStorage.")
    
    // Set a default for testing
  // Redirect to sign-in page
    return
  }

  try {
    const response = await axios.get(`http://localhost:8080/starlight/wishlist/user-wishlist/${customerId}`)
        wishlist.value = response.data
        wishlistId.value = response.data.id// Extract IDs for quick lookup
        console.log("✅ Wishlist fetched:", wishlist.value)
  } catch (error) {
        console.error("❌ Error fetching wishlist:", error)
  }


  // Example: empty list initially
  wishlist.value = []
}


// 🔑 When user successfully signs in
function handleSignedIn() {
  showModal.value = false
  navBar.value?.loadUser()
}
</script>

<style scoped>
.homepage {
  min-height: 100vh;
  background: var(--bg);
  position: relative;
}

/* Blur & dim background when modal is open */
.homepage-root {
  transition: filter 0.3s ease, opacity 0.3s ease;
}

.homepage-root.modal-open {
  filter: blur(5px) brightness(0.6);
  pointer-events: none;
}

/* Overlay wrapper */
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

/* Transition Animations */
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

