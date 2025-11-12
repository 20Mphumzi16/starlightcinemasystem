<template>
  <div class="modal-overlay">
    <div class="modal">
      <h3>Edit {{ fieldLabel }}</h3>

      <template v-if="field === 'hours'">
        <div class="day-row">
          <div class="day-label">
            <label class="switch">
              <input type="checkbox" v-model="value.enabled" />
              <span class="slider round"></span>
            </label>
            Today · {{ today }}
          </div>
          <div class="time-inputs" v-if="value.enabled">
            <input type="time" v-model="value.start" />
            <span>—</span>
            <input type="time" v-model="value.end" />
          </div>
          <span v-else class="day-off">Day off</span>
        </div>
      </template>

      <input v-else v-model="value" type="text" />

      <div class="modal-buttons">
        <button @click="$emit('save', value)">Save</button>
        <button @click="$emit('close')">Cancel</button>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps(['field', 'value', 'fieldLabel', 'today'])
defineEmits(['save', 'close'])
</script>
