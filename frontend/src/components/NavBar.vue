<template>
  <nav class="navbar">
    <div class="top-row">
      <!-- Left: Logo -->
      <router-link to="/">
        <div class="logo">
          <img :src="logo" alt="Starlight Logo" />
        </div>
      </router-link>

      <!-- Middle/Right: Actions -->
      <div :class="['actions', { open: menuOpen }]">
        <button><i class="fa-solid fa-location-dot"></i> Find A Cinema</button>
        <button @click="$emit('open-search')">
          <i class="fa-solid fa-magnifying-glass"></i> Search
        </button>
        <button class="primary">
          Select Theatre <i class="fa-solid fa-caret-down"></i>
        </button>

        <!-- Show different actions depending on login -->
        <template v-if="!user">
          <button @click="$emit('sign-in')">
            <i class="fa-regular fa-user"></i> Sign In
          </button>
          <button @click="$emit('sign-up')">
            <i class="fa-solid fa-user-plus"></i> Create Starlight Club Profile
          </button>
        </template>

        <template v-else>
          <div
            class="user-menu"
            @mouseenter="openDropdown"
            @mouseleave="closeDropdown"
            @click="isMobile && (userMenuOpen = !userMenuOpen)"
          >
            <div class="user-info">
              <img :src="photoSrc" alt="User Avatar" class="user-avatar" />
              <span>{{ user.name.firstName }}</span>
            </div>

            <!-- Dropdown Menu -->
            <div class="user-dropdown" v-if="userMenuOpen">
              <router-link to="/profile/basic">Profile</router-link>
              <router-link :to="{ name: 'MyLikes' }">My Likes</router-link>
              <router-link :to="{ name: 'OrderHistory' }">Order History</router-link>
              <hr />
              <a href="#" @click.prevent="signOut">Sign Out</a>
            </div>
          </div>
        </template>
      </div>

      <!-- Right: Hamburger (mobile only) -->
      <button class="hamburger" @click="toggleMenu">
        <i class="fa-solid fa-bars"></i>
      </button>
    </div>

    <!-- Bottom row navigation -->
    <div :class="['bottom-row', { open: menuOpen }]">
      <div class="nav-item" v-for="(item, index) in navLinks" :key="index">
        <a
          href="#"
          @mouseenter="item.active = true"
          @mouseleave="item.active = false"
          @click.prevent="item.active = !item.active"
        >
          {{ item.name }} <i class="fa-solid fa-caret-down"></i>
        </a>
        <div class="dropdown" v-if="item.active">
          <a v-for="(sub, i) in item.subLinks" :key="i" href="#">{{ sub }}</a>
        </div>
      </div>
    </div>
  </nav>

  <!-- Confirmation Modal -->
  <div v-if="showLogoutConfirm" class="logout-modal-overlay">
    <div class="logout-modal">
      <h3>Confirm Sign Out</h3>
      <p>Are you sure you want to sign out of your Starlight account?</p>
      <div class="modal-actions">
        <button class="cancel-btn" @click="showLogoutConfirm = false">Cancel</button>
        <button class="confirm-btn" @click="confirmSignOut">Sign Out</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, onBeforeUnmount, computed, defineExpose } from "vue";
import { useRouter } from "vue-router";
import { userApi } from "@/api/user.js";
import logo from "@/assets/logo.png";
import defaultAvatar from "@/assets/default_avatar.png";

const router = useRouter();
const user = ref(null);

const isMobile = ref(window.innerWidth <= 768);
const menuOpen = ref(false);
const userMenuOpen = ref(false);
const showLogoutConfirm = ref(false); // 👈 modal state
let hideTimeout = null;

function toggleMenu() {
  menuOpen.value = !menuOpen.value;
}

function openDropdown() {
  if (!isMobile.value) {
    clearTimeout(hideTimeout);
    userMenuOpen.value = true;
  }
}

function closeDropdown() {
  if (!isMobile.value) {
    hideTimeout = setTimeout(() => {
      userMenuOpen.value = false;
    }, 200);
  }
}

async function loadUser() {
  const id = localStorage.getItem("customerId");
  if (id) {
    const res = await userApi.getCustomer(id);
    user.value = res.data;
  } else {
    user.value = null;
  }
}
defineExpose({ loadUser });

