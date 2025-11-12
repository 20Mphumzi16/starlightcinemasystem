<template>
  <div class="quickbook">
    <button><i class="fa-solid fa-bolt"></i> Quick Book</button>
  </div>
</template>

<style scoped>
.quickbook {
  position: fixed;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
}

.quickbook button {
  background: var(--accent-yellow);
  border-radius: 6px 0 0 6px;
  padding: 10px 16px;
  font-weight: bold;
}
</style>
