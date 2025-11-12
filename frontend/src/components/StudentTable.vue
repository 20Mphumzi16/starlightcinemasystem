<template>
  <div class="student-table-container">
    <h2 class="heading">Students</h2>
        <SpinnerComponent :visible="isLoading"/>


    <!-- Filter Section -->
    <div class="filters">
      <button @click="toggleFilterMenu" class="add-filter-btn">
        <font-awesome-icon icon="filter" style="margin-right: 6px;"/>
        Add Filter
      </button>

      <!-- Filter Dropdown -->
      <select v-if="showFilterMenu" @change="addFilter" class="filter-select">
        <option disabled selected value="">Select filter...</option>
        <option value="status">Status</option>
        <option value="tutor">Tutor</option>
      </select>

      <!-- Active Filters -->
      <div class="filter-tags">
        <span
            v-for="(value, key) in activeFilters"
            :key="key"
            class="filter-tag"
        >
          {{ keyLabel(key) }} is {{ value }}
          <button @click="removeFilter(key)">×</button>
        </span>

        <button v-if="Object.keys(activeFilters).length" @click="clearFilters" class="clear-btn">
          Clear all
        </button>
      </div>
    </div>

    <!-- Students Table -->
    <div class="table-wrapper">
      <table class="student-table">
        <thead>
        <tr>
          <th>Name</th>
          <th>Email</th>
          <th>Assigned Tutor</th>
          <th>Sessions</th>
          <th>Next Session</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr
            v-for="student in filteredStudents"
            :key="student.id"
        >
          <td class="name-cell">
            <img :src="getPhotoUrl(student.image)" class="avatar"/>

            {{ studentName(student) }}
          </td>
          <td>{{ student.email }}</td>
          <td>{{ student.tutor }}</td>
          <td>{{ student.sessions }}</td>
          <td>{{ student.nextSession }}</td>
          <td>{{ student.status }}</td>
          <td>
            <button @click="viewStudent(student.id)">View</button>
            <button @click="editStudent(student.id)">Edit</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import {ref, computed,} from 'vue';
import SpinnerComponent from "@/components/SpinnerComponent.vue";
const isLoading = ref(false);
const students = ref([]);
const activeFilters = ref({});
const showFilterMenu = ref(false);

const toggleFilterMenu = () => {
  showFilterMenu.value = !showFilterMenu.value;
};

const addFilter = (event) => {
  const key = event.target.value;
  let value = prompt(`Enter value for ${key}:`);
  if (value) {
    activeFilters.value[key] = value;
  }
  event.target.selectedIndex = 0;
};
const getPhotoUrl = (photo) => {
  return `data:image/jpeg;base64,${photo}`;
}
const studentName = (student) => {


  return `${student.name.firstName} ${student.name.lastName}`;

}

const removeFilter = (key) => {
  delete activeFilters.value[key];
};

const clearFilters = () => {
  activeFilters.value = {};
};

const keyLabel = (key) => {
  return key.charAt(0).toUpperCase() + key.slice(1);
};

const filteredStudents = computed(() => {
  return students.value.filter(student => {
    return Object.entries(activeFilters.value).every(([key, value]) =>
        String(student[key]).toLowerCase().includes(value.toLowerCase())
    );
  });
});

const viewStudent = (id) => {
  alert(`Viewing student with ID: ${id}`);
};

const editStudent = (id) => {
  alert(`Editing student with ID: ${id}`);
};

// const fetchStudents = async () => {
//   try {
//     isLoading.value = true;
//     await new Promise(resolve => setTimeout(resolve, 1500));  //Remove later
//     const response = await getAllStudents();
//     students.value = response.data;
//     console.log(response.data);

//   } catch (error) {
//     console.error('Error fetching students:', error);
//   }
//   finally {
//     isLoading.value=false;
//   }
// };

// onMounted(fetchStudents);
</script>


<style scoped>
.student-table-container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.heading {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
}

/* Filter UI */
.filters {
  margin-bottom: 12px;
}

.add-filter-btn {
  padding: 6px 12px;
  cursor: pointer;
  background-color: #2d3748;
  color: white;
  border: none;
  border-radius: 5px;
  transition: background 0.1s;
}

.add-filter-btn :hover {
  background-color: #0056b3;
}

.filter-select {
  margin-left: 10px;
  padding: 6px;
  border-radius: 5px;
}

.filter-tags {
  margin-top: 10px;
}

.filter-tag {
  display: inline-block;
  background-color: #e0e0e0;
  margin: 5px 5px 0 0;
  padding: 5px 10px;
  border-radius: 16px;
  font-size: 14px;
}

.filter-tag button {
  margin-left: 6px;
  border: none;
  background: none;
  cursor: pointer;
}

.clear-btn {
  margin-left: 10px;
  background: none;
  border: none;
  color: #007bff;
  cursor: pointer;
  font-size: 14px;
  padding: 5px;
}

/* Table UI */
.table-wrapper {
  overflow-x: auto;
}

.student-table {
  width: 100%;
  border-collapse: collapse;

}

.student-table th,
.student-table td {
  padding: 12px;
  border: 1px solid #ccc;
  text-align: left;
}

.student-table th {
  background-color: #2d3748;
  color: #ffffff;


}

.name-cell {
  display: flex;
  align-items: center;
  gap: 8px;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: contain;
}
</style>
