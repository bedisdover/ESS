<template>
  <div>
    <el-row>
      <el-col :span="20"  :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span>课程列表</span>
            <el-button v-if="user.role === 1" style="float: right; padding: 3px 0" type="text">
              <router-link to="createCourse">创建课程</router-link>
            </el-button>
          </div>
          <el-table
            :data="courseListData"
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
                <el-button type="success" size="mini" @click="dialogHandle(scope.row.name, scope.row.id)" v-if="user.role === 2">加入课程</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :close-on-click-modal=false
      title="加入课程"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span>您确定加入{{courseName}}</span>
      <el-form :model="form" ref="form">
        <el-form-item label="选课密码">
          <el-input v-model="form.courseKey" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="joinCourse()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import request from '../lib/request'
  import util from '../lib/util'
  import CourseInfo from '../components/CourseInfo.vue'

  export default {
    name: 'CourseList',
    components: {CourseInfo},
    props: ['user'],
    data () {
      return {
        courseListData: [],
        dialogVisible: false,
        courseName: '',
        courseId: '',
        form: {
          courseKey: ''
        }
      }
    },
    created: function () {
      request('/course/list', 'get', '', (success, message, data) => {
        if (success) {
          this.courseListData = data
        } else {
          util.notifyError(message)
        }
      })
    },
    methods: {
      dialogHandle (courseName, courseId) {
        this.courseId = courseId
        this.courseName = courseName
        this.form.courseKey = ''
        this.dialogVisible = true
      },
      joinCourse () {
        this.dialogVisible = false
        let params = {
          courseId: this.courseId,
          courseKey: this.form.courseKey
        }
        request('/course/enroll', 'post', params, (success, message) => {
          if (success) {
            this.$router.push('my')
          } else {
            util.notifyError(message)
          }
        })
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
