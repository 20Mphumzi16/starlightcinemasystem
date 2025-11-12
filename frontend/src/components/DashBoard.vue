<template>
  <main class="main-content">

    <section class="cards-grid">
      <DashboardCard label="Upcoming bookings" :count="0" icon="calendar-check" />
      <DashboardCard label="Pending bookings" :count="0" icon="exclamation-triangle" />
      <DashboardCard label="Cancellation requested" :count="0" icon="minus-circle" />
      <DashboardCard label="Cancelled bookings" :count="0" icon="times-circle" />
      <DashboardCard label="Total refunds" :count="0" icon="undo-alt" />
    </section>
  </main>
</template>

<script setup>
import DashboardCard from '@/components/DashboardCard.vue'
</script>

<style scoped>
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #f9f9f9;
}
.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  padding: 20px;
}
</style>
