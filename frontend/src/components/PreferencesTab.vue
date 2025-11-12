<template>
  <div class="preferences-tab">
    <div class="preference-item" v-for="(value, key) in prefs" :key="key">
      <font-awesome-icon :icon="icons[key]" />
      {{ formatLabel(key) }}: <strong>{{ formatValue(value) }}</strong>
    </div>
  </div>
</template>

<script setup>
const props = defineProps(['prefs'])

const icons = {
  location: 'location',
  canTravel: 'car',
  lastLogin: 'power',
  registered: 'user',
  totalTeachingExp: 'graduation-cap',
  teachesOnline: 'wifi',
  onlineTeachingExp: 'globe',
  teachesAtStudentsHome: 'home',
  homeworkHelp: 'book',
  gender: 'venus-mars',
  worksAs: 'brief-case',
  speaks: 'comments',
  feeRange: 'money'
}

const formatLabel = (key) => {
  return key
      .replace(/([A-Z])/g, ' $1')
      .replace(/^./, (str) => str.toUpperCase())
}

const formatValue = (value) => {
  if (typeof value === 'boolean') return value ? 'Yes' : 'No'
  if (typeof value === 'object' && value.local) return `${value.local} (${value.usd})`
  return value
}
</script>
