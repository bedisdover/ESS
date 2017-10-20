<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button @click="getData()">click</button>
  </div>
</template>

<script>
  import request from '../utils/request'

  export default {
    name: 'HelloWorld',
    data () {
      return {
        msg: 'Hello world!!!'
      }
    },
    methods: {
      getData: function () {
        request('/test', 'get', '', function (success, message) {
          if (success) {
            console.log('success')
          }
          console.log(message)
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
