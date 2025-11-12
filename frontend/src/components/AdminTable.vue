<template>
  <table class="admin-table">
    <thead>
      <tr>
        <!-- If col is object → use col.label, else just display col -->
        <th v-for="(col, i) in columns" :key="i">
          {{ typeof col === 'object' ? col.label : col }}
        </th>
        <th>Actions</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="item in data" :key="item.id">
        <!-- If col is object → use col.key, else fallback to col.toLowerCase() -->
        <td v-for="(col, i) in columns" :key="i">
          {{ typeof col === 'object' ? item[col.key] : item[col.toLowerCase()] }}
        </td>

        <td>
          <button @click="$emit('edit', item)">✏️ Edit</button>
          <button @click="$emit('delete', item)">🗑️ Delete</button>
        </td>
      </tr>
    </tbody>
  </table>
</template>


<script setup>
import {defineProps} from 'vue'

defineProps({
  columns: Array,
  data: Array
})
</script>

<style scoped>
.admin-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border-radius: 8px;
  overflow: hidden;
}

.admin-table th, .admin-table td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

.admin-table th {
  background: #f1f1f1;
}

button {
  border: none;
  background: none;
  cursor: pointer;
}
</style>
