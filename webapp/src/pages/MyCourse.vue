<template>
  <div>
    <el-row>
      <el-col :span="20" :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span style="margin-right: -50px">我的课程</span>
            <router-link to="createCourse">
              <el-button v-if="user.role === 1" style="float: right" type="primary" size="small">
                创建课程
              </el-button>
            </router-link>
          </div>
          <el-table
            :data="myListData"
            v-loading="loading"
            style="width: 100%;text-align: left"
          >
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
            <el-table-column
              label="设置"
              v-if="user.role === 1"
            >
              <template slot-scope="scope">
                <router-link :to="{ name: 'QuestionList', params: { id: scope.row.id, courseName: scope.row.name }}">
                  <el-tooltip content="试题库" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-questionList"></use>
                          </svg>
                        </span>
                  </el-tooltip>
                </router-link>
                <router-link :to="{ name: 'ExamStudents', params: { id: scope.row.id, courseName: scope.row.name }}">
                  <el-tooltip content="学生名单" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-xuesheng"></use>
                          </svg>
                        </span>
                  </el-tooltip>
                </router-link>
                <router-link :to="{ name: 'ExamList', params: { id: scope.row.id, cls: scope.row.cls }}">
                  <el-tooltip content="考试列表" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true">
                            <use xlink:href="#icon-examList"></use>
                          </svg>
                        </span>
                  </el-tooltip>
                </router-link>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
            >
              <template slot-scope="scope">
                <div v-if="user.role === 1">
                  <el-tooltip content="编辑课程" effect="light">
                        <span class="operation">
                          <svg class="icon" aria-hidden="true" @click="editCourseHandle(scope.row)">
                            <use xlink:href="#icon-edit"></use>
                          </svg>
                        </span>
                  </el-tooltip>
                </div>

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
  import CourseForm from '../components/CourseForm.vue'
  import CourseInfo from '../components/CourseInfo.vue'

  export default {
    components: {
      CourseForm, CourseInfo
    },
    name: 'MyCourse',
    props: ['user'],
    data () {
      return {
        loading: true,
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
          this.loading = false
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
          cls: courseForm.cls.join(','),
          year: courseForm.year,
          term: courseForm.term,
          password: courseForm.password,
          id: this.courseId
        }
        request('/course/modify', 'post', JSON.stringify(params), (success, message) => {
          if (success) {
            this.loading = true
            request('/course/my', 'get', '', (success, message, data) => {
              if (success) {
                this.myListData = data
                this.loading = false
              } else {
                util.notifyError(message)
              }
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

  .operation {
    font-size: 1.5em;
  }
</style>
