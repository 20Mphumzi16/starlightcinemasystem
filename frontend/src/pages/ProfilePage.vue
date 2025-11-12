<template>
  <div class="profile-page">
    <SpinnerComponent v-if="loading" />
    <!-- ✅ Full-width NavBar -->
    <NavBar
      @sign-in="showModal = true; activeModal = 'signin'"
      @sign-up="showModal = true; activeModal = 'signup'"
      @open-search="showSearch = true"
    />

    <!-- Root wrapper with blur when modal is open -->
    <div :class="['profile-layout', { 'modal-open': showModal }]">
      <!-- Sidebar -->
      <aside class="sidebar">
        <div class="user-info">
          <div class="avatar-wrapper">
            <img
              :src="photoSrc"
              alt="User Avatar"
              class="avatar"
              @click="fileInput.click()"
            />
            <input
              type="file"
              ref="fileInput"
              class="hidden"
              @change="handleFileChange"
              accept="image/*"
            />
          </div>
          <h3>{{ customer?.name?.firstName || 'User' }} {{ customer?.name?.lastName || '' }}</h3>
        </div>
        <nav>
          <RouterLink :to="{ name: 'BasicInfo' }" class="link" active-class="active">My Profile</RouterLink>
          <RouterLink :to="{ name: 'OrderHistory' }" class="link" active-class="active">Order History</RouterLink>
          <RouterLink :to="{ name: 'MyLikes' }" class="link" active-class="active">My Likes</RouterLink>
          <RouterLink :to="{ name: 'MyTickets' }" class="link" active-class="active">My Tickets</RouterLink>
          <RouterLink to="/" class="link">Logout</RouterLink>
        </nav>
      </aside>

      <!-- Main Content -->
      <main class="content">
        <!-- Tabs only for My Profile -->
        <div v-if="showTabs" class="tabs">
          <RouterLink :to="{ name: 'BasicInfo' }" class="tab" active-class="active">BASIC INFO</RouterLink>
          <RouterLink :to="{ name: 'DetailedInfo' }" class="tab" active-class="active">DETAILED INFO</RouterLink>
          <RouterLink :to="{ name: 'ChangePassword' }" class="tab" active-class="active">CHANGE PASSWORD</RouterLink>
        </div>

        <!-- Child View -->
        <router-view  
        :wishlistMovies="wishlistMovies"
  @remove="removeFromWishlist"
  :bookings="userBookings" @view="handleViewBooking"

  />
      </main>
    </div>

    <!-- Search Overlay -->
    <SearchOverlay
      v-if="showSearch"
      @close="showSearch = false"
    />

    <!-- Sign In / Sign Up Modal -->
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
  </div>
</template>

<script setup>
import { ref, computed,onMounted  } from "vue"
import axios from "axios"
import { useRoute } from "vue-router"
import NavBar from "@/components/NavBar.vue"
import SearchOverlay from "@/components/SearchOverlay.vue"
import SignInComponent from "@/components/SignInComponent.vue"
import SignUpComponent from "@/components/SignUpComponent.vue"
import { useCustomerProfile } from "@/composables/useCustomerProfile.js"
import defaultAvatar from "@/assets/default_avatar.png"
import SpinnerComponent from "@/components/SpinnerComponent.vue"

const route = useRoute()
const profileTabRoutes = ["BasicInfo", "DetailedInfo", "ChangePassword", "InvoiceDetails"]
const showTabs = computed(() => profileTabRoutes.includes(route.name))

const showModal = ref(false)
const activeModal = ref("signin")
const showSearch = ref(false)
const loading = ref(false)

onMounted(() => {
  fetchWishlist()
})

const { customer, updateCustomer } = useCustomerProfile()
const fileInput = ref(null)

// Convert byte[] to Base64 for avatar display
const photoSrc = computed(() => {
  if (!customer?.value?.photo) return defaultAvatar
  return 'data:image/jpeg;base64,' + customer.value.photo
})

