<template>
  <section class="section">
    <div class="box">
      <nav class="level">
        <div class="level-left">
          <router-link
            to="/"
            class="level-item button is-black is-outlined is-large"
            >Home</router-link
          >
        </div>
      </nav>
      <router-view />
    </div>
  </section>
</template>

<script>

export default {
  name: 'App', 
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
