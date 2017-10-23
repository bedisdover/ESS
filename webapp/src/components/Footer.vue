<template>
  <div id="footer">
    Copyright &copy; {{ year }} ESS
  </div>
</template>

<script>
  export default {
    name: 'Footer',
    data () {
      return {
        year: new Date().getFullYear()
      }
    }
  }
</script>

<style scoped>
  #footer {
    position: absolute;
    height: 100px;
    width: 100%;
    margin-top: -100px;
    line-height: 100px;
    text-align: center;
  }
</style>
