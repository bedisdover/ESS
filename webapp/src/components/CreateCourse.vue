<template>
  <div>
    <div v-if="user.role === 1">
      <el-row>
        <el-col :span="12"  :offset="6">
          <el-card class="box-card" style="width: 100%">
            <el-form ref="courseForm" :model="courseForm" label-width="80px" :rules="rules">
              <el-form-item label="课程名称" prop="name">
                <el-input v-model="courseForm.name" ></el-input>
              </el-form-item>
              <el-form-item label="年级" prop="grade">
                <el-select v-model="courseForm.grade" placeholder="请选择年级" style="width: 100%">
                  <el-option label="大一" value="1"></el-option>
                  <el-option label="大二" value="2"></el-option>
                  <el-option label="大三" value="3"></el-option>
                  <el-option label="大四" value="4"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="年份" prop="year">
                <el-select v-model="courseForm.year" style="width: 100%" placeholder="请选择年份">
                  <el-option v-for="year in years" :label="year.label" :key="year.value" :value="year.value"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="学期" prop="term">
                <el-select v-model="courseForm.term" placeholder="请选择学期" style="width: 100%">
                  <el-option label="第一学期" value="1"></el-option>
                  <el-option label="第二学期" value="2"></el-option>
                  <el-option label="第三学期" value="3"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="班级" prop="cls">
                <el-checkbox-group v-model="courseForm.cls">
                  <el-checkbox label="1"></el-checkbox>
                  <el-checkbox label="2"></el-checkbox>
                  <el-checkbox label="3"></el-checkbox>
                  <el-checkbox label="4"></el-checkbox>
                </el-checkbox-group>
              </el-form-item>
              <el-form-item label="选课密码" prop="password">
                <el-input v-model="courseForm.password"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit('courseForm')">立即创建</el-button>
                <el-button v-on:click="onCancel">取消</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div v-else>
        您身份不正确
    </div>

  </div>
</template>

<script>
  import request from '../lib/request'

  export default {
    name: 'CreateCourse',
    props: ['user'],
    data () {
      return {
        courseForm: {
          name: '',
          grade: '',
          cls: [],
          year: '',
          term: '',
          password: ''
        },
        years: [{
          label: '2014 - 2015',
          value: '2014'
        }, {
          label: '2015 - 2016',
          value: '2015'
        }, {
          label: '2016 - 2017',
          value: '2016'
        }, {
          label: '2017 - 2018',
          value: '2017'
        }],
        rules: {
          name: [
            { required: true, message: '请输入课程名称', trigger: 'blur' }
          ],
          grade: [
            { required: true, message: '请选择年级', trigger: 'change' }
          ],
          year: [
            { required: true, message: '请选择年份', trigger: 'blur' }
          ],
          term: [
            { required: true, message: '请选择学期', trigger: 'change' }
          ],
          cls: [
            { type: 'array', required: true, message: '请至少选择一个班级', trigger: 'change' }
          ],
          password: [
            { required: true, message: '请填写选课密码', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      onSubmit (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let params = {
              name: this.courseForm.name,
              grade: this.courseForm.grade,
              cls: this.courseForm.cls,
              year: this.courseForm.year,
              term: this.courseForm.term,
              password: this.courseForm.password
            }
            request('/course/create', 'post', params, (success, message, data) => {
              if (success) {
                this.$router.push('my')
              } else {
                this.$notify.error({
                  title: '错误',
                  message: message
                })
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      onCancel () {
        this.courseForm = {
          name: '',
          grade: '',
          class: [],
          year: [],
          term: '',
          password: ''
        }
      }
    }
  }
</script>

<style scoped>

</style>