const navLinks = reactive([
  {
    name: "MOVIES",
    active: false,
    subLinks: ["Now Showing", "Coming Soon", "Special Screenings"],
  },
  {
    name: "CINEMAS",
    active: false,
    subLinks: ["Find A Cinema", "Cinema Map", "Offers"],
  },
  {
    name: "EXCLUSIVE TO SL",
    active: false,
    subLinks: ["SL Exclusives", "Events"],
  },
  {
    name: "SL-XPERIENCE",
    active: false,
    subLinks: ["SL Experience", "Booking Info"],
  },
  {
    name: "SL NEWS",
    active: false,
    subLinks: ["Latest News", "Press Releases"],
  },
]);

function signOut() {
  showLogoutConfirm.value = true; // 👈 open modal
}

function confirmSignOut() {
  user.value = null;
  localStorage.removeItem("customerId");
  userMenuOpen.value = false;
  showLogoutConfirm.value = false;
  router.push("/");
}

function handleResize() {
  isMobile.value = window.innerWidth <= 768;
}

onMounted(async () => {
  await loadUser();
  window.addEventListener("resize", handleResize);
});

onBeforeUnmount(() => window.removeEventListener("resize", handleResize));

const photoSrc = computed(() => {
  if (!user.value?.photo) return defaultAvatar;
  return "data:image/jpeg;base64," + user.value.photo;
});
</script>

<style scoped>
.navbar {
  background: black;
  padding: 10px 20px;
  position: sticky;
  top: 0;
  z-index: 1000;
}

.top-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.logo img {
  height: 60px;
}

.actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
  align-items: center;
  flex: 1;
}
.actions button {
  padding: 8px 12px;
  border-radius: 6px;
  background: #222;
  font-size: 16px;
  font-weight: bold;
  color: white;
  display: flex;
  align-items: center;
  gap: 4px;
}
.actions .primary {
  background: var(--primary-blue);
  color: white;
}

.user-menu {
  position: relative;
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: bold;
  color: white;
  font-size: 18px;
  cursor: pointer;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 6px;
}
.user-avatar {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid var(--primary-blue);
}

.user-dropdown {
  position: absolute;
  top: 55px;
  right: 0;
  background: #111;
  border-radius: 6px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 8px;
  min-width: 180px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
  z-index: 2000;
}
.user-dropdown a,
.user-dropdown router-link {
  color: white;
  text-decoration: none;
  font-size: 14px;
}
.user-dropdown a:hover,
.user-dropdown router-link:hover {
  color: var(--primary-blue);
}
.user-dropdown hr {
  border: none;
  border-top: 1px solid #333;
  margin: 6px 0;
}

.bottom-row {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  transition: max-height 0.3s ease;
}
.nav-item {
  position: relative;
}
.nav-item a {
  color: white;
  font-size: 17px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 4px;
  text-decoration: none;
  cursor: pointer;
}
.dropdown {
  position: absolute;
  top: 30px;
  left: 0;
  background: #111;
  padding: 10px;
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  gap: 6px;
  min-width: 150px;
  opacity: 0;
  transform: translateY(-10px);
  pointer-events: none;
  transition: all 0.2s ease-in-out;
}
.nav-item:hover .dropdown {
  opacity: 1;
  transform: translateY(0);
  pointer-events: auto;
}
.dropdown a {
  color: white;
  font-size: 13px;
  text-decoration: none;
}
.dropdown a:hover {
  color: var(--primary-blue);
}

.hamburger {
  display: none;
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  margin-left: 12px;
}

@media (max-width: 768px) {
  .hamburger {
    display: block;
  }
  .actions,
  .bottom-row {
    flex-direction: column;
    width: 100%;
    overflow: hidden;
    max-height: 0;
  }
  .actions.open,
  .bottom-row.open {
    max-height: 1000px;
  }
  .nav-item .dropdown {
    position: static;
    transform: translateY(0);
    opacity: 1;
    pointer-events: auto;
    background: #111;
    margin-top: 5px;
  }
}

/* Logout Modal */
.logout-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 3000;
}

.logout-modal {
  background: #111;
  padding: 20px;
  border-radius: 10px;
  max-width: 400px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.4);
  color: white;
}

.logout-modal h3 {
  margin-bottom: 10px;
  color: var(--primary-blue);
}

.modal-actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 20px;
}

.cancel-btn,
.confirm-btn {
  padding: 8px 14px;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
  border: none;
}

.cancel-btn {
  background: #444;
  color: white;
}

.confirm-btn {
  background: var(--primary-blue);
  color: white;
}
</style>
