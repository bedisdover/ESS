<template>
  <el-row>
    <el-col :span="6" :offset="10">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="tab">
        <el-tab-pane label="学生登录" name="2"></el-tab-pane>
        <el-tab-pane label="教师登录" name="1"></el-tab-pane>
      </el-tabs>
      <el-card>
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
          <el-form-item label="邮箱" prop="email">
            <el-input type="email" v-model="loginForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('loginForm')">登 录</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  import request from '../utils/request'

  export default {
    name: 'Login',
    data () {
      return {
        role: 2,
        activeName: '2',
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
      handleClick (tab) {
        this.role = tab.name
      },
      submitForm (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            request('/login', 'post', '', function (success, message, data) {
              if (success) {

              } else {
                console.log(message)
              }
            })
          } else {
            return false
          }
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
