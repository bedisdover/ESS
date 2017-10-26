<template>
  <div class="main">
    <div class="header">
      <span id="logo">ESS 在线考试系统</span>
      <span class="sign">
        <router-link to="/login">登录</router-link>
        <span class="divider"> | </span>
        <router-link to="/register">注册</router-link>
      </span>
    </div>
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

  .header {
    max-width: 1200px;
    height: 120px;
    line-height: 120px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    color: #eeeeee;
  }

  #logo {
    font-size: 2em;
  }

  .sign > a {
    position: relative;
    cursor: pointer;
    text-decoration: none;
    color: #eeeeee;
  }

  .sign a::before,
  .sign a::after {
    display: inline-block;
    opacity: 0;
    -webkit-transition: -webkit-transform 0.3s, opacity 0.2s;
    -moz-transition: -moz-transform 0.3s, opacity 0.2s;
    transition: transform 0.3s, opacity 0.2s;
  }

  .sign a::before {
    margin-right: 10px;
    content: '[';
    -webkit-transform: translateX(20px);
    -moz-transform: translateX(20px);
    transform: translateX(20px);
  }

  .sign a::after {
    margin-left: 10px;
    content: ']';
    -webkit-transform: translateX(-20px);
    -moz-transform: translateX(-20px);
    transform: translateX(-20px);
  }

  .sign a:hover::before,
  .sign a:hover::after,
  .sign a:focus::before,
  .sign a:focus::after {
    opacity: 1;
    -webkit-transform: translateX(0px);
    -moz-transform: translateX(0px);
    transform: translateX(0px);
  }
</style>
