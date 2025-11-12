<template>
  <header class="top-nav">
    <div class="icons">
      <span class="icon"><font-awesome-icon icon="bell"/></span>
      <span class="icon"><font-awesome-icon icon="message"/></span>
      <span class="profile">M</span>
    </div>
  </header>
</template>

<script >
export default {
  name: 'TopNav',
}

</script>

<style scoped>
.top-nav {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 10px 20px;
  background-color: #ffffff;
  border-bottom: 1px solid #ddd;
}

.icons {
  display: flex;
  align-items: center;
  gap: 15px;
}

.icon {
  font-size: 18px;
  cursor: pointer;
}

.profile {
  background-color: #f97316;
  color: white;
  font-weight: bold;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  text-align: center;
  line-height: 30px;
}
</style>
