<template>
  <div class="nav">
    <div v-if="role === 1">
      <router-link to="/my">我的课程</router-link>
    </div>
    <div v-else-if="role === 2">
      <span>
        <router-link to="/list">所有课程</router-link>
        <router-link to="/my">我的课程</router-link>
      </span>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'NavBar',
    props: ['role']
  }
</script>

<style scoped>
  .nav {
    width: 100%;
    height: 72px;
    background: #2b83f9 linear-gradient(143deg, #2945cb 20%, #2b83f9 81%, #3a9dff);
  }

  a {
    line-height: 72px;
    color: #dedfe3;
    padding: 6px 20px;
    text-decoration: none;
  }
</style>
