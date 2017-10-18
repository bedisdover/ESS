<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <button @click="getData()">click</button>
  </div>
</template>

<script>
  export default {
    name: 'HelloWorld',
    data () {
      return {
        msg: 'Hello world!!!'
      }
    },
    methods: {
      getData: function () {
        console.log(process.env.API_ROOT)
        function reqListener () {
          console.log(this.responseText)
        }

        let oReq = new XMLHttpRequest()
        oReq.onload = reqListener
        oReq.open('get', process.env.API_ROOT + '/test', true)
        oReq.send()
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
