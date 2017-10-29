<template>
  <div class="main">
    <el-row type="flex" justify="center" align="middle">
      <el-col>
        <el-card>
          <el-form :model="loginForm" :rules="rules" ref="loginForm" :label-width="'60px'" class="form">
            <el-form-item label="邮箱" prop="email" required>
              <el-input type="email" v-model="loginForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="loginForm.password"></el-input>
            </el-form-item>
            <el-form-item>
              <div class="button-container">
                <el-button type="primary" @click="login('loginForm')" :loading="loginForm.loading">登录</el-button>
                <span>
                  还没账号?
                  <el-button type="text" @click="gotoRegister()">立即注册</el-button>
                </span>
              </div>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import request from '../lib/request'
  import Util from '../lib/util'

  export default {
    name: 'Login',
    data () {
      return {
        loginForm: {
          password: '',
          email: '',
          loading: false
        },
        rules: {
          email: [
            {validator: Util.validateEmail, trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码'}
          ]
        }
      }
    },
    methods: {
      login (formName) {
        this.loginForm.loading = true

        let params = {
          email: this.loginForm.email,
          password: this.loginForm.password
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            request('/login', 'post', params, (success, message, data) => {
              this.loginForm.loading = false

              if (success) {
                Util.setCookie('user', data)
                this.$router.push({
                  name: 'MyCourse'
                })
              } else {
                this.$notify.error({
                  title: '错误',
                  message: message
                })
              }
            }, () => {
              this.loginForm.loading = false
            })
          } else {
            return false
          }
        })
      },
      gotoRegister: function () {
        this.$router.push({
          name: 'Register'
        })
      }
    }
  }
</script>

<style scoped>
  .main {
    height: calc(100vh - 100px);
    display: flex;
    justify-content: center;
    background: #eeeeee;
  }

  .form {
    min-width: 400px;
    padding: 30px 20px 0 20px;
    text-align: left;
  }

  .button-container {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
  }
</style>
