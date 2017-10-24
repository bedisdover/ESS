<template>
  <div id="app">
    <div class="content">
      <NavBar></NavBar>
      <router-view></router-view>
    </div>
    <MyFooter></MyFooter>
  </div>
</template>

<script>
  import NavBar from './components/Nav'
  import MyFooter from './components/Footer'

  export default {
    name: 'app',
    components: {NavBar, MyFooter}
  }
</script>

<style>
  html, body {
    margin: 0;
    padding: 0;
    height: 100%;
  }

  ol, ul {
    list-style: none;
  }

  a {
    text-decoration: none;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    min-height: 100%;
  }

  .content {
    padding-bottom: 100px;
  }
</style>
