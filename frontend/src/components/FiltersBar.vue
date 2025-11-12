<template>
  <div class="filters">
    <!-- Tabs -->
    <div class="tabs">
      <button
        :class="{ active: props.tab === 'now' }"
        @click="selectTab('now')"
      >
        Now Showing
      </button>
      <button
        :class="{ active: props.tab === 'coming' }"
        @click="selectTab('coming')"
      >
        Coming Soon
      </button>
      <button
        :class="{ active: props.tab === 'activities' }"
        @click="selectTab('activities')"
      >
        Activities
      </button>
    </div>

    <!-- Dropdown Filters -->
    <div class="dropdowns">
      <label>
        Sort by:
        <select v-model="sortBy" @change="emitFilters">
          <option value="popular">Popular</option>
          <option value="latest">Latest</option>
        </select>
      </label>

      <label>
        Filter by:
        <select v-model="selectedGenre" @change="emitFilters">
          <option value="">All Genres</option>
          <option
            v-for="genre in genres"
            :key="genre.id"
            :value="genre.name"
          >
            {{ genre.name }}
          </option>
        </select>
      </label>

      <label>
        Cinema branch:
        <select v-model="selectedBranch" @change="emitFilters">
          <option value="">All</option>
          <option
            v-for="branch in branches"
            :key="branch.id"
            :value="branch.name"
          >
            {{ branch.name }}
          </option>
        </select>
      </label>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, onMounted } from "vue"
import axios from "axios"

const props = defineProps({
  tab: { type: String, required: true },
})

const emit = defineEmits(["update:tab", "filter-change"])

function selectTab(value) {
  emit("update:tab", value)
  emitFilters()
}

// Local state
const genres = ref([])
const branches = ref([])
const sortBy = ref("popular")
const selectedGenre = ref("")
const selectedBranch = ref("")

function emitFilters() {
  emit("filter-change", {
    sortBy: sortBy.value,
    genre: selectedGenre.value,
    branch: selectedBranch.value,
  })
}

// Fetch from backend
async function fetchGenres() {
  try {
    const { data } = await axios.get("http://localhost:8080/starlight/genres/get-all")
    genres.value = data
  } catch (err) {
    console.error("❌ Failed to fetch genres:", err)
  }
}

async function fetchBranches() {
  try {
    const { data } = await axios.get("http://localhost:8080/starlight/branch/get-all")
    branches.value = data
  } catch (err) {
    console.error("❌ Failed to fetch branches:", err)
  }
}

onMounted(() => {
  fetchGenres()
  fetchBranches()
})
</script>

<style scoped>
.filters {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin: 20px auto 30px;
  max-width: 1200px;
  padding: 0 16px;
}

.tabs {
  display: flex;
  gap: 16px;
}

.tabs button {
  background: none;
  border: none;
  font-size: 22px;
  font-weight: bold;
  cursor: pointer;
  padding: 6px 12px;
  color: #444;
}

.tabs button.active {
  border-bottom: 2px solid var(--primary-blue, #007bff);
  color: var(--primary-blue, #007bff);
}

.dropdowns {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.dropdowns label {
  font-size: 14px;
  color: #333;
}

.dropdowns select {
  margin-left: 6px;
  padding: 4px 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
}
</style>
