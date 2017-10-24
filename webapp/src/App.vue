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
  import NavBar from './components/Nav'
  import MyFooter from './components/Footer'
  import request from './utils/request'

  export default {
    name: 'app',
    components: {NavBar, MyFooter},
    data () {
      return {
        login: true,
        role: 2,
        user: {}
      }
    },
    created () {
      request('/isLogin', 'post', '', (success, message, data) => {
        this.login = success
        console.log(success, message)

        if (this.login) {
          this.user = data
        }
      })
    }
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
    height: 100%;
  }

  #container {
    min-height: 100%;
  }

  .content {
    padding-bottom: 100px;
  }
</style>
