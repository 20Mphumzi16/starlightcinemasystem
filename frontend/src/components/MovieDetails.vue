<template>
  <div class="movie-details">
    <div class="content">
      <!-- LEFT COLUMN -->
      <div class="left-panel">
        <h1><strong>{{ movie.title }}</strong></h1>

        <!-- Poster + Trailer -->
        <div class="poster-wrapper">
  <div class="poster-bg" :style="{ backgroundImage: `url(${movie.photo})` }"></div>
  <div class="poster">
    <!-- If trailer is being shown -->
    <iframe
      v-if="showTrailer && embedUrl"
      :src="embedUrl"
      frameborder="0"
      allow="autoplay; encrypted-media"
      allowfullscreen
    ></iframe>

    <!-- Otherwise show the poster image -->
    <img
      v-else
      :src="movie.photo"
      alt="Movie Poster"
    />

    <!-- Play Trailer Button (only if valid trailerUrl exists and not already showing) -->
    <button
      v-if="!showTrailer && embedUrl"
      class="play-btn"
      @click="showTrailer = true"
    >
      <i class="fa-solid fa-play"></i>
      Watch Trailer
    </button>
  </div>
</div>

        <!-- DETAILS SECTION -->
        <div class="details-row">
          <!-- Info -->
          <div class="info">
            <p><strong>Release date:</strong> {{ movie.releaseDate }}</p>
            <p><strong>Duration:</strong> {{ movie.durationMinutes }} Minutes</p>
            <p><strong>Rating:</strong> {{ movie.rating?.rating }}</p>
            <p>
              <strong>Genres:</strong>
              {{ movie.genres?.map(g => g.name).join(", ") }}
            </p>
          </div>

          <!-- Right Side (dates/times/selection) -->
          <div class="right-side">
            <!-- Locations / Wishlist -->
            <div class="other-locations">
              <p class="see-also">SEE ALSO</p>
              <a href="#"><i class="fa-solid fa-location-dot"></i> Other Locations</a>
              <button class="wishlist-btn" @click="$emit('add-to-wishlist')">
                <i class="fa-solid fa-heart"></i> Add to Wishlist
              </button>
            </div>

            <!-- AVAILABLE DATES -->
            <div class="dates">
              <span
                v-for="(date, index) in availableDates"
                :key="index"
                class="date"
                :class="{ active: selectedDate === date }"
                @click="selectDate(date)"
              >
                {{ formatDate(date) }}
              </span>
            </div>

            <!-- SHOW TIMES FOR SELECTED DATE -->
            <div class="times" v-if="selectedDate">
              <button
                v-for="show in showsByDate[selectedDate]"
                :key="show.id"
                @click="selectedShow = show"
                :class="{ active: selectedShow?.id === show.id }"
              >
                {{ formatTime(show.startTime) }}
                <small>({{ show.hall?.branch?.name }})</small>
              </button>
            </div>

            <!-- SELECTION SUMMARY -->
            <div class="selection" v-if="selectedShow">
              <p>
                You selected:
                <strong>{{ formatDate(selectedDate) }}</strong> at
                <strong>{{ formatTime(selectedShow.startTime) }}</strong>
                <br />
                <small>({{ selectedShow.hall?.branch?.name }} - {{ selectedShow.hall?.name }})</small>
              </p>
            </div>

            <!-- Proceed Button -->
            <div class="proceed-selection">
              <router-link
                v-if="selectedShow"
                :to="{ name: 'seat-selection', params: { showId: selectedShow.id } }"
              >
                <button>
                  <i class="fa-solid fa-chair"></i> Proceed to Seat Selection
                </button>
              </router-link>
              <button v-else disabled>Select a date and time first</button>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>

import { ref, computed, onMounted, defineProps, watch, toRef } from "vue";
import axios from "axios";

const props = defineProps({
  movie: { type: Object, required: true },
});
const movie = toRef(props, 'movie');

const shows = ref([]);
const selectedDate = ref("");
const selectedShow = ref(null);

// Trailer logic
const showTrailer = ref(false);

