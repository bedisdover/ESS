<template>
  <el-container class="bg">
    <el-aside width="35%"></el-aside>
    <el-main>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>
            ESS注册成功
          </span>
        </div>
        <p>
          ESS注册成功，我们专业为教师学生提供服务，开始您的工作吧。
        </p>
        <el-button class="btn-login" type="success" round @click="gotoLogin">去登录</el-button>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: 'Verify',

    methods: {
      gotoLogin: function () {
        this.$router.push('login')
      }
    }
  }
</script>

<style>
  body {
    background: #F8F8F8;
  }
</style>

<style scoped>
  .bg {
    height: 700px;
    background: url(../assets/verifybg.jpg) no-repeat center;
  }

  .box-card {
    margin-top: 20%;
    margin-left: 10%;
    margin-right: 10%;
    height: 40%;
    background-color: inherit;
  }

  .btn-login {
    margin: 5% 0;
  }
</style>
