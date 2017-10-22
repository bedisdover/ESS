<template>
  <div id="app">
    <div v-if="login">
      <NavBar :role="role"></NavBar>
      <router-view></router-view>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  import Index from './components/Index'
  import NavBar from './components/Nav'

  export default {
    name: 'app',
    components: {Index, NavBar},
    data () {
      return {
        login: true,
        role: 2
      }
    }
  }
</script>

<style>
  body {
    margin: 0;
    padding: 0;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  .main {
    margin-top: 70px;
  }
</style>
