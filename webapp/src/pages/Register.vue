<template>
  <div class="main">
    <el-row type="flex" justify="center" align="middle">
      <el-col>
        <div class="tab">
          <div class="tab-item" :class="{active: role === 2}" @click="changeRole(2)">学生注册</div>
          <div class="tab-item" :class="{active: role === 1}" @click="changeRole(1)">教师注册</div>
        </div>
        <el-card>
          <el-form :model="registerForm" ref="registerForm" :rules="rules" :label-width="'80px'" class="form"
                   @keyup.native.enter="register('registerForm')">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="registerForm.name"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email" required>
              <el-input v-model="registerForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" type="password" autoComplete="false"></el-input>
            </el-form-item>
            <el-form-item label="重复密码" prop="passwordRepeat" required>
              <el-input v-model="registerForm.passwordRepeat" type="password" autoComplete="false"></el-input>
            </el-form-item>
            <el-form-item>
              <div class="button-container">
                <el-button type="primary" @click="register('registerForm')" :loading="registerForm.loading">注册
                </el-button>
                <span>
                  已有账号?
                  <el-button type="text" @click="gotoLogin()">前去登录</el-button>
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
          passwordRepeat: '',
          loading: false
        },
        rules: {
          name: [
            {required: true, message: '请输入姓名'}
          ],
          email: [
            {validator: Util.validateEmail, trigger: 'blur'}
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
      changeRole (role) {
        this.role = role
      },
      register (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.registerForm.loading = true

            let data = {
              name: this.registerForm.name,
              email: this.registerForm.email,
              password: this.registerForm.password,
              role: this.role
            }

            request('/signUp', 'post', JSON.stringify(data), (success, message) => {
              this.registerForm.loading = false

              if (success) {
                Util.notifySuccess(message)
                this.gotoLogin()
              } else {
                Util.notifyError(message)
              }
            }, () => {
              this.registerForm.loading = false
            })
          } else {
            return false
          }
        })
      },
      gotoLogin: function () {
        this.$router.push({
          name: 'Login'
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
  }

  .form {
    min-width: 400px;
    padding: 30px 30px 0 20px;
    text-align: left;
  }

  .button-container {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
  }

  .tab {
    height: 60px;
    line-height: 60px;
    border: 1px solid #e6ebf5;
    border-bottom: none;
    border-radius: 4px;
    background: white;
  }

  .tab-item {
    display: inline-block;
    width: 50%;
    text-align: center;
    position: relative;
    cursor: pointer;
  }

  .tab-item.active {
    background: #1D8CE0;
    color: white;
  }

  .tab-item:not(.active):hover {
    background: #58B7FF;
    color: white;
  }

  .tab-item:first-child {
    border-top-left-radius: 4px;
  }

  .tab-item:nth-child(2) {
    float: right;
    border-top-right-radius: 4px;
  }

  .tab-item:nth-child(2):before {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    width: 2px;
    height: 60px;
    background: #e6ebf5;
  }
</style>
