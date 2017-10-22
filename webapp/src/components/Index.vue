<template>
  <div>
    <el-button><router-link to="/login">
      login
    </router-link></el-button>

    <router-link to="/register">
      register
    </router-link>
  </div>
</template>

<script>
  import request from '../utils/request'

  (function () {
    request('/test', 'get', '', function (success, message) {
      if (success) {
        console.log(message)
      }
    }, function () {
      console.log('errror')
    })
  })()
  export default {
    name: 'Index'
  }
</script>

<style scoped>

</style>
