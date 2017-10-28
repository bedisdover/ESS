<template>
  <div>
    <el-row>
      <el-col :span="20"  :offset="2">
        <el-card class="box-card clearfix">
          <div slot="header">
            <span>未选课程</span>
            <el-button v-if="user.role === 1" style="float: right; padding: 3px 0" type="text">
              <router-link to="createCourse">创建课程</router-link>
            </el-button>
          </div>
          <el-table
            :data="courseListData"
            style="width: 100%;text-align: left">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-form label-position="left" inline class="demo-table-expand">
                  <el-form-item label="课程名称：">
                    <span>{{ props.row.name }}</span>
                  </el-form-item>
                  <el-form-item label="年份：">
                    <span>{{ props.row.year }}</span>
                  </el-form-item>
                  <el-form-item label="年级：">
                    <span>{{ props.row.grade }}</span>
                  </el-form-item>
                  <el-form-item label="学期：">
                    <span>{{ props.row.term }}</span>
                  </el-form-item>
                  <el-form-item label="班级：">
                    <span>{{ props.row.cls }}</span>
                  </el-form-item>
                </el-form>
              </template>
            </el-table-column>
            <el-table-column
              label="课程名称"
              prop="name"
            >
            </el-table-column>
            <el-table-column
              label="年级"
              prop="year"
            >
            </el-table-column>
            <el-table-column
              label="年级"
              prop="grade"
            >
            </el-table-column>
            <el-table-column
              label="学期"
              prop="term"
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
      <span>您正在加入{{courseName}}</span>
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

  export default {
    name: 'CourseList',
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
          this.$notify.error({
            title: '错误',
            message: message
          })
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
        request('/course/enroll', 'post', params, (success, message, data) => {
          if (success) {
            this.$router.push('my')
          } else {
            this.$notify.error({
              title: '错误',
              message: message
            })
          }
        })
      }
    }
  }
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

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
