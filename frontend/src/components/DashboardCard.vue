<template>
  <div class="card">
    <font-awesome-icon :icon="icon" class="icon" />
    <div class="content">
      <h3>{{ label }}</h3>
      <p>{{ count }}</p>
    </div>
  </div>
</template>

<script setup>
// eslint-disable-next-line
defineProps({
  label: String,
  count: Number,
  icon: String
})
</script>

<style scoped>
.card {
  background: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  gap: 15px;
}

.icon {
  font-size: 24px;
  color: #4a5568;
}

.content h3 {
  margin: 0;
  font-size: 16px;
  color: #2d3748;
}

.content p {
  margin: 0;
  font-size: 24px;
  font-weight: bold;
  color: #1a202c;
}
</style>
