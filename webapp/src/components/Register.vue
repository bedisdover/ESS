<template>
  <el-row>
    <el-col :span="6" :offset="10">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="tab">
        <el-tab-pane label="学生注册" name="2"></el-tab-pane>
        <el-tab-pane label="教师注册" name="1"></el-tab-pane>
      </el-tabs>
      <el-card>
        <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="80px">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="registerForm.name"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="registerForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="registerForm.password" type="password" autoComplete="false"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" prop="passwordRepeat">
            <el-input v-model="registerForm.passwordRepeat" type="password" autoComplete="false"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('registerForm')">立即注册</el-button>
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
        activeName: '2',
        registerForm: {
          name: '',
          email: '',
          password: '',
          passwordRepeat: ''
        },
        rules: {
          name: [
            {required: true, message: '请输入姓名'}
          ],
          email: [
            {required: true, message: '请输入邮箱'}
          ],
          password: [
            {required: true, message: '请输入密码'}
          ],
          passwordRepeat: [
            {validator: validatePassRepeat, trigger: 'blur'}
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
            global.request('/login', 'post', '', function (success, message, data) {
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
