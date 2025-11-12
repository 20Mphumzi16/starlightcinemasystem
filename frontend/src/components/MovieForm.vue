<template>
  <div class="overlay" @click.self="$emit('close')">
    <SpinnerComponent :visible="loading" />
    <div class="modal-content animate-fade-in">
      <div class="header">
        <h2 class="title">🎬 Add New Movie</h2>
        <button class="close-btn" @click="$emit('close')">✕</button>
      </div>

      <div class="modal-body">
        <!-- LEFT SIDE: Form -->
        <div class="form-section">
          <div class="form-grid">
            <div class="form-group full-width">
              <label>Title</label>
              <input v-model="form.title" placeholder="Enter movie title..." />
            </div>

            <div class="form-group full-width">
              <label>Poster Image</label>
              <input type="file" accept="image/*" @change="handleFileUpload" />
            </div>

            <div class="form-group full-width">
              <label>Genres</label>
              <div class="genre-list">
                <div
                  v-for="genre in availableGenres"
                  :key="genre.id"
                  class="genre-tag"
                  :class="{ selected: form.genres.includes(genre) }"
                  @click="toggleGenre(genre)"
                >
                  {{ genre.name }}
                </div>
              </div>
            </div>

            <div class="form-group">
              <label>Release Date</label>
              <input type="date" v-model="form.releaseDate" />
            </div>

            <div class="form-group">
              <label>Duration (minutes)</label>
              <input
                type="number"
                v-model="form.durationMinutes"
                min="0"
                placeholder="e.g. 120"
              />
            </div>

            <div class="form-group">
              <label>Parental Rating</label>
      <select v-model="form.rating">
  <option disabled value="">Select rating...</option>
  <option v-for="r in availableRatings" :key="r.id" :value="r">
    {{ r.rating }}
  </option>
</select>
            </div>

            

            <div class="form-group full-width">
              <label>Description</label>
              <textarea
                v-model="form.description"
                rows="5"
                placeholder="Write a short description about the movie..."
              ></textarea>
            </div>
          </div>

          <div class="actions">
            <button class="btn cancel" @click="$emit('close')">Cancel</button>
            <button class="btn save" @click="save">Save Movie</button>
          </div>
        </div>

        <!-- RIGHT SIDE: Live Preview -->
        <div class="preview-section">
          <div class="preview-card">
            <div class="poster-wrapper">
              <img :src="previewUrl || placeholder" alt="Movie poster preview" />
              <div class="badge">{{ previewBadge }}</div>
            </div>
            <div class="preview-content">
              <h3>{{ form.title || 'Movie Title' }}</h3>
              <p class="genres">
                <span v-if="form.genres.length">
                  {{ form.genres.map(g => g.name).join(', ') }}
                </span>
                <span v-else>No genres selected</span>
              </p>
              <p class="info">
                {{ form.releaseDate || 'YYYY-MM-DD' }} •
                {{ form.durationMinutes || '0' }} min •
                {{ form.rating?.code || 'NR' }}
              </p>
              <p class="desc">
                {{ form.description || 'Short movie description goes here...' }}
              </p>
              <div class="actions">
                <button class="blue" disabled>Buy Tickets</button>
                <button class="heart"><i class="fa-regular fa-heart"></i></button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, defineEmits, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from "vue-router"
import SpinnerComponent from './SpinnerComponent.vue';

const loading = ref(false);
const router = useRouter();

const emit = defineEmits(['close', 'save'])

const availableGenres = ref([
])

// this will be populated from backend
const availableRatings = ref([])

const form = reactive({
  title: '',
  photo: null,
  genres: [],
  releaseDate: '',
  durationMinutes: 0,
  rating: null,
  description: ''
})

const previewUrl = ref(null)
const placeholder = "https://via.placeholder.com/300x450?text=Poster+Preview"
const previewBadge = ref('Now Showing')

// Fetch parental ratings from DB
async function fetchRatings() {
  try {
    const response = await axios.get('http://localhost:8080/starlight/pg/get-all');

    console.log('✅ Fetched parental ratings:', response.data);
    availableRatings.value = response.data;
  } catch (error) {
    console.error('❌ Failed to fetch parental ratings:', error);
  }
}

async function fetchGenres() {
  try {
    const response = await axios.get('http://localhost:8080/starlight/genres/get-all')
    console.log('✅ Fetched genres:', response.data)
    availableGenres.value = response.data
  } catch (error) {
    console.error('❌ Failed to fetch genres:', error)
  }
}

// Run on mount
onMounted(() => {
  fetchRatings()
  fetchGenres() 
})

function handleFileUpload(event) {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      const base64 = e.target.result
      form.photo = base64.split(',')[1]
      previewUrl.value = base64
    }
    reader.readAsDataURL(file)
  }
}

