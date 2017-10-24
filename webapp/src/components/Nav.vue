<template>
  <div class="nav" v-show="user !== undefined">
    <div v-if="user.role === 1">
      <router-link to="/my">我的课程</router-link>
    </div>
    <div v-else-if="user.role === 2">
      <span>
        <router-link to="/list">所有课程</router-link>
        <router-link to="/my">我的课程</router-link>
      </span>
    </div>
  </div>
</template>

<script>
  import Cookies from 'js-cookie'

  export default {
    name: 'NavBar',
    props: ['role'],
    computed: {
      user: function () {
        let user = Cookies.get('user')

        if (user) {
          user = JSON.parse(user)
        }

        return user
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
  }

  a {
    line-height: 72px;
    color: #dedfe3;
    padding: 6px 20px;
    text-decoration: none;
  }
  a:hover{
    color: gold;
  }
</style>
