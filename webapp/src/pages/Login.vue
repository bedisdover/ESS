<template>
  <el-row>
    <el-col :span="6" :offset="10">
      <el-card>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
          <el-form-item label="邮箱" prop="email">
            <el-input type="email" v-model="loginForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="login('loginForm')">登 录</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
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
          email: ''
        },
        rules: {
          email: [
            {required: true, message: '请输入邮箱'}
          ],
          password: [
            {required: true, message: '请输入密码'}
          ]
        }
      }
    },
    methods: {
      login (formName) {
        let params = {
          email: this.loginForm.email,
          password: this.loginForm.password
        }
        this.$refs[formName].validate((valid) => {
          if (valid) {
            request('/login', 'post', params, (success, message, data) => {
              if (success) {
                this.afterLogin(data)
              } else {
                this.$notify.error({
                  title: '错误',
                  message: message
                })
              }
            })
          } else {
            return false
          }
        })
      },
      afterLogin: function (data) {
        Util.setCookie('user', data)
        this.$router.push({
          name: 'MyCourse'
        })
      }
    }
  }
</script>

<style scoped>
  .tab {
    margin-bottom: -16px;
  }
</style>