function toggleGenre(genre) {
  const index = form.genres.indexOf(genre)
  if (index >= 0) form.genres.splice(index, 1)
  else form.genres.push(genre)
}

async function save() {
  const payload = {
    title: form.title,
    description: form.description,
    photo: form.photo,
    durationMinutes: form.durationMinutes,
    releaseDate: form.releaseDate,
    genres: form.genres.map(g => ({ id: g.id, name: g.name })),
    rating: form.rating ? { id: form.rating.id, rating: form.rating.rating, description: form.rating.description } : null
  }

  try {
    loading.value = true;
    const res = await axios.post('http://localhost:8080/starlight/movie/create', payload)
    console.log('✅ Movie created:', res.data)
    router.push('/admin/movies');
    emit('close')
  } catch (err) {
    console.error('❌ Failed to save movie:', err)
  }
    finally {
        loading.value = false;
    }
}

</script>

<style scoped>
/* Overlay */
.overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.8);
  backdrop-filter: blur(8px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* Modal Container */
.modal-content {
  width: 95%;
  max-width: 1200px;
  background: rgba(30, 30, 30, 0.9);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  padding: 32px 40px;
  color: #fff;
  max-height: 92vh;
  overflow-y: auto;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.6);
}

/* Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.title {
  font-size: 1.8rem;
  font-weight: 700;
  color: #ff3d3d;
}

.close-btn {
  background: none;
  border: none;
  color: #aaa;
  font-size: 1.6rem;
  cursor: pointer;
}

.close-btn:hover {
  color: #fff;
}

/* Layout */
.modal-body {
  display: flex;
  gap: 40px;
}

.form-section {
  flex: 1.2;
}

.preview-section {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

/* Form Styles */
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px 24px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group.full-width {
  grid-column: span 2;
}

label {
  color: #bbb;
  font-size: 0.95rem;
  margin-bottom: 6px;
  font-weight: 500;
}

input,
select,
textarea {
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 10px;
  padding: 10px 12px;
  color: white;
  font-size: 1rem;
  transition: all 0.25s ease;
}

input:focus,
select:focus,
textarea:focus {
  border-color: #ff3d3d;
  background: rgba(255, 255, 255, 0.12);
  outline: none;
}

/* Genre Tags */
.genre-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.genre-tag {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 6px;
  padding: 6px 12px;
  cursor: pointer;
  transition: all 0.25s ease;
  font-size: 0.9rem;
}

.genre-tag:hover {
  background: rgba(255, 255, 255, 0.2);
}

.genre-tag.selected {
  background: #ff3d3d;
  border-color: #ff3d3d;
  color: #fff;
}

/* Buttons */
.actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 30px;
}

.btn {
  border: none;
  border-radius: 10px;
  padding: 12px 22px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.25s ease;
}

.btn.cancel {
  background: rgba(255, 255, 255, 0.1);
  color: #ccc;
}

.btn.cancel:hover {
  background: rgba(255, 255, 255, 0.2);
}

.btn.save {
  background: #ff3d3d;
  color: #fff;
  box-shadow: 0 0 12px rgba(255, 61, 61, 0.4);
}

.btn.save:hover {
  background: #ff5555;
  box-shadow: 0 0 16px rgba(255, 61, 61, 0.6);
}

/* Preview Card */
.preview-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  width: 100%;
  max-width: 360px;
  box-shadow: 0 3px 12px rgba(0, 0, 0, 0.3);
  position: relative;
}
select {
  background: rgba(255, 255, 255, 0.08);
  color: #fff; /* keeps the selected item visible */
}

select option {
  color: #000; /* ensures the options in dropdown are readable */
  background: #fff; /* optional: make dropdown white for visibility */
}


.poster-wrapper {
  position: relative;
}

.poster-wrapper img {
  width: 100%;
  height: 440px;
  object-fit: cover;
}

.badge {
  position: absolute;
  top: 10px;
  left: 10px;
  background: crimson;
  color: #fff;
  font-size: 13px;
  padding: 6px 10px;
  border-radius: 5px;
}

.preview-content {
  padding: 12px 16px;
}

.preview-content h3 {
  color: #222;
  font-size: 18px;
  margin-bottom: 6px;
}

.preview-content p {
  color: #444;
  font-size: 0.9rem;
  margin-bottom: 8px;
}

.preview-content .desc {
  min-height: 60px;
  overflow: hidden;
  text-overflow: ellipsis;
}

.preview-card .actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
}

.preview-card .actions button.blue {
  background: #007bff;
  border: none;
  color: white;
  padding: 6px 12px;
  border-radius: 5px;
  font-size: 14px;
  cursor: not-allowed;
}

.preview-card .actions button.heart {
  background: none;
  border: none;
  font-size: 18px;
  color: crimson;
  cursor: pointer;
}

.animate-fade-in {
  animation: fadeIn 0.3s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