// Safely extract YouTube ID
function extractYouTubeId(url) {
  if (!url || typeof url !== "string") return null; // ✅ handle undefined/null/non-string
  const match = url.match(
    /(?:youtu\.be\/|youtube\.com\/(?:embed\/|watch\?v=))([^?&"'>]+)/
  );
  return match && match[1] ? match[1] : null;
}

// Only compute embedUrl if trailerUrl exists and is valid
const embedUrl = computed(() => {
  if (!movie.value || !movie.value.trailerUrl) return null; // ✅ handle undefined movie
  const id = extractYouTubeId(movie.value.trailerUrl);
  return id ? `https://www.youtube.com/embed/${id}?autoplay=1&rel=0` : null;
});

// Reset trailer when movie changes
watch(movie, () => {
  showTrailer.value = false;
});

// Fetch shows for this movie
onMounted(async () => {
  try {
    const { data } = await axios.get(
      `http://localhost:8080/starlight/show/by-movie/${movie.value.id}`
    );
    shows.value = data || [];
  } catch (err) {
    console.error("❌ Failed to fetch shows:", err);
  }
});

// Group shows by date
const showsByDate = computed(() => {
  const map = {};
  shows.value.forEach((show) => {
    const date = show.startTime.split("T")[0];
    if (!map[date]) map[date] = [];
    map[date].push(show);
  });
  return map;
});

// Available dates
const availableDates = computed(() => Object.keys(showsByDate.value).sort());

// Helpers
function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleDateString("en-GB", {
    weekday: "short",
    day: "numeric",
    month: "short",
  });
}

function formatTime(isoTime) {
  const date = new Date(isoTime);
  return date.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
}

function selectDate(date) {
  selectedDate.value = date;
  selectedShow.value = null;
}
</script>


<style scoped>
/* ----- tweak these two variables to control layout quickly ----- */
/* --details-gap: space between info and right-side (smaller = closer) */
/* --info-width: width of the info column (smaller = more compact) */
:root {
  --details-gap: 40px;
  /* change this to push/pull the two columns */
  --info-width: 300px;
  /* width of the left info block */
  --right-width: 420px;
  /* width of the right side block */
}

/* -------------------------------------------------------------- */

.movie-details {
  background: #000;
  color: #fff;
  min-height: 100vh;
  padding: 20px;
  font-family: Arial, sans-serif;
}

/* The details row: left info + right controls */
.details-row {
  display: flex;
  align-items: flex-start;

  gap: 60px;
  /* use the variable here */
  margin-top: 30px;
  flex-wrap: wrap;
  /* stacks on smaller screens */
  justify-content: center;
  /* keep centered on wide screens */
}

.proceed-selection {
  margin-top: 20px;
}

.proceed-selection button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background: #28a745;
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 0.95rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.proceed-selection button i {
  font-size: 1rem;
}

.proceed-selection button:hover {
  background: #218838;
}

/* Make the info block a fixed-width column so it doesn't expand */
.info {
  flex: 0 0 var(--info-width);
  /* fixed basis, doesn't grow */
  max-width: var(--info-width);
  text-align: left;
  font-size: 0.95rem;
  color: #ccc;


}

.wishlist-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  margin-top: 8px;
  margin-left: 10px;
  margin-bottom: 10px;
  padding: 8px 14px;
  background: #1e90ff;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.2s ease;
}

.wishlist-btn i {
  font-size: 1rem;
}

.wishlist-btn:hover {
  background: #e73370;
}

/* Small spacing for the info lines */
.info p {
  margin: 6px 0;
}

/* Right side gets its own fixed-ish width to sit next to info */
.right-side {
  flex: 0 0 var(--right-width);
  max-width: calc(var(--right-width));
  box-sizing: border-box;
  /* Align content to the left within the right-side column */
  text-align: left;
}

/* Layout helpers (keeps poster as you originally had it) */
.content {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
}

.left-panel {
  flex: 1;
  min-width: 500px;
}

/* Poster (unchanged) */
.poster-wrapper {
  position: relative;
  width: 100%;
  height: 500px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #333;
  overflow: hidden;
  border-radius: 10px;
  margin: 20px auto;
  max-width: 1200px;
}

.poster-bg {
  position: absolute;
  inset: 0;
  background-size: cover;
  background-position: center;
  filter: blur(20px) brightness(0.6);
  transform: scale(1.1);
  z-index: 0;
}

.poster {
  position: relative;
  z-index: 1;
  height: 95%;
  max-width: 800px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.7);
}

.poster img,
.poster iframe {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  border-radius: 10px;
}

/* Dates */
.dates {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin: 12px 0 18px;
}

.date {
  padding: 10px 15px;
  border-radius: 6px;
  cursor: pointer;
  background: #222;
  color: #fff;
  font-size: 0.9rem;
  border: 1px solid #333;
  min-width: 60px;
  text-align: center;
  transition: all 0.2s ease;
}

.date:hover {
  background: #444;
}

.date.active {
  background: gold;
  color: #000;
  font-weight: bold;
}

/* Times */
.times {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.times button {
  background: #1e90ff;
  border: none;
  color: #fff;
  padding: 10px 20px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.95rem;
  transition: all 0.2s ease;
}

.times button:hover {
  background: #0d6efd;
}

.times button.active {
  background: #0d6efd;
  font-weight: bold;
}

/* Selection */
.selection {
  margin-top: 16px;
  color: #ccc;
  font-size: 0.9rem;
}

/* Other Locations */
.other-locations {
  margin-top: 6px;
}

.see-also {
  font-size: 0.8rem;
  text-transform: uppercase;
  color: #aaa;
  margin-bottom: 5px;
}

.other-locations a {
  color: #1e90ff;
  text-decoration: none;
  font-weight: bold;
}

.other-locations a:hover {
  text-decoration: underline;
}
/* Play button overlay inside poster */
.play-btn {
  position: absolute;
  bottom: 20px;
  left: 20px;
  background: rgba(255, 0, 0, 0.9);
  color: white;
  padding: 10px 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 1rem;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.2s ease;
  z-index: 2;
}

.play-btn i {
  font-size: 1rem;
}

.play-btn:hover {
  background: rgba(255, 0, 0, 1);
}

/* Responsive: when the screen is narrow, stack the columns */
@media (max-width: 880px) {
  .details-row {
    justify-content: flex-start;
  }

  :root {
    --details-gap: 18px;
    --info-width: 100%;
    --right-width: 100%;
  }

  .info,
  .right-side {
    flex: 0 0 100%;
    max-width: 100%;
  }
}
</style>
