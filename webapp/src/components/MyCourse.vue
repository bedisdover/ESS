<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span>我的课程</span>
            <el-button v-if="user.role === 1" style="float: right; padding: 3px 0" type="text">
              <router-link to="createCourse">创建课程</router-link>
            </el-button>
          </div>
          <el-table
            :data="myListData"
            style="width: 100%;text-align: left">
            <el-table-column type="expand">
              <template slot-scope="props">
                <CourseInfo :props="props">
                </CourseInfo>
              </template>
            </el-table-column>
            <el-table-column
              label="课程名称"
              prop="name"
            >
            </el-table-column>
            <el-table-column
              label="年份"
              prop="year"
            >
            </el-table-column>
            <el-table-column
              label="学期"
              prop="term"
            >
            </el-table-column>
            <el-table-column
              label="年级"
              prop="grade"
            >
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="editCourseHandle(scope.row)" v-if="user.role === 1">编辑课程
                </el-button>
                <el-button type="danger" size="mini" @click="dialogHandle(scope.row.name, scope.row.id)"
                           v-if="user.role === 2">退出课程
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :close-on-click-modal=false
      title="退出课程"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>是否确认退出{{courseName}}？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dropCourse">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :close-on-click-modal=false
      title="编辑课程"
      :visible.sync="editDialogVisible"
      width="60%"
    >
      <CourseForm :courseForm="courseForm" @onSubmit="onSubmit" @onCancel="onCancel"
                  :submitText="'更新'" :cancelText="'取消'">
      </CourseForm>
    </el-dialog>
  </div>
</template>

<script>
  import request from '../lib/request'
  import util from '../lib/util'
  import CourseForm from './CourseForm.vue'
  import CourseInfo from './CourseInfo.vue'

  export default {
    components: {
      CourseForm, CourseInfo
    },
    name: 'MyCourse',
    props: ['user'],
    data () {
      return {
        myListData: [],
        courseId: '',
        courseName: '',
        dialogVisible: false,
        editDialogVisible: false,
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
    created: function () {
      request('/course/my', 'get', '', (success, message, data) => {
        if (success) {
          this.myListData = data
        } else {
          util.notifyError(message)
        }
      })
    },
    methods: {
      dialogHandle (courseName, courseId) {
        this.courseName = courseName
        this.courseId = courseId
        this.dialogVisible = true
      },
      editCourseHandle (course) {
        this.courseId = course.id
        this.editDialogVisible = true
        this.courseForm.name = course.name
        this.courseForm.password = course.password
        this.courseForm.year = course.year.toString()
        this.courseForm.grade = course.grade.toString()
        this.courseForm.term = course.term.toString()
        this.courseForm.cls = course.cls.split(',')
      },
      dropCourse () {
        this.dialogVisible = false
        let params = {
          courseId: this.courseId
        }
        request('/course/drop', 'post', params, (success, message) => {
          if (success) {
            this.$router.push('list')
          } else {
            util.notifyError(message)
          }
        })
      },
      onSubmit (courseForm) {
        this.editDialogVisible = false
        let params = {
          name: courseForm.name,
          grade: courseForm.grade,
          cls: courseForm.cls,
          year: courseForm.year,
          term: courseForm.term,
          password: courseForm.password,
          id: this.courseId
        }
        request('/course/modify', 'post', params, (success, message) => {
          if (success) {
            this.myListData = this.myListData.map((obj) => {
              if (obj.id === this.courseId) {
                obj = params
                obj.cls = params.cls.join(',')
              }
              return obj
            })
          } else {
            util.notifyError(message)
          }
        })
      },
      onCancel () {
        this.editDialogVisible = false
      }
    }
  }
</script>

<style scoped>
  .box-card {
    width: 100%;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both;
  }
</style>
