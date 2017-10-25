<template>
  <div id="app">
    <div class="content">
      <NavBar v-if="showNav()" :user="getUser()" @onLogout="onLogout"></NavBar>
      <router-view :user="getUser()"></router-view>
    </div>
    <MyFooter></MyFooter>
  </div>
</template>

<script>
  import Cookies from 'js-cookie'
  import request from './lib/request'
  import NavBar from './components/Nav'
  import MyFooter from './components/Footer'

  export default {
    name: 'app',
    components: {NavBar, MyFooter},
    methods: {
      getUser: function () {
        let user = Cookies.get('user')

        if (user) {
          user = JSON.parse(user)
        }

        return user
      },
      showNav: function () {
        return this.getUser() !== undefined
      },
      onLogout: function () {
        request('/logout', 'post', '', (success, message) => {
          if (success) {
            Cookies.remove('user')
            this.$router.push({
              name: 'Index'
            })
          } else {
            this.$notify.error({
              title: '错误',
              message: message
            })
          }
        })
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
