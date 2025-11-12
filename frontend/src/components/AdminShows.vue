<template>
  <div class="admin-section">
    <h2>🎬 Manage Shows</h2>
<SpinnerComponent :visible="loading" />
    <!-- 🔍 Search & Filters -->
    <div class="filter-bar">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Search by movie or cinema..."
        class="search-input"
      />

      <!-- 🎥 Movie Filter -->
      <div class="movie-filter">
        <label v-for="movie in allMovies" :key="movie" class="movie-option">
          <input type="checkbox" :value="movie" v-model="selectedMovies" />
          {{ movie }}
        </label>
      </div>

      <!-- 📅 Date Range Filter -->
      <div class="date-filter">
        <label>From:</label>
        <input type="date" v-model="startDate" />
        <label>To:</label>
        <input type="date" v-model="endDate" />
      </div>

      <button class="clear-btn" @click="clearFilters">Clear</button>
    </div>

    <!-- ➕ Add Show -->
    <button class="add-btn" @click="showForm = true">+ Add Show</button>

    <!-- 🗓️ Table -->
 <AdminTable
  :columns="[
    { label: 'Movie', key: 'movie' },
    { label: 'Branch', key: 'cinema' },
    { label: 'Hall', key: 'hall' },
    { label: 'Date', key: 'date' },
    { label: 'Start Time', key: 'startTime' },
    { label: 'End Time', key: 'endTime' },
    { label: 'Price', key: 'price' },
    { label: 'Status', key: 'status' }
  ]"
  :data="formattedShows"
  @edit="editShow"
  @delete="deleteShow"
/>


    <!-- 🧾 Form Modal -->
    <ShowForm v-if="showForm" @close="showForm = false" @save="saveShow" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import AdminTable from "@/components/AdminTable.vue";
import ShowForm from "@/components/ShowForm.vue";
import SpinnerComponent from "./SpinnerComponent.vue";

const loading = ref(false);
const showForm = ref(false);
const searchQuery = ref("");
const selectedMovies = ref([]);
const startDate = ref("");
const endDate = ref("");
const shows = ref([]);

// ✅ Fetch shows from backend
const fetchShows = async () => {
  try {
    loading.value = true;
    const { data } = await axios.get("http://localhost:8080/starlight/show/get-all");
    shows.value = data;
  } catch (err) {
    console.error("Failed to fetch shows:", err);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchShows);

// 🎬 Compute movie titles for filter
const allMovies = computed(() => {
  const titles = shows.value.map((s) => s.movie?.title);
  return [...new Set(titles.filter(Boolean))];
});

// 🎯 Filtering logic
const filteredShows = computed(() => {
  return shows.value.filter((show) => {
    const movieTitle = show.movie?.title?.toLowerCase() || "";
    const cinemaName = show.hall?.branch?.name?.toLowerCase() || "";

    const matchesSearch =
      movieTitle.includes(searchQuery.value.toLowerCase()) ||
      cinemaName.includes(searchQuery.value.toLowerCase());

    const matchesMovie =
      selectedMovies.value.length === 0 ||
      selectedMovies.value.includes(show.movie?.title);

    const datePart = show.startTime?.split("T")[0];
    const matchesDateRange =
      (!startDate.value || datePart >= startDate.value) &&
      (!endDate.value || datePart <= endDate.value);

    return matchesSearch && matchesMovie && matchesDateRange;
  });
});

const formattedShows = computed(() =>
  filteredShows.value.map((show) => ({
    id: show.id,
    movie: show.movie?.title || "—",
    cinema: show.hall?.branch?.name || "—",
    hall: show.hall?.name || "—",
    date: show.startTime ? show.startTime.split("T")[0] : "—",
    startTime: show.startTime ? show.startTime.split("T")[1].slice(0, 5) : "—",
    endTime: show.endTime ? show.endTime.split("T")[1].slice(0, 5) : "—",
    price: show.price != null ? `R${show.price.toFixed(2)}` : "—",
    status: show.status || "—",
  }))
);
// 🧹 Reset Filters
function clearFilters() {
  searchQuery.value = "";
  selectedMovies.value = [];
  startDate.value = "";
  endDate.value = "";
}

// ✏️ Edit Show
function editShow(show) {
  alert(`Editing show for: ${show.Movie}`);
}

// ❌ Delete Show (you can extend this to call backend)
function deleteShow(show) {
  shows.value = shows.value.filter((s) => s.id !== show.id);
}

// 💾 Save new show (send FULL movie + hall objects)
async function saveShow(newShow) {
  try {
    const payload = {
      movie: newShow.movie, // full movie object
      hall: newShow.hall,   // full hall object
      startTime: newShow.startTime,
      endTime: newShow.endTime,
      price: newShow.price,
      status: newShow.status,
    };

    await axios.post("http://localhost:8080/starlight/show/create", payload);

    // ✅ Refresh table after creation
    await fetchShows();
    showForm.value = false;
  } catch (err) {
    console.error("❌ Failed to create show:", err);
    alert("Error saving show. Please try again.");
  }
}
</script>

<style scoped>
.admin-section {
  padding: 20px;
}

.filter-bar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
  background: #f8fdf9;
  padding: 12px;
  border-radius: 8px;
  border-left: 4px solid #198754;
}

.search-input {
  padding: 8px;
  flex: 1;
  min-width: 180px;
  border: 1px solid #ccc;
  border-radius: 6px;
}

.movie-filter {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.movie-option {
  font-size: 14px;
}

.date-filter {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
}

.clear-btn {
  background: #6c757d;
  color: white;
  border: none;
  padding: 6px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.add-btn {
  background: #198754;
  color: white;
  padding: 10px 16px;
  border: none;
  border-radius: 6px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: 0.2s;
}

.add-btn:hover {
  background: #157347;
}
</style>
