<template>
  <div class="admin-sidebar">
    <h2 class="logo">🎬 Starlight Admin</h2>

    <nav>
      <RouterLink to="/admin/dashboard" class="link" active-class="active">Dashboard</RouterLink>
      <RouterLink to="/admin/movies" class="link" active-class="active">Movies</RouterLink>
      <RouterLink to="/admin/shows" class="link" active-class="active">Shows</RouterLink>
      <RouterLink to="/admin/genres" class="link" active-class="active">Genres</RouterLink>
    </nav>
  </div>
</template>

<style scoped>
.admin-sidebar {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  height: 100%;
}

.logo {
  text-align: center;
  font-size: 1.4rem;
  margin-bottom: 1.5rem;
  font-weight: bold;
}

nav {
  display: flex;
  flex-direction: column;
}

.link {
  padding: 12px 20px;
  color: #ddd;
  text-decoration: none;
  font-weight: 500;
  transition: 0.2s;
}

.link:hover {
  background: #222;
}

.active {
  background: #0d6efd;
  color: #fff;
}
</style>
