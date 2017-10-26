<template>
  <div class="nav">
    <span v-if="user.role === 1">
      <router-link to="/my">我的课程</router-link>
    </span>
    <span v-else-if="user.role === 2">
      <span>
        <router-link to="/list">所有课程</router-link>
        <router-link to="/my">我的课程</router-link>
      </span>
    </span>
    <span>
        <a @click="logout">登出</a>
    </span>
  </div>
</template>

<script>
  export default {
    name: 'NavBar',
    props: ['user'],
    methods: {
      logout: function () {
        this.$emit('onLogout')
      }
    }
  }
</script>

<style scoped>
  .nav {
    width: 100%;
    height: 72px;
    background: #2b83f9 linear-gradient(143deg, #2945cb 20%, #2b83f9 81%, #3a9dff);
    margin-bottom: 72px;
    display: inline-block;
  }

  a {
    line-height: 72px;
    color: #dedfe3;
    padding: 6px 20px;
    text-decoration: none;
    display: inline-block;
    cursor: pointer;
  }

  a:hover {
    color: gold;
  }
</style>
