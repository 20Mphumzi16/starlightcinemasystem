<template>
  <div class="hero">
    <div
      v-if="slides.length"
      class="slide"
      :style="{ backgroundImage: `url(${slides[current].poster})` }"
    >
      <div class="overlay">
        <h2>{{ slides[current].title }}</h2>
        <p>{{ slides[current].description || slides[current].title }}</p>
        <div class="buttons">
          <button class="yellow">
            <i class="fa-solid fa-play"></i> Trailer
          </button>
          <button class="blue">Book Now</button>
        </div>
      </div>
    </div>

    <!-- loading fallback -->
    <div v-else class="loading">
      <p>Loading featured movies...</p>
    </div>

    <!-- arrows -->
    <button v-if="slides.length" class="arrow left" @click="prevSlide">
      <i class="fa-solid fa-chevron-left"></i>
    </button>
    <button v-if="slides.length" class="arrow right" @click="nextSlide">
      <i class="fa-solid fa-chevron-right"></i>
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue"
import axios from "axios"

const slides = ref([])
const current = ref(0)
let interval = null

// Fetch movies from backend
const fetchSlides = async () => {
  try {
    const { data } = await axios.get("http://localhost:8080/starlight/movie/get-all")
    // Ensure valid data and map to expected structure
    slides.value = data.map(m => ({
      id: m.id,
      title: m.title,
      description: m.description || "",
      poster: m.photo ? `data:image/jpeg;base64,${m.photo}` : "/default-poster.jpg",
      badge: m.rating || "N/A",
     
    }))
  } catch (err) {
    console.error("❌ Failed to load movies:", err)
  }
}

function prevSlide() {
  current.value = (current.value - 1 + slides.value.length) % slides.value.length
}

function nextSlide() {
  current.value = (current.value + 1) % slides.value.length
}

onMounted(() => {
  fetchSlides()
  interval = setInterval(() => nextSlide(), 5000)
})

onUnmounted(() => {
  clearInterval(interval)
})
</script>

<style scoped>
.hero {
  margin: 20px auto;
  max-width: 1200px;
  border-radius: 10px;
  overflow: hidden;
  position: relative;
}

.slide {
  background-size: cover;
  background-position: center;
  height: 500px;
  display: flex;
  align-items: flex-end;
  border-radius: 10px;
}

.overlay {
  background: rgba(0, 0, 0, 0.5);
  padding: 20px;
  width: 100%;
}

.overlay h2 {
  margin: 0;
  font-size: 24px;
  color: white;
}

.overlay p {
  margin: 4px 0;
  font-size: 14px;
  color: var(--text-muted);
}

.buttons {
  margin-top: 10px;
}

.yellow {
  background: var(--accent-yellow, #f5c518);
  padding: 8px 16px;
  margin-right: 8px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
}

.blue {
  background: var(--primary-blue, #007bff);
  padding: 8px 16px;
  border-radius: 6px;
  color: white;
  border: none;
  cursor: pointer;
}

/* arrows */
.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.5);
  border-radius: 50%;
  color: white;
  padding: 12px;
  cursor: pointer;
  border: none;
}

.arrow.left {
  left: 10px;
}

.arrow.right {
  right: 10px;
}

.arrow:hover {
  background: rgba(0, 0, 0, 0.8);
}

/* loading state */
.loading {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 400px;
  color: #999;
  font-size: 18px;
}
</style>
