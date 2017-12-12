<template>
  <div id="app">
    <div class="container">
      <NavBar v-if="showNav()" :user="getUser()" @onLogout="onLogout"></NavBar>
      <router-view :user="getUser()"></router-view>
    </div>
    <MyFooter v-show="showFooter()"></MyFooter>
  </div>
</template>

<script>
  import request from './lib/request'
  import Util from './lib/util'
  import NavBar from './components/Nav'
  import MyFooter from './components/Footer'

  export default {
    name: 'app',

    components: {NavBar, MyFooter},

    methods: {
      getUser: function () {
        return Util.getCookie('user')
      },
      showNav: function () {
        return this.getUser() !== undefined && this.$route.name !== 'Exam' && this.$route.name !== 'Paper'
      },
      showFooter: function () {
        return this.$router.currentRoute.name !== 'Index'
      },
      onLogout: function () {
        request('/logout', 'post', '', (success, message) => {
          if (success) {
            Util.removeCookie('user')
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
    background: #eeeeee;
  }

  ol, ul {
    list-style: none;
  }

  a {
    text-decoration: none;
    color: #333333;
  }

  pre {
    display: inline-block;
    margin: 0;
  }

  input {
    text-align: left !important;
  }

  svg {
    cursor: pointer;
  }

  .icon {
    width: 1em;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    display: flex;
    flex-flow: column;
    text-align: center;
    color: #2c3e50;
    min-height: 100vh;
  }

  .container {
    flex: 1;
  }
</style>
