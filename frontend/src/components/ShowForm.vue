<template>
  <div class="modal">
    <SpinnerComponent :visible="loading" />
    <div class="modal-content">
      <h3>Add Show</h3>

      <!-- 🎬 Movie Selection -->
      <label>Movie</label>
      <input
        v-model="movieSearch"
        placeholder="Search movie..."
        class="form-control"
      />
   <select v-model="form.movie" class="form-select">
  <option disabled value="">-- Select Movie --</option>
  <option
    v-for="movie in filteredMovies"
    :key="movie.id"
    :value="movie"
  >
    {{ movie.title }}
  </option>
</select>

      <!-- 🏢 Branch Selection -->
      <label>Branch</label>
     <select v-model="form.branch" @change="fetchHalls" class="form-select">
  <option disabled value="">-- Select Branch --</option>
  <option v-for="b in branches" :key="b.id" :value="b">
    {{ b.name }}
  </option>
</select>


      <!-- 🎟️ Hall Selection -->
      <label>Hall</label>
      <select v-model="form.hall" class="form-select">
  <option disabled value="">-- Select Hall --</option>
  <option v-for="h in halls" :key="h.id" :value="h">
    {{ h.name }}
  </option>
</select>

      <!-- 🕒 Start & End Time -->
      <div class="row">
        <div>
          <label>Start Time</label>
          <input type="datetime-local" v-model="form.startTime" />
        </div>
        <div>
          <label>End Time</label>
          <input type="datetime-local" v-model="form.endTime" />
        </div>
      </div>

      <!-- 💰 Price -->
      <label>Price</label>
      <input type="number" v-model="form.price" step="0.01" />

      <!-- ✅ Status -->
      <label>Status</label>
      <select v-model="form.status">
        <option value="CANCELLED">CANCELLED</option>
        <option value="SCHEDULED">SCHEDULED</option>
        <option value="RUNNING">RUNNING</option>
      </select>

      <div class="actions">
        <button class="cancel" @click="$emit('close')">Cancel</button>
        <button class="save" @click="save">Save</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, defineEmits } from "vue";
import axios from "axios";
import SpinnerComponent from "./SpinnerComponent.vue";

const emit = defineEmits(["close", "save"]);
const loading = ref(false);
const form = reactive({
  movieId: "",
  branchId: "",
  hallId: "",
  startTime: "",
  endTime: "",
  price: "",
  status: "ACTIVE",
});

const movies = ref([]);
const branches = ref([]);
const halls = ref([]);
const movieSearch = ref("");

// ✅ Filtered Movies
const filteredMovies = computed(() =>
  movies.value.filter((m) =>
    m.title.toLowerCase().includes(movieSearch.value.toLowerCase())
  )
);

// Fetch Data
const fetchMovies = async () => {
  const { data } = await axios.get("http://localhost:8080/starlight/movie/get-all");
  movies.value = data;
};

const fetchBranches = async () => {
  const { data } = await axios.get("http://localhost:8080/starlight/branch/get-all");
  branches.value = data;
};

const fetchHalls = async () => {
  form.hall = null;
  if (!form.branch) return;
  const { data } = await axios.get(
    `http://localhost:8080/starlight/hall/get-branch-halls/${form.branch.id}`
  );
  halls.value = data;
};

// 🧾 Save show (emit to parent)
function save() {
  loading.value = true;
  const showData = {
    movie: form.movie,
    hall: form.hall,
    startTime: form.startTime,
    endTime: form.endTime,
    price: parseFloat(form.price),
    status: form.status,
  };

  emit("save", showData);
loading.value = false;
}

onMounted(() => {
  fetchMovies();
  fetchBranches();
});
</script>

<style scoped>
.modal {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  min-width: 350px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.form-select,
.form-control,
input,
select {
  width: 100%;
  padding: 6px;
  border-radius: 6px;
  border: 1px solid #ccc;
}
.actions {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
.cancel {
  background: #ccc;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
}
.save {
  background: #198754;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
}
.row {
  display: flex;
  gap: 10px;
}
</style>
