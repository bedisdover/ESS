<template>
  <div>
    <div v-if="user.role === 1">
      <el-row>
        <el-col :span="12" :offset="6">
          <el-card class="box-card" style="width: 100%">
            <div slot="header" class="clearfix">
              <span>创建课程</span>
            </div>
            <CourseForm :courseForm="courseForm" @onSubmit="onSubmit" @onCancel="onCancel"
                        :submitText="'创建'" :cancelText="'重置'"
            >
            </CourseForm>
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
  import CourseForm from './CourseForm.vue'
  import util from '../lib/util'

  export default {
    components: {
      CourseForm
    },
    name: 'CreateCourse',
    props: ['user'],
    data () {
      return {
        courseForm: {
          name: '',
          password: '',
          year: '',
          grade: '',
          cls: [],
          term: ''
        }
      }
    },
    methods: {
      onSubmit (courseForm) {
        let params = {
          name: courseForm.name,
          grade: courseForm.grade,
          cls: courseForm.cls,
          year: courseForm.year,
          term: courseForm.term,
          password: courseForm.password
        }
        request('/course/create', 'post', params, (success, message) => {
          if (success) {
            this.$router.push('my')
          } else {
            util.notifyError(message)
          }
        })
      },
      onCancel () {
        this.courseForm = {
          name: '',
          password: '',
          year: '',
          grade: '',
          cls: [],
          term: ''
        }
      }
    }
  }
</script>

<style scoped>

</style>