// Sample wishlist data — this would later come from your backend
const wishlistMovies = ref([])
const wishlistId = ref(null) // For quick lookup
async function fetchWishlist() {

  const customerId = localStorage.getItem("customerId")
  if (!customerId) {
    console.warn("⚠️ No customer ID found in localStorage.")
    return
  }

  try {
    loading.value = true
    const response = await axios.get(
      `http://localhost:8080/starlight/wishlist/user-wishlist/${customerId}`
    )
    wishlistMovies.value = response.data.movieSet.map(movieData => ({
      ...movieData,
      photo: movieData.photo
        ? `data:image/jpeg;base64,${movieData.photo}`
        : "" // fallback if missing
    }))
    wishlistId.value = response.data.id

    console.log("✅ Wishlist fetched:", wishlistMovies.value)
  } catch (error) {
    console.error("❌ Error fetching wishlist:", error)
  }
  finally {
    loading.value = false
  } 
}

const userBookings = ref([])

onMounted(async () => {
  const userId = localStorage.getItem("customerId")
  const res = await axios.get(`http://localhost:8080/starlight/booking/by-customer/${userId}`)
  userBookings.value = res.data
})

function handleViewBooking(booking) {
  console.log("View booking clicked:", booking)
}


// Function to remove a movie
async function removeFromWishlist(id) {

  if (!wishlistId.value) {
    console.warn("⚠️ No wishlist ID found in localStorage.")
    return
  }

  try {
    loading.value = true
    const response = await axios.post(`http://localhost:8080/starlight/wishlist/remove-from-wishlist/${wishlistId.value}/${id}`)
    if (response.status === 200) {
      alert("✅ Removed from wishlist:")
    }

  } catch (error) {
    console.error("❌ Error removing from wishlist:", error)
    alert("❌ Failed to remove from wishlist. Please try again.")
  } finally {
    fetchWishlist()
    loading.value = false
  }
}

// Handle file selection
const handleFileChange = async (event) => {
  const file = event.target.files[0]
  if (!file) return

  const reader = new FileReader()
  reader.onload = async (e) => {
    const base64Data = e.target.result.split(",")[1]
    customer.value.photo = base64Data

    // 🔥 Save to backend
    const success = await updateCustomer()
    if (!success) {
      console.error("Failed to update photo on backend")
    }
  }
  reader.readAsDataURL(file)
}
</script>

<style scoped>
.profile-page {
  min-height: 100vh;
  background: #fafafa;
  display: flex;
  flex-direction: column;
}

/* Layout below navbar */
.profile-layout {
  display: flex;
  flex: 1;
}

/* Blur background when modal open */
.profile-layout.modal-open {
  filter: blur(5px) brightness(0.6);
  pointer-events: none;
}

.sidebar {
  width: 250px;
  background: white;
  border-right: 1px solid #ddd;
  padding: 20px;
  flex-shrink: 0;
}

.user-info {
  text-align: center;
  margin-bottom: 20px;
}

.avatar-wrapper {
  position: relative;
  width: 80px;
  height: 80px;
  margin: 0 auto 8px auto;
  cursor: pointer;
}

.avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #0b84d0;
}

.hidden {
  display: none;
}

.sidebar .link {
  display: block;
  padding: 10px;
  color: #444;
  text-decoration: none;
  border-radius: 4px;
}

.sidebar .link.active {
  background: #0b84d0;
  color: white;
}

.content {
  flex: 1;
  padding: 20px;
  background: #fafafa;
  min-width: 0;
}

.tabs {
  display: flex;
  gap: 20px;
  border-bottom: 2px solid #ddd;
  margin-bottom: 20px;
}

.tab {
  padding: 10px 0;
  color: #666;
  text-decoration: none;
}

.tab.active {
  color: #0b84d0;
  font-weight: bold;
  border-bottom: 2px solid #0b84d0;
}

/* Overlay */
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

/* fade transition */
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
