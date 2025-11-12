<template>
    <SpinnerComponent v-if="loading" />
  <div class="admin-section">
    <h2>Manage Genres</h2>
    <button class="add-btn" @click="showForm = true">+ Add Genre</button>

    <AdminTable
      :columns="['Name', 'Description']"
      :data="genres"
      @edit="editGenre"
      @delete="deleteGenre"
    />

    <GenreForm v-if="showForm" @close="showForm = false" @save="saveGenre" />
  </div>
</template>

<script setup>
import { ref ,onMounted} from 'vue'
import axios from 'axios'       

import AdminTable from '@/components/AdminTable.vue'
import GenreForm from '@/components/GenreForm.vue'
import SpinnerComponent from './SpinnerComponent.vue'

const showForm = ref(false)

const loading = ref(false);
const genres = ref([
])

onMounted(() => {
  fetchGenres();
});
async function fetchGenres() {

  try {
    loading.value = true
    const response = await axios.get('http://localhost:8080/starlight/genres/get-all')
    console.log('✅ Genres fetched:', response.data)
    genres.value = response.data
  } catch (error) {
    console.error('❌ Failed to fetch genres:', error)
  } finally {   
    loading.value = false
  }
}

function editGenre(genre) {
  alert(`Editing genre: ${genre.name}`)
}

async function deleteGenre(genre) {
    alert(`Deleting genre: ${genre.name}`)

    try {
        loading.value = true
        await axios.delete(`http://localhost:8080/starlight/genres/delete/${genre.id}`)
        console.log('✅ Genre deleted')         
    fetchGenres();
  } catch (error) { 
    console.error('❌ Failed to delete genre:', error)
  } finally {
    loading.value = false
  } 
}

async function saveGenre(newGenre) {
  try {
    loading.value = true
    const response = await axios.post('http://localhost:8080/starlight/genres/create', newGenre)
    console.log('✅ Genre saved:', response.data)
   

  } catch (error) {
    console.error('❌ Failed to save genre:', error)
  } finally {
     loading.value = false
    fetchGenres();
  }
}
</script>

<style scoped>
.admin-section {
  padding: 20px;
}

.add-btn {
  background: #0d6efd;
  color: white;
  padding: 10px 16px;
  border: none;
  border-radius: 6px;
  margin-bottom: 15px;
  cursor: pointer;
}
</style>
