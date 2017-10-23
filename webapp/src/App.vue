<template>
  <div id="app">
    <div id="container">
      <div v-if="login" class="content">
        <NavBar :role="role"></NavBar>
        <router-view></router-view>
      </div>
      <div v-else class="content">
        <router-view></router-view>
      </div>
    </div>
    <MyFooter></MyFooter>
  </div>
</template>

<script>
  import Index from './components/Index'
  import NavBar from './components/Nav'
  import MyFooter from './components/Footer'

  export default {
    name: 'app',
    components: {Index, NavBar, MyFooter},
    data () {
      return {
        login: true,
        role: 2
      }
    }
  }
</script>

<style>
  html, body {
    margin: 0;
    padding: 0;
    height: 100%;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    height: 100%;
  }

  #container {
    min-height: 100%;
  }

  .content {
    padding-bottom: 100px;
  }
</style>
