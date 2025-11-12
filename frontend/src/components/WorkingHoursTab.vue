<template>
  <div class="working-hours">
    <p>What days and hours do you work? This determines your booking availability.</p>
    <div v-for="day in days" :key="day.name" class="day-row">
      <div class="day-label">
        <label class="switch">
          <input type="checkbox" v-model="day.enabled" />
          <span class="slider round"></span>
        </label>
        {{ day.name }}
      </div>
      <div class="time-inputs" v-if="day.enabled">
        <input type="time" v-model="day.start" />
        <span>—</span>
        <input type="time" v-model="day.end" />
      </div>
      <span v-else class="day-off">Day off</span>
    </div>
    <div class="buttons">
      <button @click="emit('cancel')">Cancel</button>
      <button @click="emit('save')">Save</button>
    </div>
  </div>
</template>

<script setup>
defineProps({ days: Array })
const emit = defineEmits(['save', 'cancel'])
</script>
