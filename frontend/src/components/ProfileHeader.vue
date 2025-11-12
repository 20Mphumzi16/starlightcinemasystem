<template>
  <div class="profile-header">
    <div class="avatar">M</div>
    <div class="info">
      <h2>{{ name }}</h2>
      <p>{{ location }} · {{ time }}</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  name: String,
  location: String,
  time: String,
})
</script>
