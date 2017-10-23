<template>
  <el-row class="main">
    <el-col :span="8" :offset="8">
      <el-card>
        <div>
          <span>学生登录</span>
          <span>教师登录</span>
        </div>
      </el-card>
      <el-card>
        <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="80px">
          <el-form-item label="姓名" required>
            <el-input v-model="registerForm.name"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" required>
            <el-input v-model="registerForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" required>
            <el-input v-model="registerForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" required>
            <el-input v-model="registerForm.passwordRepeat" type="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onRegister">立即注册</el-button>
            <router-link to="/login">登录</router-link>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: 'Register',
    data () {
      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'))
        } else {
          callback()
        }
      }
      let validatePassRepeat = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.registerForm.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      return {
        role: 2,
        registerForm: {
          name: '',
          email: '',
          password: '',
          passwordRepeat: ''
        },
        rules: {
          password: [
            {validator: validatePass, trigger: 'blur'}
          ],
          passwordRepeat: [
            {validator: validatePassRepeat, trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      onRegister: function (e) {
        e.preventDefault()
        this.$refs['registerForm'].validate((valid) => {
          if (valid) {
            console.log(this.registerForm.name)
          }
        })
      }
    }
  }
</script>

<style scoped>
  .main {
    min-height: 100%;
  }
</style>
