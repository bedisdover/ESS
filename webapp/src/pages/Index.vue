<template>
  <div class="main">
    <div class="model"></div>
  </div>
</template>

<script>
  export default {
    name: 'Index'
  }
</script>

<style scoped>
  .main {
    height: 100vh;
    background: url(../assets/homebg.jpg) no-repeat;
    background-size: cover;
  }

  .model {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 100%;
    height: 100px;
    background-color: rgba(23, 37, 63, .2);
  }
</style>
