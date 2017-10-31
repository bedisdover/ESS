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
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
</style>
