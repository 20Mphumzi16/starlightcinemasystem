<template>
  <div class="showtimes-page">
    <!-- Shared NavBar -->
    <SpinnerComponent v-if="loading" />
    <SearchOverlay 
  v-if="showSearch" 
  @close="showSearch = false" 
/>

        <!-- Overlay Modal Wrapper -->
    <div v-if="showModal" class="overlay">
      <transition name="fade-scale" mode="out-in">
        <component
          :is="activeModal === 'signin' ? SignInComponent : SignUpComponent"
          :key="activeModal"
          @close="showModal = false"
          @switch-to-signup="activeModal = 'signup'"
          @switch-to-signin="activeModal = 'signin'"
        />
        
      </transition>
    </div>
  <div :class="['showtimes-page', { 'modal-open': showModal }]">
      <NavBar
        @sign-in="showModal = true; activeModal = 'signin'"
        @sign-up="showModal = true; activeModal = 'signup'"
              @open-search="showSearch = true"
      />
    <!-- Main Content -->
  <div class="content">
    <MovieDetails
      v-if="movie"
      :movie="movie"
      @add-to-wishlist="handleAddToWishlist"
    />
    <p v-else>No movie found for ID: {{ movieId }}</p>
  </div>
  </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import NavBar from '@/components/NavBar.vue'
import MovieDetails from '@/components/MovieDetails.vue'
import SignInComponent from '@/components/SignInComponent.vue'
import SignUpComponent from '@/components/SignUpComponent.vue'
import SearchOverlay from '@/components/SearchOverlay.vue'
import axios from 'axios'
import SpinnerComponent from '@/components/SpinnerComponent.vue'

const loading = ref(true)

const showModal = ref(false)
const activeModal = ref('signin')
const showSearch = ref(false)
const wishlist = ref([])
const wishlistId = ref(null) // For quick lookup

const route = useRoute()
const movie = ref(null)
// ✅ Retrieve movie data
const movieId = route.params.id

async function handleAddToWishlist() {
  loading.value = true
  console.log("Toggling wishlist for movie ID:", movieId)

  if (wishlist.value.includes(movieId)) {
    wishlist.value = wishlist.value.filter(m => m !== movieId)
  } else {
    try {
      const response = await axios.post(`http://localhost:8080/starlight/wishlist/add-to-wishlist/${wishlistId.value}/${movieId}`)
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

onMounted(() => {
  fetchMovie()
  fetchWishlist()
});

async function fetchMovie() {
  try {
    loading.value = true
    const res = await axios.get(`http://localhost:8080/starlight/movie/read/${movieId}`)
    // ✅ Safely handle Base64 image
    const movieData = res.data;
    movieData.photo = movieData.photo
      ? `data:image/jpeg;base64,${movieData.photo}`
      : ""; // fallback if missing

    movie.value = movieData;
  } catch (err) {
    loading.value = false
    console.error(err)
  } finally {
    loading.value = false
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





</script>


<style scoped>



/* Blur & dim background when modal is open */
.showtimes-page {
  transition: filter 0.3s ease, opacity 0.3s ease;
}

.showtimes-page.modal-open {
  filter: blur(5px) brightness(0.6);
  pointer-events: none;
}



.showtimes-page {
  min-height: 100vh;
  background: var(--bg, #000);
  color: white;
  position: relative;
}

.content {
  padding: 20px;
  text-align: center;
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
