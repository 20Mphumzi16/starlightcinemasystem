<template>
  <div class="tabs">
    <span
        v-for="tab in tabs"
        :key="tab"
        :class="['tab', { active: modelValue === tab }]"
        @click="$emit('update:modelValue', tab)"
    >
      {{ tab }}
    </span>
  </div>
</template>

<script setup>
defineProps({
  modelValue: String,
  tabs: Array
})
</script>
